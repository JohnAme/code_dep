package com.se.util;

public class StorageNorm {
    public static String getUCZip(String pname){
        return "uc_"+pname+".zip";
    }
    public static String getUC(){
        return "uc";
    }
    public static String getNomalizedUC(){
        return "normalized_uc";
    }
    public static String getCodeJar(String pname){
        return pname+".jar";
    }
    public static String getCompiledJar(String pname){
        return "c_"+pname+".jar";
    }
    public static String getNormalizedCodeRoot(){
        return "normalized_code";
    }
}
