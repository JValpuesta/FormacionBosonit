package com.example.uploadingfiles.controller;

import com.example.uploadingfiles.application.StorageService;
import com.example.uploadingfiles.domain.Fichero;
import com.example.uploadingfiles.domain.StorageException;
import com.example.uploadingfiles.domain.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
    @Autowired
    StorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<Fichero> subirFichero(@RequestParam MultipartFile file, @RequestParam String categoria)
            throws StorageException{
        try {
            String tipo = file.getContentType();
            if (!categoria.equals(tipo)) {
                throw new StorageException("El archivo " + file.getName() + " es de tipo " + tipo
                        + " y no " + categoria);
            }
            return ResponseEntity.ok().body(storageService.store(file));
        } catch (StorageException e) {
            throw new StorageException(e.getMessage());
        }
    }

    @GetMapping("/setpath")
    public ResponseEntity<String> setPath (@RequestParam String path) throws Exception {
        try{
            storageService.setPath(path);
            storageService.init();
            return ResponseEntity.ok().body("El nuevo directorio es: "+path);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/file")
    public ResponseEntity<Resource> descargarFicheroById(@RequestParam String id) {
        try {
            Resource file = storageService.downloadAsResourceById(id);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                    file.getFilename() + "\"").body(file);
        } catch (StorageFileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filename")
    public ResponseEntity<Resource> descargarFicheroByName(@RequestParam String nombre) {
        try {
            Resource file = storageService.downloadAsResourceByFilename(nombre);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                    + file.getFilename() + "\"").body(file);
        } catch (StorageFileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
