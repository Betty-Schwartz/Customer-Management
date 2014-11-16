$(document).ready(function() {
	
	// Use underscore to convert the template to a 'template' object
	// This is for the GET call
	
	var customerTemplate = _.template($('#customerTableTemplate').html());
	// Use the template object to output the html
	
	var displayCustomers = function() {
	  	$.get("customers", function(data) {
		  	var html = customerTemplate({'listOfCustomers': data});
			$("#customerList").html(html);
		});
	};
	
	/**
	 * Displays list of customers in database
	 */
	
	var showCustomerList = function() {
      $("#editCustomer").hide();		  		
      $("#customerList").show();
	};
	
	/**
	 * Displays form to edit or create a customer in database
	 */
	
	var showCustomerForm = function() {
      $("#customerList").hide();
      $("#editCustomer").show();		  
	};
    
	/*
	 * Updates a customer in the database
	 * @param  customerData
	 *
	 */
	
	var updateUser = function(customerData) {
		var id = customerData.id;
		var customerUrl = "customers/" + id;
		
		$.ajax(customerUrl, {
			type: "PUT",
		    contentType: 'application/json; charset=utf-8',
			data: JSON.stringify(customerData),
			success: function() {
				displayCustomers();
				showCustomerList();
			}
		});
	};
	
	/*
	 * Inserts a customer in the database
	 * @param  customerData
	 * 
	 */
	var createUser = function(customerData) {
		var customerUrl = "customers";
		
		$.ajax(customerUrl, {
			type: "POST",
		    contentType: 'application/json; charset=utf-8',
			data: JSON.stringify(customerData),
			success: function() {
				displayCustomers();
				showCustomerList();
			}
		});
	};

	
	/**
	 * This is for the Submit button
	 * It will trigger an Ajax PUT call 
	 * to update an existing customer
	 * or POST to add a new customer
	 * This will submit a customer entry to the customer database
	 */
	$('#customerDetails').on('submit', function(event) {
		var $form = $(event.target);
		var customerId = $form.find("input[name='id']").val();
		var customerData = $form.serializeObject();

		if(customerId) {
			updateUser(customerData);
		} else {
			createUser(customerData);
		}
		
		event.preventDefault();
	});
	
	/**
	 * Constructs Customer form
	 * for editing or creating a customer
	 */
	
	var setupCustomerForm = function(data) {
	  var form = $("#editCustomer");
	  form.find("input").each(function(_, input) {
	    var $input = $(input);
		$input.val("");
				 
		var name = $input.attr("name");
				 
		if (data[name]) {
		  $input.val(data[name]); 				 
	    }
	  });
	  showCustomerForm();
	};
	
	/**
	 * Handler for Edit button
	 * delegate style
	 */
	$('#customerList').on("click", ".edit", function(event) {
      var $target = $(event.target);
	  var $tr = $target.parents("tr");
      var customerId = $tr.attr("id");
	  
	  var customerUrl = "customers/" + customerId;
	  $.get(customerUrl, setupCustomerForm);
	});
	
	
	/**
	 * Handler for Delete Button
	 */
	$('#customerList').on("click",".delete", function(event) {
      var $target = $(event.target);
      var $tr = $target.parents("tr");
      
      var customerId = $tr.attr("id");
      var path = "customers/" + customerId;
      $.ajax(path, {
    	  type: "DELETE",
    	  success: function() {
    		 displayCustomers(); 
    	  },
    	  error: function() {
    		  alert("Something unexpected happened!");
    	  }
    	  
      });
	});
	
	
	/**
	 * Handler for Create Customer Button
	 */	
	
	$('#customerList').on("click", "#newCustomer", function() {
       setupCustomerForm({});		
	});
	 
	displayCustomers();
});