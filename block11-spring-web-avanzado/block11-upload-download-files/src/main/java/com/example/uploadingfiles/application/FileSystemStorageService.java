package com.example.uploadingfiles.application;

import com.example.uploadingfiles.domain.Fichero;
import com.example.uploadingfiles.domain.StorageException;
import com.example.uploadingfiles.domain.StorageFileNotFoundException;
import com.example.uploadingfiles.domain.StorageProperties;
import com.example.uploadingfiles.repository.FicheroRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class FileSystemStorageService implements StorageService {

    @Value("${direccion:~/directorioFicheros}")
    private Path rootLocation;

    @Autowired
    private ApplicationArguments args;
    @PostConstruct
    public void configurarRuta() {
        String[] argumentos = args.getSourceArgs();
        if (argumentos.length > 0) {
            this.setPath(argumentos[0]);
        } else {
            this.setPath("C:\\Users\\jorge.valpuesta\\IdeaProjects\\FormacionBosonit\\block11-spring-web-avanzado" +
                    "\\block11-upload-download-files\\src\\main\\resources");
        }
    }

    @Autowired
    FicheroRepository ficheroRepository;

    @Autowired
    StorageProperties storageProperties;

    @Override
    public Fichero store(MultipartFile file) throws  StorageException{
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
                Fichero fichero = new Fichero();
                fichero.setNombre(file.getOriginalFilename());
                fichero.setFechaSubida(new Date());
                fichero.setCategoria(file.getContentType());

                return ficheroRepository.save(fichero);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public void setPath(String path) {
        storageProperties.setLocation(path);
        this.rootLocation = Paths.get(storageProperties.getLocation());
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource downloadAsResourceById(String id) throws StorageFileNotFoundException{
        try {
            Optional<Fichero> stringOptional = ficheroRepository.findById(Integer.parseInt(id));
            if (stringOptional.isPresent()) {
                String fichero = stringOptional.get().getNombre();
                Path file = load(fichero);
                Resource resource = new UrlResource(file.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    throw new StorageFileNotFoundException("No se pudo leer el fichero con id: " + id);
                }
            } else {
                throw new StorageFileNotFoundException("No se pudo leer el fichero con id: " + id);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("No se pudo leer el fichero con id: " + id, e);
        }
    }

    @Override
    public Resource downloadAsResourceByFilename(String filename) throws StorageFileNotFoundException {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new StorageFileNotFoundException("No se pudo leer el fichero con nombre: " + filename);
            }
            return resource;

        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("No se pudo leer el fichero con nombre: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() throws  StorageException{
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("No se pudo inicializar el archivo", e);
        }
    }
}