package com.se.util;

import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

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
    public static void readZip(){
        Path path=Paths.get("C:\\Users\\Jerry\\Desktop\\material\\new 1.zip");
        File file=new File(path.toString());

        try(ZipFile zipFile=new ZipFile(file)){
            Enumeration<? extends ZipEntry> entries=zipFile.entries();
            while (entries.hasMoreElements()){
                ZipEntry entry=entries.nextElement();
                String str=FilenameUtils.getFullPath(entry.getName());
                InputStream input=zipFile.getInputStream(entry);
                InputStreamReader inputStreamReader=new InputStreamReader(input);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                String s=bufferedReader.readLine();
                while(null!=s){
                    System.out.println(s);
                    s=bufferedReader.readLine();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void filenameUtils(){
        String path="\\a.txt";
        System.out.println(FilenameUtils.getName(path));
        System.out.println(FilenameUtils.getBaseName(path));
        System.out.println(FilenameUtils.getPath(path).length());
    }
    public static void listFiles(){
        Path dir=Paths.get("C:\\Users\\Jerry\\Desktop\\material");
        Path target=Paths.get("C:\\Users\\Jerry\\Desktop\\material\\chap10.pdf");
        File file=new File(dir.toString());
        File[] list=file.listFiles();
        for(File f:list){
            System.out.println(f.getName());
        }
        list=new File(target.toString()).listFiles();
        System.out.println(list==null);
    }

    public static void fileSeparator(){
        System.out.print("file separate:"+File.separator);
    }
    public static void main(String[] args) {

        listFiles();
    }

}
