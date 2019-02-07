<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="com.example.appengine.java8.ElectionSummary" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
    
<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="Style.css"/>
<head>
<meta charset="ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Voters</title>
</head>
<body align="center">
<form action="/voters" method="post" id="voter-form">
	<div id="email-text">
		<input type="text" name="email" id="email" placeholder="Voter's Email"><br>
	</div><br>
	<a href="javascript:void(0)" id="add-email">Add</a>
	<input type="submit" value="Submit" name="submit">
</form>
<h3>Voter's List</h3>
<table style="width:700px" align="center">
  <tr>
    <th>Voter's Email</th>
    <th>Email Sent</th> 
    <th>Voted</th>
  </tr>
  <% 
  ElectionSummary es = new ElectionSummary();
  List<Entity> votersList = es.votersList(); %>
  <% if(votersList.isEmpty()){ %>
  <% } else { %>
  	<% for(Entity voter : votersList){ %>
  	<tr>
    <td><%= voter.getProperty("email").toString().trim() %></td>
    <td>Not sent <a href="/cast-vote?token=<%= voter.getProperty("token").toString() %>">vote</a></td> 
    <% if(voter.getProperty("voted").toString() == "false"){ %>
    	<td>Not Voted</td>
    <%} else { %>
    	<td>Voted</td>
    <% } %>
  </tr>
  <% } } %>
</table>
</body>
<script type="text/javascript">
	$(document).on("click", "#add-email", function(){
		var element = '<div><input type="text" name="email" id="email" placeholder="Voters Email"><span id="remove-email" style="cursor: pointer">remove</span><br></div>';
		$('#email-text').append(element);
	});
	
	$(document).on("click", "#remove-email", function(){
		$(this).parent().remove();
	});
</script>
</html>