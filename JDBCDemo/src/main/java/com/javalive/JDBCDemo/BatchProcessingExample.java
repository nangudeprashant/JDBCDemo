package com.javalive.JDBCDemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.javalive.dbconnectionutility.MySQLDbUtil;

/*
Batch Processing allows you to group related SQL statements into a batch and submit them with one call to 
the database.When you send several SQL statements to the database at once, you reduce the amount of 
communication overhead, thereby improving performance.JDBC drivers are not required to support this feature. 
You should use the DatabaseMetaData.supportsBatchUpdates() method to determine if the target database 
supports batch update processing. The method returns true if your JDBC driver supports this feature.
The addBatch() method of Statement, PreparedStatement, and CallableStatement is used to add individual 
statements to the batch. The executeBatch() is used to start the execution of all the statements grouped 
together.The executeBatch() returns an array of integers, and each element of the array represents the update count for the respective update statement.
Just as you can add statements to a batch for processing, you can remove them with the clearBatch() method.
This method removes all the statements you added with the addBatch() method. However, you cannot selectively
choose which statement to remove.
Batching with Statement Object
Here is a typical sequence of steps to use Batch Processing with Statement Object −
Create a Statement object using either createStatement() methods.
Set auto-commit to false using setAutoCommit().
Add as many as SQL statements you like into batch using addBatch() method on created statement object.
Execute all the SQL statements using executeBatch() method on created statement object.
Finally, commit all the changes using commit() method.
*/
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

	public static void printRows(Statement stmt) throws SQLException {
		// System.out.println("Displaying available rows...");
		// Let us select all the records and display them.
		String sql = "SELECT id, first, last, age FROM Employees";
		ResultSet rs = stmt.executeQuery(sql);

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
		System.out.println();
		rs.close();
	}// end printRows()

}
