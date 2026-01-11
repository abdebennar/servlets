package org.example;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UrMomServlet extends HttpServlet {
    
    @Override
    public void init() throws ServletException {
        System.out.println("UrMomServlet initialized!");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Someone visited /urmom");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body><h1>ur mom is sexy</h1></body></html>");
    }
    
    @Override
    public void destroy() {
        System.out.println("UrMomServlet destroyed!");
    }
}