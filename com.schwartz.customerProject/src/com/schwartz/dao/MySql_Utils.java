package com.schwartz.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.*;
import javax.sql.*;

public class MySql_Utils {
	private static DataSource MySql_DataSource = null; //holds the database object
	private static Context context = null;  // used to lookup the database connection in MySql
	
	
	/**
	 * This is a public method does the lookup
	 * @return Database object
	 * @throws SQLException
	 */
	
	public static DataSource MySqlConn() throws SQLException {
		
		/**
		 * check if the database object is already defined
		 * if it is, return the connection, 
		 * no need to look it up again
		 */
		if (MySql_DataSource != null){
			return MySql_DataSource;
		}
		
		try {
			/**
			 * This only needs to run one time to get the database object
			 * context is used to lookup the database object in MySql
			 * MySql_Utils will hold the database object
			 */
			if (context == null)  {
				context = new InitialContext();
			}
			Context envContext  = (Context)context.lookup("java:/comp/env");
			MySql_DataSource = (DataSource)envContext.lookup("jdbc/customers");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	    return MySql_DataSource;
	}
	
	
	//This method creates the connection
	protected static Connection MySqlCustomersConnection() {
		Connection conn = null;
		try {
			conn = MySqlConn().getConnection();
			return conn;
		}
		catch (SQLException sqe){
			sqe.printStackTrace();
		}
		return conn;
	}
}