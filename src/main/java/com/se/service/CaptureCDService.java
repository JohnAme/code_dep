package com.se.service;

public interface CaptureCDService {
    void getCDFromJarFile(String path);
    public void getCDFromJarFile(String path,long pid,String pname);
}
