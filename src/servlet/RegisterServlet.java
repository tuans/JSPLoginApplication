package servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
 
@WebServlet(name = "Register", urlPatterns = { "/Register" })
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    static Logger logger = Logger.getLogger(RegisterServlet.class);
     
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        
        String errorMsg = null;
        if(username == null || username.equals("")){
            errorMsg = "username ID can't be null or empty.";
        }
        if(password == null || password.equals("")){
            errorMsg = "Password can't be null or empty.";
        }
        if(name == null || name.equals("")){
            errorMsg = "Name can't be null or empty.";
        }
        
         
        if(errorMsg != null){
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.html");
            PrintWriter out= response.getWriter();
            out.println("<font color=red>"+errorMsg+"</font>");
            rd.include(request, response);
        }else{
         
        Connection con = (Connection) getServletContext().getAttribute("DBConnection");
        
        PreparedStatement ps = null;
        try {
        	ps = con.prepareStatement("insert into UserDB.users(name,username,password) values (?,?,?)");
            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, password);
            
            ps.executeUpdate();
             
            logger.info("User registered with username="+username);
             
            //forward to login page to login
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out= response.getWriter();
            out.println("<font color=green>Registration successful, please login below.</font>");
            rd.include(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Database connection problem");
            throw new ServletException("DB Connection problem.");
        }finally{
            try {
                ps.close();
            } catch (SQLException e) {
                logger.error("SQLException in closing PreparedStatement");
            }
        }
        }
         
    }
 
}
