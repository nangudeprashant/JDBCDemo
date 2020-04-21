package com.javalive.JDBCDemo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.javalive.dbconnectionutility.MySQLDbUtil;

public class CallableStatementExample {
	public static void main(String[] args) {
		// STEP 2: Declaring the required object references.
		Connection conn = null;
		CallableStatement stmt = null;
		try {
			System.out.println("Connecting to database...");
			// STEP 3: Instead of registering the driver as in OldWay example here we are
			// using Factory pattern
			// to get connection direct from com.javalive.dbconnectionutility.MySQLDbUtil
			// class's method.
			conn = MySQLDbUtil.getConnetion();
			// STEP 4: Creating a statement
			System.out.println("Creating callablestatement...");
			String sql = "{call getEmpName (?, ?)}";
			stmt = conn.prepareCall(sql);

			// Bind IN parameter first, then bind OUT parameter
			int emp_ID = 2;
			stmt.setInt(1, emp_ID);
			// This would set ID as 102
			// Because second parameter is OUT so register it
			stmt.registerOutParameter(2, java.sql.Types.VARCHAR);

			// Use execute method to run stored procedure.
			System.out.println("Executing stored procedure...");
			stmt.execute();
			// Retrieve employee name with getXXX method
			String empName = stmt.getString(2);
			System.out.println("Emp Name with ID:" + emp_ID + " is " + empName);
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
		}
	}
}
