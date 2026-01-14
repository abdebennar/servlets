package org.cinema;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting server...");

        Server server = new Server(3000);
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setResourceBase("src/main/webapp");
        context.setParentLoaderPriority(true);

        server.setHandler(context);

        context.addEventListener(new AppContextListner());

        FilterHolder profileFilterHolder = new FilterHolder(new ProfileFilter());
        context.addFilter(profileFilterHolder, "/profile", null);

        // servlets (routes)
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

        System.out.println("Server started on http://localhost:3000");

        // Keep server running
        server.join();
    }
}
