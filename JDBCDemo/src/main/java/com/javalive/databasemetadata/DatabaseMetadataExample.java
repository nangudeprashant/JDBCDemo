package com.javalive.databasemetadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

/*
 * Generally, Data about data is known as metadata. The DatabaseMetaData interface provides methods to 
 * get information about the database you have connected with like, database name, database driver version, 
 * maximum column length etc...
 * Following are some methods of DatabaseMetaData class.
1.  getDriverName()	Retrieves the name of the current JDBC driver
2.  getDriverVersion()	Retrieves the version of the current JDBC driver
3.  getUserName()	Retrieves the user name.
4.  getDatabaseProductName()	Retrieves the name of the current database.
5.  getDatabaseProductVersion()	Retrieves the version of the current database.
6.  getNumericFunctions()	Retrieves the list of the numeric functions available with this database.
7.  getStringFunctions()	Retrieves the list of the numeric functions available with this database.
8.  getSystemFunctions()	Retrieves the list of the system functions available with this database.
9.  getTimeDateFunctions()	Retrieves the list of the time and date functions available with this database.
10. getURL()	Retrieves the URL for the current database.
11. supportsSavepoints()	Verifies weather the current database supports save points
12. supportsStoredProcedures()	Verifies weather the current database supports stored procedures.
13. supportsTransactions()	Verifies weather the current database supports transactions.
*/
public class DatabaseMetadataExample {
	public static void main(String args[]) throws Exception {
		// Getting the connection
		String mysqlUrl = "jdbc:mysql://localhost/test";
		Connection con = DriverManager.getConnection(mysqlUrl, "root", "root");
		System.out.println("Connection established......");

		// Creating the DatabaseMetaData object
		DatabaseMetaData dbMetadata = con.getMetaData();
		// invoke the supportsBatchUpdates() method.
		boolean bool = dbMetadata.supportsBatchUpdates();

		if (bool) {
			System.out.println("Underlying database supports batch updates");
		} else {
			System.out.println("Underlying database doesnt supports batch updates");
		}

		// Retrieving the driver name
		System.out.println("Driver name for the underlying database is " + dbMetadata.getDriverName());
		// Retrieving the driver version
		System.out.println("Driver version for the underlying database is " + dbMetadata.getDriverVersion());
		// Retrieving the user name
		System.out.println("User name for the underlying database is " + dbMetadata.getUserName());
		// Retrieving the URL
		System.out.println("URL for the underlying database is " + dbMetadata.getURL());
		// Retrieving the list of numeric functions
		System.out.println("Numeric functions: " + dbMetadata.getNumericFunctions());
		System.out.println("========================================================");
		// Retrieving the list of String functions
		System.out.println("String functions: " + dbMetadata.getStringFunctions());
		System.out.println("========================================================");
		// Retrieving the list of system functions
		System.out.println("System functions: " + dbMetadata.getSystemFunctions());
		System.out.println("========================================================");
		// Retrieving the list of time and date functions
		System.out.println("Time and Date funtions: " + dbMetadata.getTimeDateFunctions());
	}
}
