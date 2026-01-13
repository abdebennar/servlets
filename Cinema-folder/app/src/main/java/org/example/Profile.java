package org.example;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet
public class Profile extends HttpServlet {

    @Override
    public void init() {
        System.out.println("Profile initialized!");
    }

    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response)
            throws jakarta.servlet.ServletException, java.io.IOException {
        response.setContentType("text/html");
        response.getWriter().println("<h1>Profile</h1>");
    }

    @Override
    public void destroy() {
        System.out.println("Profile destroyed!");
    }
}
