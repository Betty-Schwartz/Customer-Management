package com.schwartz.customerProject.customers;

import java.sql.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.schwartz.dao.MySql_Utils;
import com.schwartz.util.ToJSON;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

@Path("/")
public class Customers extends MySql_Utils {
	
	/**
	 * Return all customers.
	 */
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllCustomers() throws Exception{		
			
			String returnString = null;
			Connection conn = null;
			PreparedStatement query = null;
			Response rb = null;
			try {
				conn = MySql_Utils.MySqlConn().getConnection();
				query = conn.prepareStatement("select * " +
				                              "from customers");
				
				ToJSON converter = new ToJSON();
				JSONArray json = new JSONArray();
				
				ResultSet rs = query.executeQuery();
				json = converter.toJSONArray(rs);
				
				query.close(); // close connection
				
				returnString = json.toString();
				
			    rb = Response.ok(returnString).build();
			}
			catch (SQLException sqe){
				sqe.printStackTrace();
				Response.status(500);
				rb = Response.serverError().build();
			}catch (Exception e){
				e.printStackTrace();
				Response.status(500);
				rb = Response.serverError().build();
			}
			finally {
				if (query != null) {
	             	query.close();
	             }
				if ( conn != null){
					conn.close();
				}			
			}
			
			return rb;
		}
	
	/**
	 * Return a customer.
	 * @throws JSONException 
	 */
	@Path("/{id}") 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject queryReturnCustomer(@PathParam("id") int id) throws SQLException, JSONException {
	
		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONObject json = new JSONObject();
		
		try {
			conn = MySqlCustomersConnection(); //inherited from MySql_Utils
			query = conn.prepareStatement("SELECT id, name, email, phoneNumber, street, city, state, zip "+
	                                       "FROM customers " + 
	                                       "WHERE id = ? ");
			
	        query.setInt(1, id);
	        ResultSet rs = query.executeQuery();
	       
	        json = converter.toJSONObject(rs);  //JSONObject to return
	        
	        query.close();  // close connection
		}
		catch(SQLException sqe) {
			sqe.printStackTrace();
		    json.append(sqe.getMessage(), sqe.getClass());
		}
		catch (Exception e){
			e.printStackTrace();
			json.append(e.getMessage(), e.getClass());
		}
		finally {
			if (query != null) {
             	query.close();
             }
			if (conn != null){
				conn.close();
			}
		}
	
	    return json;
	}
	
	/**
	 * Insert a row into the customers table.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertIntoCustomers(JSONObject customerData)  throws SQLException{
		ResponseBuilder builder=new ResponseBuilderImpl();
		Connection conn = null;
		PreparedStatement query = null;
		 
		try {
			/*
			 * TODO:  Validate data here before starting to insert into the database.
			 * Call a validate function in remote.js
			 * 
			 */
			
		    conn =  MySqlCustomersConnection(); //inherited from MySql_Utils
			query = conn.prepareStatement( "INSERT INTO customers " + 
					"(name, email, phoneNumber, street, city, state, zip) " + 
					"VALUES (?, ?, ?, ?, ?, ?, ?) ");
		  
			query.setString(1, customerData.optString("name"));
			query.setString(2, customerData.optString("email"));
			query.setString(3, customerData.optString("phoneNumber"));
			query.setString(4, customerData.optString("street"));
			query.setString(5, customerData.optString("city"));
			query.setString(6, customerData.optString("state"));
			query.setString(7, customerData.optString("zip"));

			 
			int rowsInserted = query.executeUpdate();
			if (rowsInserted > 0) {
				builder.status(200);
			    System.out.println("A new user was inserted successfully!");
			}

			
		} catch(SQLException sqe) {
			sqe.printStackTrace();
			builder.status(500);
		}
		catch(Exception e) {
			e.printStackTrace();
			builder.status(500);
		}
		finally {
			//TODO move cleanup code to it's own method
			if (query != null) {
             	query.close();
             }
			if (conn != null) {
				conn.close();
			}
		}
	    return builder.build();
	}
	

	

	/**
	 * Update an existing customer.
	 * @return 
	 */
	@Path("/{id}") 
	@PUT
	@Consumes(MediaType.APPLICATION_JSON) 
	public Response updateCustomer(@PathParam("id") int id,JSONObject customerData) throws SQLException {
		ResponseBuilder builder = new ResponseBuilderImpl();
		 PreparedStatement query = null;
		Connection conn = null;
		
		try {
		    conn = MySqlCustomersConnection(); //inherited from MySql_Utils
		    query = conn.prepareStatement("UPDATE customers " +
										  "SET name = ? " +
										  ", email = ? " +
										  ", phoneNumber = ? " +
										  ", street = ? " +
										  ", city = ? " +
										  ", state = ?" +
										  ", zip  = ? " +
	                                      "WHERE id = ? ");
			query.setString(1, customerData.optString("name"));
			query.setString(2, customerData.optString("email"));
			query.setString(3, customerData.optString("phoneNumber"));
			
			query.setString(4, customerData.optString("street"));
			query.setString(5, customerData.optString("city"));
			query.setString(6, customerData.optString("state"));
			query.setString(7, customerData.optString("zip"));
			query.setInt(8, id);
				
			
			int rowsUpdated = query.executeUpdate();
			if (rowsUpdated > 0) {
			    System.out.println("An existing user was updated successfully!");
			    builder.status(200);
			}

	        query.close();  // close connection
	        
	        
		}
		catch(SQLException sqe) {
			sqe.printStackTrace();
			builder.status(500);
			return builder.build();		
		} 
		catch (Exception e){
			e.printStackTrace();
			builder.status(500);
			return builder.build();	
		}
		finally {
			 if (query != null) {
             	query.close();
             }
			
			if (conn != null){
				conn.close();
			}
		}
	
	   return builder.build();
	}

	/**
	 * Delete a row from the customers table.
	 */
	
	@Path("{id}") 
	@DELETE
	public Response deleteACustomer( @PathParam("id") int id)  throws SQLException{
		
		ResponseBuilder builder=new ResponseBuilderImpl();
		System.out.println("\nCustomers:deleteACustomer:  " + id );
	                                
	    PreparedStatement query = null;
		Connection conn = null;
		
		try {
			
			/*
			 * Validate before doing the delete
			 */
	       		conn =  MySqlCustomersConnection(); //inherited from MySql_Utils
				query = conn.prepareStatement( "DELETE FROM customers " +
		       		                            "WHERE id =  ? " );
				
				query.setInt(1, id);
				System.out.println("\n Delete query = " + query.toString());
				
				query.executeUpdate();
				
			} catch(SQLException sqe) {
				System.out.println("\n Customers, caught SQLException");
				sqe.printStackTrace();
				builder.status(500);
				return builder.build();		
			} catch(Exception e) {
				System.out.println("\n Customers, caught Exception");
				e.printStackTrace();
				builder.status(500);
				return builder.build();
			}
		
			finally {
                if (query != null) {
                	query.close();
                }
				if (conn != null) {
					conn.close();
				}
		}
		
		
		builder.status(200);
		return builder.build();
	}
	
}
