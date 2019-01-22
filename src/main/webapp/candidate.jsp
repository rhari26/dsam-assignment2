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
<title>Candidates</title>
</head>
<body>
<form action="/candidate" method="post" id="voter-form">
	<div id="name-text">
	<bor><label>FirstName</label>
		<input type="text" value="FirstName" name="name" id="name" placeholder="Candidate First Name">
	</bor></div>
	<div id="surname-text">
	<bor><label>LastName</label>
		<input type="text" value="LastName" name="surname" id="surname" placeholder="Candidate Last Name">
	</bor></div>
	<div id="email-text">
	<bor><label>E-Mail</label>
		<input type="text" value="E=mail" name="email" id="email" placeholder="Candidate E-mail">
	</bor></div>
	<div id="faculty-text">
	<bor><label>Faculty</label>
		<input type="text" name="faculty" id="faculty" placeholder="Faculty Name">
	</bor></div>
	<bor><input type="submit" value="Submit" name="submit"></bor>
</form>

<h3>Candidate's List</h3>
<table style="width:100%">
  <tr>
    <th>First Name</th>
    <th>Last Name</th> 
    <th>E-mail</th>
  </tr>
  <% 
  ElectionSummary es = new ElectionSummary();
  List<Entity> candidateList = es.candidateList(); %>
  <% if(candidateList.isEmpty()){ %>
  <% } else { %>
  	<% for(Entity voter : candidateList){ %>
  	<tr>
    <td><%= voter.getProperty("name").toString().trim() %></td>
    <td><%= voter.getProperty("surname").toString().trim() %></td> 
    <td><%= voter.getProperty("email").toString().trim() %></td>
  </tr>
  <% } } %>
</table>
</body>
</html>