package com.schwartz.customerProject.status;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;

import com.schwartz.dao.*;

@Path("/v1/status")
public class V1_status {
	
	private static final String customers_version = "00.01.00";
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle() {
		return "<p>Customer Project Web Service</p> ";
	}
	
	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion() {
		return "<p>Version: " + customers_version + "</p>";
	}
	
	@Path("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnDatabaseStatus()  throws Exception {
		PreparedStatement query = null;
		String myString = null;
		String returnString = null;
		Connection conn = null;
		
		try {
			conn = MySql_Utils.MySqlConn().getConnection();
			query = conn.prepareStatement("select now()  DATETIME");
			ResultSet rs = query.executeQuery();
			
			while (rs.next()) {
				// /*Debug*/ System.out.println(rs.getString("DATETIME"));
				myString = rs.getString("DATETIME");
			}
			query.close();  // close connection
			
			returnString  = "<p>Database Status</p> " +
			   "<p>Database Date/Time return:  " + myString + "</p>";
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
		finally {
			if (conn != null){
				conn.close();
			}
		}
		return returnString;
	}
	
	
	@Path("/printSystemProperties")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public void printSystemProperties() {
		Properties p = System.getProperties();
		Enumeration keys = p.keys();
		while (keys.hasMoreElements()) {
		  String key = (String)keys.nextElement();
		  String value = (String)p.get(key);
		  System.out.println(key + ": " + value);
		}
	}	

}
