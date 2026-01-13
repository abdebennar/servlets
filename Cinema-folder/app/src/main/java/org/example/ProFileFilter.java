package org.example;

import jakarta.servlet.Filter;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/profile/*")
public class ProFileFilter implements Filter {

    @Override
    public void init(jakarta.servlet.FilterConfig filterConfig)
            throws jakarta.servlet.ServletException {
        System.out.println("Profile Filter initialized!");
    }

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response,
            jakarta.servlet.FilterChain chain) throws jakarta.servlet.ServletException, java.io.IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            // log 
            System.out.println("##################### Profile doFilter called ###################");

            String sessionId = this.getCookieValue(httpRequest, "sessionId");

            Database db = new Database();
            if (db.isAuthenticated(sessionId)) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendRedirect("/signIn");
            }
        } catch (Exception e) {
            httpResponse.sendRedirect("/signIn");
        }

    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
