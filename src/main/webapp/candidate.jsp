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
<h1 align="center"> Add Candidate</h1>
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
		<input type="text" value="E-Mail" name="email" id="email" placeholder="Candidate E-mail">
	</bor></div>
	<div id="faculty-text">
	<bor><label>Faculty</label>
		<input type="text" name="faculty" id="faculty" placeholder="Faculty Name">
	</bor></div><br>
	<input type="submit" value="Submit" name="submit" style="color:red;background-color: #FFFFC0;margin-left: 150px;">
</form>

<h2 align="center">Candidate's List</h2>
<table style="width:100%" align="center">
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
  	<% for(Entity candidate : candidateList){ %>
  	<tr>
    <td><%= candidate.getProperty("name").toString().trim() %></td>
    <td><%= candidate.getProperty("surname").toString().trim() %></td> 
    <td><%= candidate.getKey().toString().trim() %></td>
  </tr>
  <% } } %>
</table>
</body>
</html>