package com.se.service;

import java.nio.file.Path;

public interface GenerateGraphService {
    public boolean generateNewGraph(String pname,Path codeDir, Path dbDir);

    public boolean findByName(String pname);

    public long DataDPSize(String pname);
}
