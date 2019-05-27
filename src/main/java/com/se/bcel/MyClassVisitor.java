package com.se.bcel;

import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.MethodGen;


import java.util.*;

public class MyClassVisitor extends EmptyVisitor {
    private JavaClass clazz;
    private ConstantPoolGen constantPool;
    private final List<String> jarClass;
    private final DynamicCallManager dynamicCallManager=new DynamicCallManager();
    private Map<String,Integer> classCall=new HashMap<>();
    private Map<String,Integer> occurrence=new HashMap<>();
    private Map<Set<String>, Set<String>> sharedDataType=new HashMap<>();

    public MyClassVisitor(JavaClass javaClass,List<String> jarClass){
        this.clazz=javaClass;
        this.jarClass=jarClass;
        this.constantPool=new ConstantPoolGen(clazz.getConstantPool());
    }

    public void visitJavaClass(JavaClass javaClass){
        //visit super classes
        JavaClass[] superClazzes=null;
        try {
            superClazzes=this.clazz.getSuperClasses();
        }catch (ClassNotFoundException e){
            superClazzes=null;
//            e.printStackTrace();
        }
        if(null!=superClazzes){
            for(int i=0;i<superClazzes.length-1;i++){
                String className=superClazzes[i].getClassName();
                if(this.jarClass.contains(className)&&!className.equals(clazz.getClassName())){
                    addClassToClassCall(classCall,className,1);
                }
            }
        }
        //visit constant pool
        javaClass.getConstantPool().accept(this);
        //visit method call
        Method[] methods=javaClass.getMethods();
        for(int i=0;i<methods.length;i++){
            Method method=methods[i];
            dynamicCallManager.retrieveCalls(method,javaClass);
            dynamicCallManager.linkCalls(method);
            method.accept(this);
        }


    }

    public void visitConstantPool(ConstantPool constantPool){
        for(int i=0;i<constantPool.getLength();i++){
            Constant constant=constantPool.getConstant(i);
            if(null==constant){
                continue;
            }else if(7==constant.getTag()){
                String constantClass=constantPool.constantToString(constant);
                if(this.jarClass.contains(constantClass)&&!constantClass.equals(clazz.getClassName())){
                    addClassToClassCall(classCall,constantClass,1);
                }
            }
        }
    }

    public void visitMethod(Method method){
        MethodGen methodGen=new MethodGen(method,this.clazz.getClassName(),constantPool);
        MyMethodVisitor myMethodVisitor=new MyMethodVisitor(methodGen,clazz,jarClass);
        Map<String,Integer> methodCall=myMethodVisitor.startAndGetMethodCall();
        for(String str:methodCall.keySet()){
            addClassToClassCall(this.classCall,str,methodCall.get(str));
        }
        Map<String,Integer> temp=myMethodVisitor.getOccurrence();
        for(String str:temp.keySet()) {
            addClassToClassCall(this.occurrence, str, temp.get(str));
        }
        Map<Set<String>,Set<String>> singleMethodSharedDataType=myMethodVisitor.getSharedDataType();
        for(Set<String> set:singleMethodSharedDataType.keySet()){
            Set<String> shared=singleMethodSharedDataType.get(set);
            Object list[]=set.toArray();
            for(String str:shared){
                MyMethodVisitor.addSharedData(this.sharedDataType,(String)list[0],(String)list[1],str);
            }
        }
    }

    public static void addClassToClassCall(Map<String,Integer> classCall,String name,int num){
        if(classCall.keySet().contains(name)){
            classCall.put(name,classCall.get(name)+num);

        }else{
            classCall.put(name,num);
        }
    }


    public MyClassVisitor start(){
        visitJavaClass(clazz);
        return this;
    }

    public Map<String,Integer> getClassCall(){
        return this.classCall;
    }

    public Map<String, Integer> getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(Map<String, Integer> occurrence) {
        this.occurrence = occurrence;
    }

    public void setSharedDataType(Map<Set<String>, Set<String>> sharedDataType) {
        this.sharedDataType = sharedDataType;
    }

    public Map<Set<String>, Set<String>> getSharedDataType() {
        return sharedDataType;
    }
}
