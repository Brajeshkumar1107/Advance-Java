import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Enumeration;

public class RegisterServlet extends HttpServlet {

  private PreparedStatement ps;

  public void init() throws ServletException {
    ServletContext ctxt = super.getServletContext();
    ServletConfig cfg = super.getServletConfig();

    Connection conn = (Connection) ctxt.getAttribute("dbconn");
    String qry = cfg.getInitParameter("insertquery");

    try {
      ps = conn.prepareStatement(qry);
    } catch (Exception sq) {
      System.out.println("Some problem in init:" + sq);

      ServletException obj = new ServletException(sq.getMessage());
      throw obj;
    }
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/html");
    PrintWriter pw = resp.getWriter();
    pw.println("<html>");
    pw.println("<head><title>Registration Response</title></head>");
    pw.println("<body>");
    String userid = req.getParameter("userid");
    String pwd = req.getParameter("password");
    String username = req.getParameter("username");

    try {
      ps.setString(1, userid);
      ps.setString(2, pwd);
      ps.setString(3, username);
      int ans = ps.executeUpdate();
      if (ans == 1) {
        pw.println("<h2>Thank you for regestring with us</h2>");
        pw.println("<p>Now you can <a href='signin.html'>Login</a></p>");
      } else {
        pw.println("<h2>Sorry!</h2>");
        pw.println("<p>Registration rejected.</p>");
        pw.println("<p>Try again <a href='signup.html'>Register again</a></p>");

      }
    } catch (SQLException sq) {
      System.out.println("Some problem in dopost" + sq);
      pw.println("<h2>Sorry!</h2>");
      pw.println("<p>Server is experiencing some issues. Try later</p>");

    }
    pw.println("</body>");
    pw.println("</html>");
    pw.close();

  }

}