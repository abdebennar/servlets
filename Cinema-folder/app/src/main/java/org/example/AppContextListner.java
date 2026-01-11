package org.example;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListner implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();
        String appName = context.getInitParameter("appName");
        context.setAttribute("appName", appName != null ? appName : "DefaultApp");
        System.out.println("Application " + context.getAttribute("appName") + " initialized.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        System.out.println("Application " + context.getAttribute("appName") + " destroyed.");
    }
}
