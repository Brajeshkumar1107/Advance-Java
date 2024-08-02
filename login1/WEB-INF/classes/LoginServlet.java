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

    private Connection conn;
    private PreparedStatement ps;
    
    
    public void init() throws ServletException {
        // String url = "jdbc:oracle:thin:@//localhost:1521/xe";
        // String user = "system";
        // String password = "620677";
        ServletConfig cfg = super.getServletConfig();
        String url = cfg.getInitParameter("dbUrl");
        String user = cfg.getInitParameter("systemName");
        String password = cfg.getInitParameter("password");
    
        try{

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to Database");

            // String dbName = cfg.getInitParameter("dbName");
            // String query = "select username from" + dbName + "where userid = ? and password = ?";
            // System.out.println(query);
            ps = conn.prepareStatement("select username from ? where userid = ? and password = ?");

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
        pw.println("<html>");
        pw.print("<head><title> Login Page </title></head>");
        pw.println("<body>");

        try{
            
             = super.getServletConfig();
            String userid = req.getParameter("userid");
            String password = req.getParameter("password");
            String dbname = cfg.getInitParameter("dbName");
            ps.setString(1, dbname);
            ps.setString(2, userid);
            ps.setString(3, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                String username = rs.getString(1);
                pw.println("<h2>Welcome" + username+ "</h2>");
                pw.println("Enjoy here..");
            }
            else{
                pw.println("Invalid userid/password <br>");
                pw.println("Try Again" + "<a href = 'login.html'> Click here</a>");
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

    public void destroy(){
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Issue in destroy() method" + e);
        }
    }
}