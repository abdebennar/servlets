package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting server...");

        // Create a server on port 8080
        Server server = new Server(8080);

        // Create a context handler
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Register your servlets (routes)
        context.addServlet(new ServletHolder(new HelloWorldServlet()), "/");
        context.addServlet(new ServletHolder(new UrMomServlet()), "/urmom");

        // Start the server
        server.start();

        System.out.println("Server started on http://localhost:8080");
        System.out.println("Routes:");
        System.out.println("  / -> Hello World");
        System.out.println("  /urmom -> ur mom is sexy");
        System.out.println("Press Ctrl+C to stop");

        // Keep server running
        server.join();
    }
}
