package org.cinema;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SignIn extends BaseServlet {

    @Override
    public void init() {
        System.out.println("SignIn initialized!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        response.getWriter().println("""
			 <html>
			   <body>
				 <h2>Sign In</h2>
				 <form method="post" action="/signIn">
					Phone: <input name="phone"><br>
					Password: <input type="password" name="password"><br>
					<button type="submit">Login</button>
				 </form>
			   </body>
			 </html>
		""");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String phone = request.getParameter("phone");
            String password = request.getParameter("password");

            System.out.println("Authenticating user with phone: " + phone + " and password: " + password);
            String jwtToken = database.AuthenticateUser(phone, password);

            System.out.println("JWT Token: " + jwtToken);

            if (jwtToken != null) {
                System.out.println("User authenticated, JWT generated");

                // Create JWT cookie
                Cookie jwtCookie = new Cookie("jwt", jwtToken);
                jwtCookie.setHttpOnly(true); // Prevents JavaScript access (XSS protection)
                jwtCookie.setSecure(false); // Set to true in production with HTTPS
                jwtCookie.setMaxAge(86400); // 24 hours (matches JWT expiration)
                jwtCookie.setPath("/"); // Available to entire application

                response.addCookie(jwtCookie);

                response.setStatus(HttpServletResponse.SC_OK);
                response.sendRedirect("/profile");
            } else {
                throw new Exception("Authentication failed");
            }

        } catch (Throwable e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("<html><body><h2>Login Failed!</h2></body></html>");
        }
    }

    @Override
    public void destroy() {
        System.out.println("SignIn destroyed!");
    }
}
