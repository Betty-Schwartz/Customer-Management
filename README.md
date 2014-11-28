Customer-Management
===================

List, Create, Update, and  Delete customers. 

Readme for Customer Project


git@github.com:Betty-Schwartz/Customer-Management.git

Environment:

jersey 1.18
underscore.js for templating 
jquery.validate.js
additional-methods.js for validation
polyfill.js for serialization
bootstrap.js for css
HTML5
jquery-2.1.1.js
Java6
MySql 5.6.17
Eclipse Indigo Java EE IDE 

URI path	Resource Class	HTTP method
/			Customers	GET
/{id}		Customers	GET
/{id}		Customers	PUT
/{id}		Customers	DELETE
/			Customers	POST


ToDo:

Incorporate:
Spring
Hibernate
restclient-ui
    Added to Eclipse
    Used as standalone

Complete input form validation

Add server side validation
  1.  Don’t allow duplicate email addresses on server side

Error handling:
  Notify client in a graceful way of sql errors
  Need to test more

Add dropdown list of states

Create dialog to confirm delete


Testing with restclient-ui:

 

