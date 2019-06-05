package com.se.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/*
 *return "" when meet any error
 */
public class FileUtil {
    public static final String TEMP_DIRECTORY="D:\\repository\\code_dep\\src\\main\\resources\\project\\temp";
    public static final String PROJECT_ROOT="D:\\repository\\code_dep\\src\\main\\resources\\project";

    public static String readTxtFile(Path path){
        if(!path.toString().endsWith(".txt")){
            return "";
        }
        return readFile(path);
    }
    public static String readJavaFile(Path path){
        if(!path.toString().endsWith(".java")){
            return "";
        }
        return readFile(path);
    }

    private static String readFile(Path path){
        if(!Files.exists(path)||!Files.isReadable(path)){
            return "";
        }
        try(BufferedReader br=new BufferedReader(new FileReader(new File(path.toString())))){
            String line="";
            StringBuilder sb=new StringBuilder();
            do{
                line=br.readLine();
                sb.append(line+" ");
            }while(null!=line);
            return sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    synchronized public static boolean createAndWriteFile(Path path,String content){
        if(Files.exists(path)){
            return false;
        }
        try{
            if(!Files.exists(path.getParent())){
                Files.createDirectories(path.getParent());
            }
            Files.createFile(path);
        }catch (IOException e){
            System.err.println(e.toString());
            return false;
        }

        try(BufferedWriter bw=new BufferedWriter(new FileWriter(new File(path.toString())))){
            bw.write(content);
            return true;
        }catch (IOException e){
            System.err.println(e.toString());
            return false;
        }
    }

    public static void wirteIdtfMap(Map<String,Double> map,Path target){
        StringBuilder sb=new StringBuilder();
        for(String str:map.keySet()){
            sb.append(str);
            sb.append(" "+map.get(str)+System.lineSeparator());
        }

        createAndWriteFile(target,sb.toString());
    }

    public static void main(String[] args){
//        Path path= Paths.get("C:\\Users\\Jerry\\Desktop\\material\\new 1.txt");
//        System.out.println(readTxtFile(path));
        Path path=Paths.get(TEMP_DIRECTORY);
        System.out.print(path.resolve("a.txt"));
    }
}
