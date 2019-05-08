package com.se.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TreeViewUtil {
    private String text;
    private List<TreeViewUtil> nodes;

    private TreeViewUtil(){

    }

    public TreeViewUtil(String text) {
        this.text = text;
        this.nodes=new ArrayList<>();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<TreeViewUtil> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeViewUtil> nodes) {
        this.nodes = nodes;
    }

    public void build(List<Path> pathList){
        for(Path p:pathList){
            build(this,p);
        }
    }

    private void build(TreeViewUtil root,Path path){
        boolean hasDir=false;
        String rootName=path.getName(0).toString();
        TreeViewUtil matchedNode=null;
        for(TreeViewUtil node:root.getNodes()){
            if(node.getText().equals(rootName)){
                hasDir=true;
                matchedNode=node;
                break;
            }
        }
        if(!hasDir){
            TreeViewUtil tempNode=new TreeViewUtil(rootName);
            if(path.getNameCount()>1)
            {
                build(tempNode,path.subpath(1,path.getNameCount()));
            }
            root.getNodes().add(tempNode);
        }else if(null!=matchedNode){
            if(path.getNameCount()>1){
                build(matchedNode, path.subpath(1,path.getNameCount()));
            }
        }

    }

}
