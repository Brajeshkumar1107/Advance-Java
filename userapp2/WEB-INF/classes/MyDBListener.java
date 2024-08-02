import javax.servlet.*;
import java.sql.*;

public class MyDBListener implements ServletContextListener {

    private Connection conn;

    public void contextInitialized(ServletContextEvent ex) {

        ServletContext ctxt = ex.getServletContext();

        String dburl = ctxt.getInitParameter("dbUrl");
        String systemName = ctxt.getInitParameter("systemName");
        String password = ctxt.getInitParameter("password");

        try{

            conn = DriverManager.getConnection(dburl, systemName, password);
            System.out.println("Connection to Database successful");
            ctxt.setAttribute("dbconn", conn);

        }
        catch(Exception e) {

            System.out.println("Exception in contextInitialized to connect with database" + e.getMessage());
            
        }
    }

    public void contextDestroyed(ServletContextEvent ex) {
        try{
            if (conn != null) conn.close();
        }
        catch (Exception e) {
            System.out.println("Exception in context destroyed" + e.getMessage());
        }
    }
}