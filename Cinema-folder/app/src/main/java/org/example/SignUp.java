package org.example;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet
public class SignUp extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("SignUp initialized!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO: Server that as a static HTML file instead of hardcoding it here
        response.setContentType("text/html");
        response.getWriter().println("""
			 <html>
              <body>
                <h2>Sign Up</h2>
                <form method="post" action="/signUp">
                  First name: <input name="firstName"><br>
                  Last name: <input name="lastName"><br>
                  Phone: <input name="phone"><br>
                  Password: <input type="password" name="password"><br>
                  <button type="submit">Register</button>
                </form>
              </body>
            </html>
        """);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String phone = request.getParameter("phone");
            String password = request.getParameter("password");

            // TODO: Add user registration logic here (e.g., save to database)
            Database db = new Database();
            db.RegisterUser(firstName, lastName, phone, password);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<html><body><h2>Registration Seccuss</h2></body></html>");
            response.sendRedirect("/signIn");

        } catch (Throwable e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("<html><body><h2>Registration Failed!</h2></body></html>");
        }
    }

    @Override
    public void destroy() {
        System.out.println("SignUp destroyed!");
    }
}
