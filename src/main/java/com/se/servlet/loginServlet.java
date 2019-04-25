package com.se.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet{
//    private static final long serialVersionUID = 5417488369543075097L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("调用doget");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String message = "{\"message\":\"登录成功\"}";
        response.setContentType("application/json;charset=utf-8");
//        if("a".equals(username) && "b".equals(password)) {
            response.getWriter().write(message);
//        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    public loginServlet(){
        super();
    }
}