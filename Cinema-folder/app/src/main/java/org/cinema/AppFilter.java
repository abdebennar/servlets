package org.cinema;

import org.slf4j.Logger;

import jakarta.servlet.Filter;

public class AppFilter implements Filter {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(AppFilter.class);

    @Override
    public void init(jakarta.servlet.FilterConfig filterConfig)
            throws jakarta.servlet.ServletException {

        logger.info("App Filter initialized!");
    }

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response,
            jakarta.servlet.FilterChain chain) throws jakarta.servlet.ServletException, java.io.IOException {

        logger.info("Receving a request from IP: " + request.getRemoteAddr());
        chain.doFilter(request, response);

    }
}
