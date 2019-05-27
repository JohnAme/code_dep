package com.se.util;

public class StringUtil {
    public static String jarEntryAsClassName(String entry){
        String str=entry.replaceAll("/",".");
        String classSubfix=".class";
        if(entry.endsWith(classSubfix)){
            str=str.substring(0,str.length()-classSubfix.length());
        }
        return str;
    }
    public static int splitClassName(String className){
        return className.lastIndexOf('.');
    }

    public static String getDirectory(String name){
        int temp=name.lastIndexOf('.');
        if(-1==temp){
            return "";
        }else{
            return name.substring(0,temp);
        }
    }

    public static String getLeaf(String name){
        int temp=name.lastIndexOf('.');
        if(-1==temp){
            return name;
        }else{
            return name.substring(temp+1,name.length());
        }
    }
    public static String getPathAndName(String path,String name){
        if(null==name){
            return null;
        }
        if(null==path||0==path.length()){
            return name;
        }
        return path+"."+name;
    }


    public static void main(String[] args){
        System.out.println(splitClassName("ab"));
        System.out.println(splitClassName("a.b"));
        System.out.println(splitClassName("a.b.c"));
        String a="a.";
        System.out.println(a.substring(splitClassName(a)+1,a.length()));
    }
}
