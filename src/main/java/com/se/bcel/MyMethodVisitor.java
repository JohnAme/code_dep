package com.se.bcel;


import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.*;

import java.util.*;


public class MyMethodVisitor extends EmptyVisitor {
    JavaClass clazz;
    private MethodGen methodGen;
    private ConstantPoolGen constantPoolGen;
    private final List<String> jarClass;
    private Map<String,Integer> methodCall=new HashMap<>();
    private Map<String,Integer> occurrence=new HashMap<>();
    private Map<Set<String>, Set<String>> sharedDataType=new HashMap<>();

    public Map<String, Integer> getOccurrence() {
        return occurrence;
    }

    public MyMethodVisitor(MethodGen methodGen, JavaClass javaClass,List<String> jarClass){
        this.clazz=javaClass;
        this.methodGen=methodGen;
        this.jarClass=jarClass;
        this.constantPoolGen=methodGen.getConstantPool();
    }

    public Map<String,Integer> startAndGetMethodCall(){
        start();
        return methodCall;
    }

    public void start(){
        if (methodGen.isAbstract() || methodGen.isNative())
            return;
        for (InstructionHandle instructionHandle = methodGen.getInstructionList().getStart();
             instructionHandle != null;
             instructionHandle = instructionHandle.getNext()) {
            Instruction instruction=instructionHandle.getInstruction();
            short opcode=instruction.getOpcode();
            if(null==(InstructionConst.getInstruction(opcode))
                    || (instruction instanceof ConstantPushInstruction)
                    || (instruction instanceof ReturnInstruction)){
                instruction.accept(this);
            }
        }
    }

    @Override
    public void visitINVOKEVIRTUAL(INVOKEVIRTUAL i) {
        addMethodCall(i);
    }

    @Override
    public void visitINVOKEINTERFACE(INVOKEINTERFACE i) {
        addMethodCall(i);
    }

    @Override
    public void visitINVOKESPECIAL(INVOKESPECIAL i) {
        addMethodCall(i);
    }

    @Override
    public void visitINVOKESTATIC(INVOKESTATIC i) {
        addMethodCall(i);
    }

    @Override
    public void visitINVOKEDYNAMIC(INVOKEDYNAMIC i) {
//        addMethodCall(i);
    }

    private void addMethodCall(InvokeInstruction instruction){
        ReferenceType referenceType= instruction.getReferenceType(constantPoolGen);
        Type[] types=instruction.getArgumentTypes(constantPoolGen);
        String refClass=String.format("%s",referenceType);
        if(jarClass.contains(refClass)&&!clazz.getClassName().equals(refClass)){
            //code dependency
            MyClassVisitor.addClassToClassCall(methodCall,refClass,1);
            //count occurrence
            for(int i=0;i<types.length;i++){
                MyClassVisitor.addClassToClassCall(occurrence,types[i].toString(),1);
                addSharedData(sharedDataType,clazz.getClassName(),refClass,types[i].toString());
            }
        }
    }

    public Map<Set<String>, Set<String>> getSharedDataType() {
        return sharedDataType;
    }

    public static void addSharedData(Map<Set<String>,Set<String>> sharedData, String classA, String classB, String type){
        if(classA.equals(classB)){
            return;
        }
        Set<String> set=new HashSet<>();
        set.add(classA);
        set.add(classB);
        if(sharedData.keySet().contains(set)){
            Set temp=sharedData.get(set);
            temp.add(type);
            sharedData.put(set,temp);
        }else{
            Set<String> temp=new HashSet<>();
            temp.add(type);
            sharedData.put(set,temp);
        }
    }




}
