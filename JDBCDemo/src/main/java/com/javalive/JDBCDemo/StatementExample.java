package com.javalive.JDBCDemo;

//STEP 1: import required packages.
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.javalive.dbconnectionutility.*;

/*
 *Statement
 *The Statement interface represents the static SQL statement. It helps you to create a general purpose SQL 
 *statements using Java.
 *Creating a statement:
 *You can create an object of this interface using the createStatement() method of the Connection interface.
*/

public class StatementExample {
	public static void main(String[] args) {
		// STEP 2: Declaring the required object references.
		Connection conn = null;
		Statement stmt = null;
		try {
			System.out.println("Connecting to database...");
			// STEP 3: Instead of registering the driver as in OldWay example here we are
			// using Factory pattern to get connection direct from com.javalive.dbconnectionutility.MySQLDbUtil
			// class's method.
			conn = MySQLDbUtil.getConnetion();
			// STEP 4: Creating a statement
			System.out.println("Creating statement...");
			// STEP 5: Creating a query
			stmt = conn.createStatement();
			String sql = "UPDATE Employees set age=31 WHERE id=103";
			// STEP 6: Execute a query & let us check if it returns a true Result Set or not.
			Boolean ret = stmt.execute(sql);
			System.out.println("Return value is : " + ret.toString());
			// Let us update age of the record with ID = 103;
			int rows = stmt.executeUpdate(sql);
			System.out.println("Rows impacted : " + rows);
			// Let us select all the records and display them.
			sql = "SELECT id, first, last, age FROM Employees";
			ResultSet rs = stmt.executeQuery(sql);
			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("id");
				int age = rs.getInt("age");
				String first = rs.getString("first");
				String last = rs.getString("last");

				// Display values
				System.out.print("ID: " + id);
				System.out.print(", Age: " + age);
				System.out.print(", First: " + first);
				System.out.println(", Last: " + last);
			}
			// STEP 6: Clean-up environment
			rs.close();
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
}
