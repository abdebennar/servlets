package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting server...");

        // Create a server on port 8080
        Server server = new Server(3000);

        // Create a context handler
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addEventListener(new AppContextListner());

        // Register your servlets (routes)
        context.addServlet(new ServletHolder(new SignUp()), "/signUp");
        context.addServlet(new ServletHolder(new SignIn()), "/signIn");
        context.addServlet(new ServletHolder(new Profile()), "/profile");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("Shutting down server...");
                server.stop();
                System.out.println("Server stopped.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        // Start the server
        server.start();
        // server.setStopAtShutdown(true);

        System.out.println("Server started on http://localhost:3000");

        // Keep server running
        server.join();
    }
}
