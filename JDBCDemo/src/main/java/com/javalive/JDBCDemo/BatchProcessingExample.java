package com.javalive.JDBCDemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.javalive.dbconnectionutility.MySQLDbUtil;

public class BatchProcessingExample {
	public static void main(String[] args) {
		// STEP 2: Declaring the required object references.
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			System.out.println("Connecting to database...");
			// STEP 3: Instead of registering the driver as in OldWay example here we are
			// using Factory pattern
			// to get connection direct from com.javalive.dbconnectionutility.MySQLDbUtil
			// class's method.
			conn = MySQLDbUtil.getConnetion();
			// STEP 4: Creating a statement
			System.out.println("Creating preparedstatement...");
			String SQL = "INSERT INTO Employees(id,first,last,age) " + "VALUES(?, ?, ?, ?)";

			// Create preparedStatemen
			System.out.println("Creating statement...");
			stmt = conn.prepareStatement(SQL);

			// Set auto-commit to false
			conn.setAutoCommit(false);

			// First, let us select all the records and display them.
			printRows(stmt);

			// Set the variables
			stmt.setInt(1, 400);
			stmt.setString(2, "Pappu");
			stmt.setString(3, "Singh");
			stmt.setInt(4, 33);
			// Add it to the batch
			stmt.addBatch();

			// Set the variables
			stmt.setInt(1, 401);
			stmt.setString(2, "Pawan");
			stmt.setString(3, "Singh");
			stmt.setInt(4, 31);
			// Add it to the batch
			stmt.addBatch();

			// Create an int[] to hold returned values
			int[] count = stmt.executeBatch();

			// Explicitly commit statements to apply changes
			conn.commit();

			// Again, let us select all the records and display them.
			printRows(stmt);

			// Clean-up environment
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}// end main
	
	
	public static void printRows(Statement stmt) throws SQLException{
		   System.out.println("Displaying available rows...");
		   // Let us select all the records and display them.
		   String sql = "SELECT id, first, last, age FROM Employees";
		   ResultSet rs = stmt.executeQuery(sql);

		   while(rs.next()){
		      //Retrieve by column name
		      int id  = rs.getInt("id");
		      int age = rs.getInt("age");
		      String first = rs.getString("first");
		      String last = rs.getString("last");

		      //Display values
		      System.out.print("ID: " + id);
		      System.out.print(", Age: " + age);
		      System.out.print(", First: " + first);
		      System.out.println(", Last: " + last);
		   }
		   System.out.println();
		   rs.close();
		}//end printRows()

}
