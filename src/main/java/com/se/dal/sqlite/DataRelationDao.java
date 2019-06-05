package com.se.dal.sqlite;

import com.se.dal.sqlite.type.*;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataRelationDao {

    private DataRelationList usage=new DataRelationList();
    private DataRelationList fullDR=new DataRelationList();

    public DataRelationDao(Path dir){
        dataAsRelation(dir);
    }

    public DataRelationList dataAsRelation(Path dir){
        if(!Files.exists(dir)||!Files.isDirectory(dir)){
            System.err.println("error reading data relation at dir:"+dir.toString());
        }
        Path faPath=dir.resolve("test1.db");
        Path fmPath=dir.resolve("test2.db");
        Path ppPath=dir.resolve("test3.db");

        List<FieldMonitor> faList = relationParser(faPath, DataRelationType.FieldAccess);
        List<FieldMonitor> fmList = relationParser(fmPath, DataRelationType.FieldModification);
        /**
         * @date 2017/10/31
         * @description 暂时注释掉
         * */
        List<FieldMonitor> ppList = relationParser(ppPath, DataRelationType.ParameterPass);

        DataRelationList dr=new DataRelationList();
        usage=getUsageRelations(faList);
        usage.addAll(getUsageRelations(fmList));

//        System.out.println("shared0:"+fullDR.size());
        fullDR.addAll(getShareFieldRelations(faList));
//        System.out.println("shared1:"+fullDR.size());
        fullDR.addAll(getShareFieldRelations(fmList));
//        System.out.println("shared2:"+fullDR.size());
        fullDR.addAll(getShareFieldRelations(ppList));
//        System.out.println("shared3:"+fullDR.size());

        return fullDR;
    }

    private static DataRelationList getUsageRelations(List<FieldMonitor> fieldMonitorsList) {
        DataRelationList dataRelationList = new DataRelationList();

        for (FieldMonitor monitor : fieldMonitorsList) {

            DataRelation dr = new DataRelation();
            dr.callerClass = getClassNameFromDBFormat(monitor.getMcSignature());
            dr.calleeClass = getClassNameFromDBFormat(monitor.getfSignature());
            dr.callerMethod = monitor.getMcSignature() + "#" + monitor.getMethodName();
            dr.calleeMethod = "none";
            dr.type = monitor.getfSignature();
            dr.hashcode = monitor.getfHashcode();
            dr.isUsage = true;
            dataRelationList.add(dr);
        }

        return dataRelationList;
    }

    private static DataRelationList getShareFieldRelations(List<FieldMonitor> fieldMonitorsList) {
        // find method access same Field
        Map<String, List<String>> methodAccessSameFieldMap = new LinkedHashMap<>();
        for (FieldMonitor monitor : fieldMonitorsList) {
            if (!monitor.getfHashcode().equals("null")
                    && !monitor.getfHashcode().equals("static")
                    && !monitor.getfHashcode().equals("primitive")) {
                String accessFieldIdentify = getClassNameFromDBFormat(monitor.getfSignature()) + "#" + monitor.getfHashcode();
//                accessFieldIdentify = DAOFactory#3387681
//                System.out.println(" accessFieldIdentify = " + accessFieldIdentify );

                String methodIdentify = getClassNameFromDBFormat(monitor.getMcSignature()) + "#" + monitor.getMethodName();

                if (!methodAccessSameFieldMap.containsKey(accessFieldIdentify)) {
                    List<String> methodIdentifyList = new ArrayList<>();
                    methodIdentifyList.add(methodIdentify);
                    methodAccessSameFieldMap.put(accessFieldIdentify, methodIdentifyList);
                } else {
                    List<String> methodIdentifyList = methodAccessSameFieldMap.get(accessFieldIdentify);
                    if (!methodIdentifyList.contains(methodIdentify)) {
                        methodIdentifyList.add(methodIdentify);
                    }
                    methodAccessSameFieldMap.put(accessFieldIdentify, methodIdentifyList);
                }
            }
        }

        System.out.println(" methodAccessSameFieldMap = " + methodAccessSameFieldMap.size());
//        DataRelationList dataRelationList = new DataRelationList();
        DataRelationList dataRelationList = new DataRelationList();
        for (String accessFieldIdentify : methodAccessSameFieldMap.keySet()) {
//            System.out.println(" accessFieldIdentify = " + accessFieldIdentify );
            if (methodAccessSameFieldMap.get(accessFieldIdentify).size() > 1) {
                for (DataRelation dr : getDataRelationAccessListSameFiled(accessFieldIdentify,
                        methodAccessSameFieldMap.get(accessFieldIdentify))) {
                    if (!dr.getCalleeMethod().equals(dr.getCallerMethod())) {
                        dataRelationList.add(dr);
                    }
                }
            }
        }
        return dataRelationList;
    }

    private static DataRelationList getDataRelationAccessListSameFiled(String accessFieldIdentify, List<String> methodList) {
        DataRelationList dataRelationList = new DataRelationList();

        String type = accessFieldIdentify.split("#")[0];
        String hashcode = accessFieldIdentify.split("#")[1];

        for (int i = 0; i < methodList.size(); i++) {
            for (int j = i + 1; j < methodList.size(); j++) {
                DataRelation dr = new DataRelation();

                dr.callerClass = (methodList.get(i).split("#")[0]);
                dr.calleeClass = (methodList.get(j).split("#")[0]);
                dr.callerMethod = (methodList.get(i));
                dr.calleeMethod = (methodList.get(j));
                dr.type = type;
                dr.hashcode = hashcode;
                dr.isUsage = false;
                dataRelationList.add(dr);
            }
        }

        return dataRelationList;
    }

    private static List<FieldMonitor> relationParser(Path dbPath, DataRelationType dataRelationType) {

        if(!Files.exists(dbPath)){
            System.err.println("database file doesn't exist at:"+dbPath.toString());
            return new ArrayList<>();
        }

        Connection con;
        Statement stmt;

        List<FieldMonitor> fieldMonitorList = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbPath.toString());
            con.setAutoCommit(false);

            System.out.printf("Opened %s successfully\n", FilenameUtils.getName(dbPath.toString()));
            stmt = con.createStatement();

            String dbFileName = "";
            if (dataRelationType.equals(DataRelationType.FieldAccess)) {
                dbFileName = "fieldAccess";
            } else if (dataRelationType.equals(DataRelationType.FieldModification)) {
                dbFileName = "fieldModification";
            } else if (dataRelationType.equals(DataRelationType.ParameterPass)) {
                dbFileName = "parameterPass";
            }

            String sql = "SELECT * FROM " + dbFileName + ";";
            ResultSet rs = stmt.executeQuery(sql);

            /**
             * 2018.1.18
             * */
            while (rs.next()) {
                if (dataRelationType.equals(DataRelationType.FieldAccess)) {
                    FieldAccess fa = new FieldAccess();
                    fa.setcSignature(rs.getString("cSignature").trim());
                    fa.setoHashcode(rs.getString("oHashcode").trim());
                    fa.setfSignature(rs.getString("fSignature").trim());
                    fa.setfHashcode(rs.getString("fHashcode").trim());
                    fa.setfName(rs.getString("fName").trim());
                    fa.setMcSignature(rs.getString("McSignature").trim());
                    fa.setMethodName(rs.getString("methodName").trim());
                    fa.setMethodSignature(rs.getString("methodSignature").trim());
                    fa.setType(DataRelationType.FieldAccess);
                    fieldMonitorList.add(fa);
                } else if (dataRelationType.equals(DataRelationType.FieldModification)) {
                    FieldModification fm = new FieldModification();
                    fm.setcSignature(rs.getString("cSignature").trim());
                    fm.setoHashcode(rs.getString("oHashcode").trim());
                    fm.setfSignature(rs.getString("fSignature").trim());
                    fm.setfHashcode(rs.getString("fHashcode").trim());
                    fm.setfName(rs.getString("fName").trim());
                    fm.setMcSignature(rs.getString("McSignature").trim());
                    fm.setMethodName(rs.getString("methodName").trim());
                    fm.setMethodSignature(rs.getString("methodSignature").trim());
                    fm.setNewValue(rs.getString("newValue").trim());
                    fm.setType(DataRelationType.FieldModification);
                    fieldMonitorList.add(fm);
                } else if (dataRelationType.equals(DataRelationType.ParameterPass)) {
                    ParameterPass pp = new ParameterPass();
                    pp.setfSignature(rs.getString("fSignature").trim());
                    pp.setfHashcode(rs.getString("fHashcode").trim());
                    pp.setfName(rs.getString("fName").trim());
                    pp.setMcSignature(rs.getString("McSignature").trim());
                    pp.setMethodName(rs.getString("methodName").trim());
                    pp.setMethodSignature(rs.getString("methodSignature").trim());
                    pp.setType(DataRelationType.ParameterPass);
                    fieldMonitorList.add(pp);
                }
            }

            rs.close();

            System.out.println("Table " + dataRelationType.toString() + " parsed successfully");
            System.out.printf("Closed %s successfully\n", dataRelationType.toString());
            stmt.close();
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.printf("Read %d " + dataRelationType + " field relations from data.db\n", fieldMonitorList.size());

        return fieldMonitorList;
    }

    private static String getClassNameFromDBFormat(String dbFormat) {
        String className = "";
        if (!dbFormat.endsWith("_jsp;")) {
            // Ledu/ncsu/csc/itrust/dao/DAOFactory; -> DAOFactory
            String tokens[] = dbFormat.split("/");
            className = tokens[tokens.length - 1].split(";")[0];
        } else {
            // Lorg/apache/jsp/auth/patient/viewVisitedHCPs_jsp -> auth.patient.viewVisitedHCPs_jsp
            dbFormat = dbFormat.replace("_002d", "-");
            className = dbFormat.split("/jsp/")[1].replace("/", ".").split(";")[0];
        }

//        System.out.println(" className = " + className.split("\\$")[0] );
        return className.split("\\$")[0];
    }

    public DataRelationList getUsage() {
        return usage;
    }

    public DataRelationList getFullDR() {
        return fullDR;
    }

    public static void main(String[] args){
        Path relation= Paths.get("C:\\Users\\Jerry\\Desktop\\material\\iTrust\\relation");
        DataRelationDao dao=new DataRelationDao(relation);
//        dataAsRelation(relation);
        DataRelationList fullDR=dao.getFullDR();

        System.out.println("full:"+fullDR.size());
        int i=0;
        for(DataRelation dr:dao.getFullDR()){
            if(!dr.isUsage){
                i++;
            }
        }
        System.out.println("not usage:"+i);
        i=0;
        System.out.println("usage:"+dao.getUsage().size());
        for(DataRelation dr:dao.getUsage()){
            if(dr.isUsage){
                i++;
            }
        }
        System.out.println("usage:"+i);
    }
}
