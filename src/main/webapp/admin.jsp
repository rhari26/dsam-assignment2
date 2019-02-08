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
<title>Admin</title>
</head>
<body align="center">
<% ElectionSummary es = new ElectionSummary(); %>
<h1 align="center">Admin Management</h1><br>
<bor style="width: 600px; height: 90px; "><a href="/candidate" id="candidates">Manage Candidates</a></bor>
<bor style="width: 600px; height: 90px; "><a href="/voters" id="voters">Manage Voters</a></bor>
<bor style="width: 600px; height: 90px; "><a href="/election-time" id="voters">Manage Election DateTime</a></bor><br>
<h2>Election Summary - <%= es.votingPercentage() %>% active voters</h2>
<table style="width:900px" align="center">
  <tr>
    <th>First Name</th>
    <th>Last Name</th> 
    <th>E-mail</th>
    <th>No.of Votes</th>
  </tr>
  <% 
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