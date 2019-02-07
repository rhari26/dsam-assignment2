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
<title>Election Time</title>
</head>
<body align="center">
<h2>Manage Election Time/Date</h2>
<bor>
<% ElectionSummary es = new ElectionSummary();
  List<Entity> electionTime = es.candidateList();
  if(electionTime.isEmpty()){
   %>
   <form action="/election-time" method="post" id="voter-form">
	<div id="date-text">
		<input type="date" name="date" id="date" placeholder="Election Date" required="required"><br>
	</div>
	<div id="startTime-text">
		<input type="time" name="startTime" id="startTime" placeholder="Election Start Time" required="required"><br>
	</div>
	<div id="endTime-text">
		<input type="time" name="endTime" id="endTime" placeholder="Election End Time" required="required"><br>
	</div>
	<input type="submit" value="Submit" name="submit">
</form>
 <% } else { %>
 	<% for(Entity election : electionTime){  %>
 	<p>Election Date: <%= election.getProperty("electionDate") %></p>
 	<p>Starting at: <%= election.getProperty("electionStartTime") %> till <%= election.getProperty("electionEndTime") %></p>
 <% } } %>
 </bor>
</body>
</html>