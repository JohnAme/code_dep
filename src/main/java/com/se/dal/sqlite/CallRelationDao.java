package com.se.dal.sqlite;

import com.se.dal.sqlite.type.CallRelation;
import com.se.dal.sqlite.type.CallRelationList;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class CallRelationDao {

    public static CallRelationList callAsRelation(Path callDB){
        if(!Files.exists(callDB)){
            System.err.println("call.db doesn't exist at:"+callDB.toString());
            return new CallRelationList();
        }
        Connection con;
        Statement stat;

        CallRelationList callRelationList=new CallRelationList();
        try {
            Class.forName("org.sqlite.JDBC");
            con= DriverManager.getConnection("jdbc:sqlite:"+callDB.toString());
            con.setAutoCommit(false);

            System.out.printf("Opened %s successfully\n", callDB.toString());

            String query="select * from callGraph;";
            stat=con.createStatement();
            ResultSet rs=stat.executeQuery(query);
            while (rs.next()){
                String caller=rs.getString("caller");
                String callee=rs.getString("callee");
//                System.out.println("caller:"+caller+"--"+"callee:"+callee);

                if(caller.startsWith("L")){
                    caller=caller.substring(1);
                }
                if(callee.startsWith("L")){
                    callee=callee.substring(1);
                }
                String callerMethod=sqlFormatToIdFormatInCallDB(caller);
                String calleeMethod=sqlFormatToIdFormatInCallDB(callee);
                String callerClass=callerMethod.split("#")[0];
                String calleeClass=calleeMethod.split("#")[0];

                CallRelation callRelation=new CallRelation(callerClass,calleeClass,callerMethod,calleeMethod);
                callRelationList.add(callRelation);
            }

            rs.close();
            System.out.println("Table reqs parsed successfully");
            System.out.printf("Closed %s successfully\n", FilenameUtils.getName(callDB.toString()));
            stat.close();
            con.close();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.printf("Read %d call relations from call.db", callRelationList.size());
        return callRelationList;
    }

    private static String sqlFormatToIdFormatInCallDB(String sqlFormat) {
        String[] tokens = sqlFormat.split("\\.");

//        System.out.println(sqlFormat);
        // find Java method
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].startsWith("MPEGecoder")) continue;
            if (Character.isUpperCase(tokens[i].charAt(0))) {
                StringBuilder sb = new StringBuilder();

                sb.append(tokens[i].split("\\$")[0]);
                sb.append("#");

                sb.append(tokens[i + 1].split("\\(")[0]);
                return sb.toString();
            }
        }

//        find JSP method
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("jsp")) {

                for (int j = i+1; j < tokens.length; j++) {
                    if (tokens[j].endsWith("_jsp")) {

                        for (int k = i + 1; k <= j; k++) {
                            sb.append(tokens[k]);
                            if (k != j) {
                                sb.append(".");
                            }
                        }
                        sb.append("#");
                        sb.append(tokens[j + 1].split("\\(")[0]);
                    }
                }
            }
        }
        // [keng]
        String res = sb.toString().replace("_002d", "-");
//        System.out.println("res:"+res);
        return res;
    }

    public static void main(String[] args){
        Path path= Paths.get("D:\\sqlite\\call.db");
        callAsRelation(path);
    }
}
