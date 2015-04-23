package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.User;

import org.apache.log4j.Logger;
 
@WebServlet(name = "Login", urlPatterns = { "/Login" })
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    static Logger logger = Logger.getLogger(LoginServlet.class);
     
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String errorMsg = null;
        if(username == null || username.equals("")){
            errorMsg ="User Email can't be null or empty";
        }
        if(password == null || password.equals("")){
            errorMsg = "Password can't be null or empty";
        }
         
        if(errorMsg != null){
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out= response.getWriter();
            out.println("<font color=red>"+errorMsg+"</font>");
            rd.include(request, response);
        }else{
         
        Connection con = (Connection) getServletContext().getAttribute("DBConnection");
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        	String sqlQuery = "select * from UserDB.users where username=? and password=? limit 1";
            ps = con.prepareStatement(sqlQuery);
            
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
             
            if(rs != null && rs.next()){
                 
                User user = new User(rs.getString("name"), rs.getInt("userId"));
                logger.info("User found with details="+user);
                HttpSession session = request.getSession();
                session.setAttribute("User", user);
                response.sendRedirect("home.jsp");
            }else{
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                PrintWriter out= response.getWriter();
                logger.error("User not found with username="+username);
                out.println("<font color=red>No user found with given username, please register first.</font>");
                rd.include(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Database connection problem");
            throw new ServletException("DB Connection problem.");
        }finally{
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                logger.error("SQLException in closing PreparedStatement or ResultSet");;
            }
             
        }
        }
    }
 
}