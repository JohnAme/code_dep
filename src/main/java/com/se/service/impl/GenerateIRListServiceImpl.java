package com.se.service.impl;

import com.se.service.GenerateIRListService;
import com.se.util.NormalizeUtil;


import java.io.*;
import java.nio.Buffer;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class GenerateIRListServiceImpl implements GenerateIRListService {
    @Override
    public boolean generateDocCorpus(String path) {
        Path dir= Paths.get(path);
        if(!Files.exists(dir)||!Files.isDirectory(dir)) {
            System.err.println("Document directory not found");
            return false;
        }
        DirectoryStream.Filter<Path> filter=getDirectoryStreamFilter(".txt");
        try(DirectoryStream<Path> stream=Files.newDirectoryStream(dir,filter)){
            for(Path entry:stream){
                BufferedReader br=new BufferedReader(new FileReader(entry.toString()));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean generateCodeCorpus(String path) {
        return false;
    }

    private DirectoryStream.Filter<Path> getDirectoryStreamFilter(String suffix){
        DirectoryStream.Filter<Path> filter=new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                boolean b=entry.toString().endsWith(suffix)&&Files.isReadable(entry);
                return  b;
            }
        };
        return filter;
    }

    synchronized private static boolean processUCFile(Path path,Path target) throws IOException {
        if(!path.toString().endsWith(".txt")){
            System.err.println("uc should be txt format");
            return false;
        }
        File file=new File(path.toString());
        if(!file.exists()){
            System.err.println("cannot found file:"+path.getFileName());
            return false;
        }
        File targetFile=new File(target.toString());
        Files.createDirectories(target.getParent());
        if(!targetFile.exists()){
            Files.createFile(target);
        }
        try(BufferedReader br=new BufferedReader(new FileReader(file));
            BufferedWriter bw=new BufferedWriter(new FileWriter(targetFile))){

            StringBuilder sb=new StringBuilder();
            String line=br.readLine();
            while (null!=line){
                String tmp=NormalizeUtil.normalize(line,3);
                sb.append(tmp);
                line=br.readLine();
            }
            bw.write(sb.toString());
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }

    }
    public static void main(String[] args) throws IOException {
        Path path=Paths.get("C:\\Users\\Jerry\\Desktop\\material\\uc1.txt");
    }
}
