package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import servlet.filters.AuthenticationFilter;
 
public class DBConnectionManager {
 
    private Connection connection;
    private Logger logger = Logger.getLogger(DBConnectionManager.class);
     
    public DBConnectionManager() throws ClassNotFoundException, SQLException{
    	String dbURL = "jdbc:mysql://localhost:3306/UserDB";
    	String user = "root";
    	String pwd = "root";
    	Class.forName("com.mysql.jdbc.Driver");
        this.connection = DriverManager.getConnection(dbURL, user, pwd);
        
    }
     
    public Connection getConnection(){
        return this.connection;
    }
}
