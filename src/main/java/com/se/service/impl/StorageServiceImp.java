package com.se.service.impl;

import com.se.config.StorageProperties;
import com.se.exception.StorageException;
import com.se.service.CaptureCDService;
import com.se.service.FileStorageService;
import com.se.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.jar.JarFile;
import java.util.stream.Stream;

@Service
public class StorageServiceImp implements FileStorageService {
    private final Path rootLocation;
    private final CaptureCDService captureCDService;
    @Autowired
    public StorageServiceImp(StorageProperties storageProperties,CaptureCDService captureCDService){
        rootLocation= Paths.get(storageProperties.getLocation());
        this.captureCDService=captureCDService;
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }catch (IOException e){
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        System.out.println(filename);
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
            captureCDService.getCDFromJarFile(this.rootLocation.resolve(filename).toString());
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
