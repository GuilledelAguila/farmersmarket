/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

db.mysql.url="jdbc:mysql://localhost:3306/db?characterEncoding=UTF-8&useSSL=false"
 */
package farmersmarket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class JavaMySql {

	/** The name of the MySQL account to use (or empty for anonymous) */
	//private final String userName = "root";

	/** The password for the MySQL account (or empty for anonymous) */
	//private final String password = "root";

	/** The name of the computer running MySQL */
	private final String serverName = "localhost";

	/** The port of the MySQL server (default is 3306) */
	private final int portNumber = 3306;

	/** The name of the database we are testing with (this default is installed with MySQL) */
	private final String dbName = "farmersmarket";

	/** The name of the table we are testing with */
	private final String tableName = "JDBC_TEST";
	private final boolean useSSL = false;

	/**
	 * Get a new database connection
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection(String user, String pass) throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", user);
		connectionProps.put("password", pass);

		conn = DriverManager.getConnection("jdbc:mysql://"
				+ this.serverName + ":" + this.portNumber + "/" + this.dbName + "?characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC",
				connectionProps);

		return conn;
	}
	/**
	 * Run a SQL command which does not return a recordset:
	 * CREATE/INSERT/UPDATE/DELETE/DROP/etc.
	 * 
	 * @throws SQLException If something goes wrong
	 */
	public boolean executeUpdate(Connection conn, String command) throws SQLException {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(command); // This will throw a SQLException if it fails
			return true;
		} finally {

			// This will run whether we throw an exception or not
			if (stmt != null) { stmt.close(); }
		}
	}

	/**
	 * Connect to MySQL and do some stuff.
	 */
	public void run(String user, String pass) {

		// Connect to MySQL
		Connection conn = null;
		try {
			conn = this.getConnection(user, pass);
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			//e.printStackTrace();
			return;
		}

		// drop the database
		try {
			String drop = "DROP DATABASE lotrfinal";
			Statement stmt1 = null;
			stmt1 = conn.createStatement();
			stmt1.executeUpdate(drop);
			System.out.println("Database dropped");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}




		/**
		 * Connect to the DB and do some stuff
		 * @param args
		 */
		public static void main(String[] args) {
			// input variables
			Scanner in = new Scanner(System.in);
			String user;
			String pass;
			System.out.println("Enter username: ");
			user = in.nextLine();
			System.out.println("Enter password: ");
			pass = in.nextLine();


			JavaMySql app = new JavaMySql();
			app.run(user, pass);
		}
	}


