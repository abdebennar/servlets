package org.cinema;

public class Profile extends BaseServlet {

    @Override
    public void init() {
        super.init();
        System.out.println("Profile initialized!");
    }

    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response)
            throws jakarta.servlet.ServletException, java.io.IOException {

        String userID = (String) request.getAttribute("userID");

        String[] names = database.getNames(userID);

        request.setAttribute("firstName", names[0]);
        request.setAttribute("lastName", names[1]);
        // request.getRequestDispatcher("./Profile.jsp").forward(request, response);
        request.getRequestDispatcher("/views/Profile.jsp").forward(request, response);
        response.setContentType("text/html");
    }

    @Override
    public void destroy() {
        System.out.println("Profile destroyed!");
    }
}
