import java.util.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class Redirect extends HttpServlet { 

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        Enumeration e = req.getHeaderNames();
        while (e.hasMoreElements()) {
            String hname = (String) e.nextElement();
            String hvalues = req.getHeader(hname);

            if(hvalues.contains("Chrome"))  {
                resp.sendRedirect("https://www.google.com");
                break;
            }
            else if(hvalues.contains("Firefox")) {
                resp.sendRedirect("https://www.mozilla.org");
                break;
            }
        }
    }
}