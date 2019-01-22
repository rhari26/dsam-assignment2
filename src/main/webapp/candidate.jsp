<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="com.example.appengine.java8.ElectionSummary" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Candidates</title>
</head>
<body>
<form action="/candidate" method="post" id="voter-form">
	<div id="name-text">
		<input type="text" name="name" id="name" placeholder="Candidate First Name"><br>
	</div>
	<div id="surname-text">
		<input type="text" name="surname" id="surname" placeholder="Candidate Last Name"><br>
	</div>
	<div id="email-text">
		<input type="text" name="email" id="email" placeholder="Candidate E-mail"><br>
	</div>
	<div id="faculty-text">
		<input type="text" name="faculty" id="faculty" placeholder="Faculty Name"><br>
	</div>
	<input type="submit" value="Submit" name="submit">
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