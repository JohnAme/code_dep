package com.se.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;


public interface FileStorageService {

    void init();

    boolean store(MultipartFile file);

    boolean store(MultipartFile file,Path target);

    boolean storeWithName(MultipartFile file,Path fullName);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    boolean delete(Path path);

    void deleteAll();

}
