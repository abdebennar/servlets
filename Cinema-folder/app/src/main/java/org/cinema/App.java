package org.cinema;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;

import jakarta.servlet.http.HttpServlet;

public class App {

    public static void main(String[] args) throws Exception {

        Logger logger = org.slf4j.LoggerFactory.getLogger(App.class);

        logger.info("Starting server...");
        Server server = new Server(3001);
        WebAppContext context = new WebAppContext();

        try {
            context.setContextPath("/");
            context.setResourceBase("src/main/webapp");
            context.setParentLoaderPriority(true);

            server.setHandler(context);

            context.addEventListener(new AppContextListner());

            FilterHolder profileFilterHolder = new FilterHolder(new ProfileFilter());
            FilterHolder appFilterHolder = new FilterHolder(new AppFilter());
            context.addFilter(profileFilterHolder, "/profile", null);
            context.addFilter(appFilterHolder, "/*", null);

            // servlets(routes)
            context.addServlet(new ServletHolder(new HttpServlet() {
                @Override
                protected void doGet(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp)
                        throws jakarta.servlet.ServletException, java.io.IOException {
                    resp.sendRedirect("/profile");
                }
            }), "/");
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
            System.out.println("Server started on http://localhost:3001");
            // Keep server running
            server.join();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
