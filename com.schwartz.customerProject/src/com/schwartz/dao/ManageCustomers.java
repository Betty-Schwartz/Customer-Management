package com.schwartz.dao;

import java.sql.*;

import org.codehaus.jettison.json.JSONArray;

import com.schwartz.util.ToJSON;

public class ManageCustomers extends MySql_Utils {
	
	public JSONArray queryReturnCustomer(String name) throws SQLException {
	
	PreparedStatement query = null;
	Connection conn = null;
	ToJSON converter = new ToJSON();
	JSONArray json = new JSONArray();
	
	try {
		conn = MySqlCustomersConnection();
		query = conn.prepareStatement("SELECT id, name, email, phoneNumber, address, street, city, state, zip "+
                                       "FROM customers " + 
                                       "WHERE LOWER(name) = ? ");
		
        query.setString(1, name.toLowerCase());
        ResultSet rs = query.executeQuery();
       
        json = converter.toJSONArray(rs);
        query.close();  // close connection
	}
	catch(SQLException sqe) {
		sqe.printStackTrace();
		return json;
	}
	catch (Exception e){
		e.printStackTrace();
		return json;
	}
	finally {
		if (conn != null){
			conn.close();
		}
	}
	
	return json;
	}

}
