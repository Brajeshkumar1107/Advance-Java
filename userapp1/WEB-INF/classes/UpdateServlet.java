import java.io.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Enumeration;


public class UpdateServlet extends HttpServlet {

    private PreparedStatement ps;

    public void init() throws ServletException {
        ServletContext ctxt = super.getServletContext();
        ServletConfig cfg = super.getServletConfig();

        String query = cfg.getInitParameter("query");
        Connection conn = (Connection) ctxt.getAttribute("dbconn");

        try{
            ps = conn.prepareStatement(query);
        }
        catch(SQLException ex) {
            System.out.println("Exception in init() method" + ex);
            ServletException se = new ServletException(ex.getMessage());
            throw se;
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        pw.println("<html>");
        pw.println("<head><title>Update Response</title></head>");
        pw.println("<body>");

        String userid = req.getParameter("userid");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, userid);

            int res = ps.executeUpdate();

            if(res == 1) {
                pw.println("<h2>Record updated</h2>");
                pw.println("<h2>Thank you for regestring with us</h2>");
                pw.println("<p>Now you can <a href='signin.html'>Login</a></p>");
            }
            else {
                pw.println("<h2>Sorry!</h2>");
                pw.println("<p>Registration rejected.</p>");
                pw.println("<p>Try again <a href='signup.html'>Register again</a></p>");
            }

            pw.println("</body> </html>");
            pw.close();

        } 
        catch (Exception e) {
            System.out.println("Exception in doPost() method" + e);
            pw.println("<h2>Sorry!</h2>");
            pw.println("<p>Server is experiencing some issues. Try later</p>");
        }

    }
}