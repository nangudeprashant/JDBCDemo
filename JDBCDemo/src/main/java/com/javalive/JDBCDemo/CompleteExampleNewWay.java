package com.javalive.JDBCDemo;
//STEP 1: import required packages.
import com.javalive.dbconnectionutility.*;
import java.sql.*;
public class CompleteExampleNewWay {
	public static void main(String[] args) {
		Connection conn =null;
		Statement stmt = null;
		try {
			System.out.println("Connecting to database...");
			// STEP 2: Instead of registering the driver as in OldWay example here we are using Factory pattern
	        // to get connection direct from com.javalive.dbconnectionutility.MySQLDbUtil class's method.
	        conn = MySQLDbUtil.getConnetion();
	        //STEP 3: Open a connection
			if (conn != null) {
	                System.out.println("Connected to the database");
	            }
			System.out.println("Creating statement...");
		    stmt = conn.createStatement();
		    String sql;
		    sql = "SELECT empid, ename FROM Employee";
		    ResultSet rs = stmt.executeQuery(sql);
		    //STEP 5: Extract data from result set
		    while(rs.next()){
		         //Retrieve by column name
		         int id  = rs.getInt("empid");
		         String ename = rs.getString("ename");
	             //Display values
		         System.out.print("ID: " + id);
		         System.out.println(", Name: " + ename);
		         }
		      rs.close();
		      stmt.close();
		      conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		}
	}	
	}

	


