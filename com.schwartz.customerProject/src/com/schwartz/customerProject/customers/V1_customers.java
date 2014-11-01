package com.schwartz.customerProject.customers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONArray;

import com.schwartz.dao.MySql_Utils;
import com.schwartz.util.ToJSON;

@Path("/v1/customers")
public class V1_customers {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllCustomers() throws Exception{
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try {
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
		    
			json = converter.toJSONArray(rs);
			query.close(); //close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
		}
		catch (SQLException sqe){
			sqe.printStackTrace();
		}
		finally {
			if(conn != null){
				conn.close();
		    }
		}
		return rb;
		}
}
	
	
