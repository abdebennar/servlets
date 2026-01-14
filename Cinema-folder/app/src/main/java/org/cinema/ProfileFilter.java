package org.cinema;

import jakarta.servlet.Filter;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(value = "/profile/*")
public class ProfileFilter implements Filter {

    // protected Database database;
    // protected ApplicationContext springContext;
    @Override
    public void init(jakarta.servlet.FilterConfig filterConfig)
            throws jakarta.servlet.ServletException {

        // springContext = (ApplicationContext) filterConfig.getServletContext().getAttribute("springContext");
        // database = springContext.getBean(Database.class);
        System.out.println("Profile Filter initialized!");

    }

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response,
            jakarta.servlet.FilterChain chain) throws jakarta.servlet.ServletException, java.io.IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {

            System.out.println("Profile Filter checking authentication...");

            String sessionId = this.getCookieValue(httpRequest, "jwt");

            String userId = JwtUtil.validateTokenAndGetUserId(sessionId);
            if (userId != null) {

                request.setAttribute("userID", userId);
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
