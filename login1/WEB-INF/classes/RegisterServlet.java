import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

public class RegisterServlet extends HttpServlet {

    private Connection conn;
    private PreparedStatement ps;

    public void init() throws ServletException {

        String url = "jdbc:oracle:thin:@//localhost:1521/xe";
        String user = "system";
        String password = "620677";

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to Database");
        } 
        catch (SQLException e) {

            System.out.println("Error in init() method" + e);
            ServletException se = new ServletException(e);
            throw se;
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        pw.println("<html>");
        pw.print("<head><title> Register Page </title></head>");
        pw.println("<body>");

        try {
            String userid = req.getParameter("userid");
            String pwd = req.getParameter("password");
            String username = req.getParameter("username");

            ps = conn.prepareStatement("select userid from users where userid = ?");
            ps.setString(1, userid);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                pw.println("UserId already exist!");
                pw.println("<a href='register.html'>Try again</a>");
            }
            else {
                ps = conn.prepareStatement("insert into users values(?,?,?)");
                ps.setString(1, userid);
                ps.setString(2, pwd);
                ps.setString(3, username);

                int result = ps.executeUpdate();
                if (result > 0) {
                    System.out.println("Record inserted Successful");
                    pw.println("Do you want to login <a href = 'login.html'>Click Here..</a>");
                }
                else{
                    System.out.println("Record not inserted to Database");
                    pw.println("Try Again! <a href = 'register.html'>Click Here..</a>");
                }
            }
        } 
        catch (SQLException e) {
                
            System.out.println("Error in doPost Method" + e);
            pw.println("<h2>Sorry!</h2>");
            pw.println("<p>Server is experience some issue</p>");
        }

        pw.println("</body>");
        pw.println("</html>");
        pw.close();
    }

    public void destroy() {
        try{
            conn.close();
        }
        catch(SQLException e){
            System.out.println("Issue in Destroy() method" + e );
        }
    }
}