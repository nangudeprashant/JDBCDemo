package com.javalive.JDBCDemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import com.javalive.dbconnectionutility.MySQLDbUtil;
/*
If the JDBC Connection is in auto-commit mode, which it is by default, then every SQL statement is 
committed to the database upon its completion.
That may be fine for simple applications, but there are three reasons why you may want to turn off the 
auto-commit and manage your own transactions −
To increase performance.
To maintain the integrity of business processes.
To use distributed transactions.
Transactions enable you to control if, and when, changes are applied to the database. 
It treats a single SQL statement or a group of SQL statements as one logical unit, and if any statement 
fails, the whole transaction fails.
To enable manual- transaction support instead of the auto-commit mode that the JDBC driver uses by default, 
use the Connection object's setAutoCommit() method. If you pass a boolean false to setAutoCommit( ), 
you turn off auto-commit. You can pass a boolean true to turn it back on again.
For example, if you have a Connection object named conn, code the following to turn off auto-commit −
conn.setAutoCommit(false);
Commit & Rollback
Once you are done with your changes and you want to commit the changes then call commit() method on 
connection object as follows −
conn.commit( );
Otherwise, to roll back updates to the database made using the Connection named conn, use the following 
code −
conn.rollback( );
*/
public class TransacionAndSavepointExample {
	public static void main(String[] args) {
		// STEP 2: Declaring the required object references.
		Connection conn = null;
		Statement stmt = null;
		try {
			System.out.println("Connecting to database...");
			// STEP 3: Instead of registering the driver as in OldWay example here we are
			// using Factory pattern
			// to get connection direct from com.javalive.dbconnectionutility.MySQLDbUtil
			// class's method.
			conn = MySQLDbUtil.getConnetion();
			// STEP 4: Set auto commit as false.
			conn.setAutoCommit(false);

			// STEP 5: Execute a query to delete statment with
			// required arguments for RS example.
			System.out.println("Creating statement...");
			stmt = conn.createStatement();

			// STEP 6: Now list all the available records.
			String sql = "SELECT id, first, last, age FROM Employees";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("List result set for reference....");
			printRs(rs);

			// STEP 7: delete rows having ID 101
			// But save point before doing so.
			Savepoint savepoint1 = conn.setSavepoint("ROWS_DELETED_1");
			System.out.println("Deleting row WHERE ID = 110....");
			String SQL = "DELETE FROM Employees " + "WHERE ID = 110";
			stmt.executeUpdate(SQL);
			System.out.println("Data after deleting emplyee records with ID 110");
			rs = stmt.executeQuery(sql);
			printRs(rs);
			// oops... we deleted wrong employees!
			// STEP 8: Rollback the changes afetr save point 2.
			System.out.println("Ohh but we have deleted wrong data.Let's rollback it.");
			conn.rollback(savepoint1);
			System.out.println("Data after rollback.....");
			sql = "SELECT id, first, last, age FROM Employees";
			rs = stmt.executeQuery(sql);
			printRs(rs);
			// STEP 9: delete rows having ID grater than 104
			// But save point before doing so.
			Savepoint savepoint2 = conn.setSavepoint("ROWS_DELETED_2");
			System.out.println("Deleting row....");
			SQL = "DELETE FROM Employees " + "WHERE ID = 95";
			stmt.executeUpdate(SQL);

			// STEP 10: Now list all the available records.
			sql = "SELECT id, first, last, age FROM Employees";
			rs = stmt.executeQuery(sql);
			System.out.println("List result set for reference....");
			printRs(rs);

			// STEP 10: Clean-up environment
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

	public static void printRs(ResultSet rs) throws SQLException {
		// Ensure we start with first row
		rs.beforeFirst();
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
	}// end printRs()

}
