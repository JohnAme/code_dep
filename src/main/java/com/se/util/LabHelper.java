package com.se.util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LabHelper {
    public static void readDoc(String direcotryPath){
        Path dir= Paths.get(direcotryPath);
        DirectoryStream.Filter<Path> filter=new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                boolean b=entry.toString().endsWith(".txt")&&Files.isReadable(entry);
                return  b;
            }
        };
        try(DirectoryStream<Path> stream=Files.newDirectoryStream(dir,filter)){
            for(Path entry:stream){
                System.out.println(entry.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
//        readDoc("C:\\Users\\Jerry\\Desktop\\material");
        Path path=Paths.get("C:\\\\Users\\\\Jerry\\\\Desktop\\\\material");
        System.out.print(Files.isDirectory(path)+":"+Files.exists(path));
    }
}
