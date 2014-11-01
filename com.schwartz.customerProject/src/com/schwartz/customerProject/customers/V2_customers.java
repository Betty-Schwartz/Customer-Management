package com.schwartz.customerProject.customers;

import java.sql.SQLException;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import com.schwartz.dao.ManageCustomers;  // make this RESTful???


@Path("/v2/customers")
public class V2_customers {
	

 	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnSelectCustomer(
			@QueryParam("name") String name) 
			throws Exception{	
			
		String returnString = null;
	    JSONArray json = new JSONArray();
		
		try {
			
			if (name == null){
				return Response.status(400).entity("Error:  please specify customer name for this search").build();
			}
			
			ManageCustomers dao = new ManageCustomers();
			json = dao.queryReturnCustomer(name);
			
			returnString = json.toString();
		}
		catch (SQLException sqe){
			sqe.printStackTrace();
			return Response.status(500).entity("Server was not able to procss your request").build();
		}
		
		return Response.ok(returnString).build();
		}
/*
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnErrorOnName()  throws Exception{
		return Response.status(400).entity("Error:  please specify customer for this search").build();
		
	}
*/
	
	@Path("/{name}") 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnCustomer(
			@PathParam("name") String name) // using part of url string to bring in a variable
	        throws Exception {
		
		String returnString = null;
		
	    JSONArray json = new JSONArray();
	    
	    try {
	    	ManageCustomers dao = new ManageCustomers();
	    	
	    	json = dao.queryReturnCustomer(name);
	    	returnString = json.toString();
	    	
	    }
	    catch (Exception e ){
	    e.printStackTrace();
	    	return Response.status(500).entity("Server was not able to process your request").build();
	    }
	    return Response.ok(returnString).build();
	  
	}

}
