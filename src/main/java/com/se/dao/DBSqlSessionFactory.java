package com.se.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class DBSqlSessionFactory {

    private static SqlSessionFactory ourInstance=null;

    public static SqlSessionFactory getInstance() {
        if(ourInstance==null){
            getDBSqlSessionFactory();
        }
        return ourInstance;
    }

    private static void getDBSqlSessionFactory() {
        String resource="db/SqlMapConfig.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            ourInstance=new SqlSessionFactoryBuilder().build(inputStream);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    private DBSqlSessionFactory(){}

    public static void main(String[] args){
        SqlSession sqlSession=DBSqlSessionFactory.getInstance().openSession();
        sqlSession.close();
    }


}
