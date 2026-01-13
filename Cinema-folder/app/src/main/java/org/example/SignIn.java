package org.example;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SignIn extends HttpServlet {

    @Override
    public void init() throws ServletException {
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
				   Phone: <input phone="phone"><br>
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

            Database db = new Database();
            String sessionId = db.AuthenticateUser(phone, password);
            if (sessionId != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("<html><body><h2>Login Successful!</h2></body></html>");
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
