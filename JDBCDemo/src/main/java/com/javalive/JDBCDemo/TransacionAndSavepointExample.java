package com.javalive.JDBCDemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import com.javalive.dbconnectionutility.MySQLDbUtil;

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

			// STEP 7: delete rows having ID grater than 104
			// But save point before doing so.
			Savepoint savepoint1 = conn.setSavepoint("ROWS_DELETED_1");
			System.out.println("Deleting row....");
			String SQL = "DELETE FROM Employees " + "WHERE ID = 110";
			stmt.executeUpdate(SQL);
			// oops... we deleted too wrong employees!
			// STEP 8: Rollback the changes afetr save point 2.
			conn.rollback(savepoint1);

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
