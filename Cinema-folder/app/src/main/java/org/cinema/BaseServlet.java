package org.cinema;

import org.springframework.context.ApplicationContext;

import jakarta.servlet.http.HttpServlet;

public abstract class BaseServlet extends HttpServlet {

    protected Database database;
    protected ApplicationContext springContext;

    @Override
    public void init() {
        // Get Spring context once
        springContext = (ApplicationContext) getServletContext().getAttribute("springContext");

        // Get all common beans
        database = springContext.getBean(Database.class);

        System.out.println("✅ " + getClass().getSimpleName() + " initialized with beans");

        // Call custom init for child classes
        initServlet();
    }

    // Override this in child servlets if they need custom initialization
    protected void initServlet() {
        // Optional: override in child classes
    }

    // Helper method to get any bean
    protected <T> T getBean(Class<T> beanClass) {
        return springContext.getBean(beanClass);
    }
}
