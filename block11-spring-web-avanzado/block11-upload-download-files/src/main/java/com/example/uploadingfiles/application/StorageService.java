package com.example.uploadingfiles.application;

import com.example.uploadingfiles.domain.Fichero;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

	void init();

	void setPath(String path);

	Fichero store(MultipartFile multipartFile);

	Path load(String filename);

	Resource downloadAsResourceById(String id);

	Resource downloadAsResourceByFilename(String filename);

	void deleteAll();

}
