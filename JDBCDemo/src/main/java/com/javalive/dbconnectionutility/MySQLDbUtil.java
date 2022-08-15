package com.javalive.dbconnectionutility;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLDbUtil {
	private  static String databaseURL = "jdbc:mysql://localhost:3306/test";
    private static Connection conn = null;
	public static Connection getConnetion() throws SQLException{
	        Properties props = new Properties();
	        props.put("user", "root");
	        props.put("password", "root");
	        conn = DriverManager.getConnection(databaseURL, props);
	        if (conn != null) {
	            System.out.println("Connected to the database");
	        }
	        else {
	        	throw new SQLException();
	        }
	        return conn;
	}
		
}

