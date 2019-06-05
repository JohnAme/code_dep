package com.se.service.impl;

import com.se.config.StorageProperties;
import com.se.exception.StorageException;
import com.se.service.CaptureCDService;
import com.se.service.FileStorageService;
import com.se.service.GraphService;
import com.se.util.FileUtil;
import com.se.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
        rootLocation= Paths.get(FileUtil.PROJECT_ROOT);
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
    public boolean store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
//        System.out.println(filename);
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
                Path temp=Paths.get(FileUtil.TEMP_DIRECTORY);
                Files.copy(inputStream, temp.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
//            captureCDService.getCDFromJarFile(this.rootLocation.resolve(filename).toString());
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
        return false;
    }

    @Override
    public boolean store(MultipartFile multipartFile, Path target) {
        if(multipartFile.isEmpty()){
            System.err.println("Cannot not store empty file");
            return false;
        }
        File file=new File(target.toString());
        if(!file.exists()||!file.isDirectory()){
            System.err.println(target.toString()+" is not valid directory");
            return false;
        }
        Path path=target.resolve(StringUtils.cleanPath(multipartFile.getOriginalFilename()));
        file=new File(path.toString());
        if(file.exists()){
            System.err.println("File already exists:"+path.toString());
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, path,
                    StandardCopyOption.REPLACE_EXISTING);
            return true;
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean storeWithName(MultipartFile file,Path fullName){
        if(file.isEmpty()){
            System.err.println("Cannot not store empty file");
            return false;
        }
        File temp=new File(fullName.getParent().toString());
        if(!temp.exists()||!temp.isDirectory()){
            System.err.println(fullName.getParent().toString()+" is not valid directory");
            return false;
        }

        temp=new File(fullName.toString());
        if(temp.exists()){
            System.err.println("File already exists:"+fullName.toString());
        }
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, fullName,
                    StandardCopyOption.REPLACE_EXISTING);
            return true;
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
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
    synchronized public boolean delete(Path path) {
        File file=new File(path.toString());
        if(!file.exists()){
            return false;
        }
        try {
            Files.delete(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteAll() {

    }
}
