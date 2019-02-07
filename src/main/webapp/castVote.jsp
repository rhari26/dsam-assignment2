<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="com.example.appengine.java8.ElectionSummary" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="Style.css"/>
<head>
<meta charset="ISO-8859-1">
<title>Cast Vote</title>
</head>
<body>
<h2>Cast Vote</h2>

<form action="/cast-vote?id=${voterId}" method="post" id="voter-form">
	<% 
  ElectionSummary es = new ElectionSummary();
  List<Entity> candidateList = es.candidateList(); %>
  <% if(candidateList.isEmpty()){ %>
  <% } else { %>
  	<% for(Entity candidate : candidateList){ %>
  	<input type="radio" name="candidate" value="<%= candidate.getKey().getId() %>"><%= candidate.getProperty("name").toString() %><br>
  <% } } %>
  <input type="submit" value="Submit" name="submit">
</form>
</body>
</html>