package com.se.util;

import java.util.ArrayList;
import java.util.List;

public class SharedDataHelper {
    private static final double threshold=0.6;
    private static List<SharedDataType> listSharedData=new ArrayList<>();

    public static Double calculate(SharedData data){
        listSharedData.add(new SharedDataType("DAOFactory",0.0341,4478));
        listSharedData.add(new SharedDataType("String",0.6368,1118));
        listSharedData.add(new SharedDataType("Email",2.7310,9));

        double xdt=0;
        double udt=0;

        for(String s:data.getxDT()){
            for(SharedDataType temp:listSharedData){
                if(temp.getName().equals(s)){
                    if(temp.getIdtf()>threshold) {
                        xdt += temp.getIdtf();
                    }
                }
            }
        }

        boolean factory=false,string=false,email=false;
        for(String s:data.getuDT()){
            if("DAOFactory".equals(s)&&!factory){
                factory=true;
            }
            if("String".equals(s)){
                udt+=0.6368;
                string=true;
            }
            if("Email".equals(s)){
                udt+=2.7310;
                email=true;
            }
        }
        if(0==xdt){
            return 0.0;
        }
        if(0==udt){
            return 0.0;
        }
        return xdt/udt;
    }
}
