package org.cinema;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListner implements ServletContextListener {

    private ApplicationContext springContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        springContext = new AnnotationConfigApplicationContext(AppConfig.class);
        sce.getServletContext().setAttribute("springContext", springContext);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Destroying Spring context...");
        if (springContext instanceof AnnotationConfigApplicationContext) {
            ((AnnotationConfigApplicationContext) springContext).close();
        }
    }
}
