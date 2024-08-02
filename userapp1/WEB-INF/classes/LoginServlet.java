import java.io.*;
import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.ServletConfig;


public class LoginServlet extends HttpServlet {
    
    private PreparedStatement ps;

    public void init() throws ServletException {

        ServletContext ctxt = super.getServletContext();
        ServletConfig cfg = super.getServletConfig();

        String query = cfg.getInitParameter("query");
        Connection conn = (Connection) ctxt.getAttribute("dbconn");
    
        try{
            ps = conn.prepareStatement(query);
        }
        catch(SQLException sq) {
            System.out.println("Some Problem in init() method" + sq);
            ServletException se = new ServletException(sq.getMessage());
            throw se;
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();
        ServletConfig cfg = super.getServletConfig();
        
        pw.println("<html>");
        pw.print("<head><title> Login Page </title></head>");
        pw.println("<body>");

        String userid = req.getParameter("userid");
        String password = req.getParameter("password");

        String query = cfg.getInitParameter("query");

        
        ServletContext ctxt = super.getServletContext();
        Connection conn = (Connection) ctxt.getAttribute("dbconn");

        try{
            
            ps = conn.prepareStatement(query);
            ps.setString(1, userid);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                String username = rs.getString(1);
                pw.println("<h2>Welcome" + username+ "</h2>");
                pw.println("Enjoy here..");
            }
            else{
                pw.println("<h2>Invalid userid/password </h2><br>");
                pw.println("<h4>Try Again" + "<a href = 'login.html'> Click here</a></h4>");
                pw.println("<h4>New User" + "<a href = 'register.html'> Click here</a></h4>");
            }
        }
        catch (SQLException ex){
            System.out.println("Issue in doPost()" + ex);
            pw.println("<h2>Sorry!</h2>");
            pw.println("<p>Server is experience some issue</p>");
        }
        
        pw.println("</body");
        pw.println("</html>");

        pw.close();
    }

}
