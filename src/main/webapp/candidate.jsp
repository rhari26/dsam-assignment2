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
<body align="center">
<h1> Add Candidate</h1><br>
<form action="/candidate" method="post" id="voter-form">
	<div id="name-text">
	<bor><label>FirstName</label>
		<input type="text" name="name" id="name" placeholder="Candidate First Name">
	</bor></div><br>
	<div id="surname-text">
	<bor><label>LastName</label>
		<input type="text" name="surname" id="surname" placeholder="Candidate Last Name">
	</bor></div><br>
	<div id="email-text">
	<bor><label>E-Mail</label>
		<input type="text" name="email" id="email" placeholder="Candidate E-mail">
	</bor></div><br>
	<div id="faculty-text">
	<bor><label>Faculty</label>
		<input type="text" name="faculty" id="faculty" placeholder="Faculty Name">
	</bor></div><br>
	<input type="submit" value="Submit" name="submit" align="center" style="color:red;background-color: #FFFFC0;">
</form>

<h2>Candidate's List</h2>
<table style="width:900px" align="center">
  <tr>
    <th>First Name</th>
    <th>Last Name</th> 
    <th>E-mail</th>
    <th>No.of Votes</th>
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
    <td><%= candidate.getProperty("email").toString().trim() %></td>
    <td><%= es.showCandidateVoteCount(candidate.getKey().getId()) %></td>
  </tr>
  <% } } %>
</table>
</body>
</html>