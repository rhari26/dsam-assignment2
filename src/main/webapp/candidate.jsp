<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Candidates</title>
</head>
<body>
<form action="/candidate" method="post" id="voter-form">
	<div id="email-text">
		<input type="text" name="name" id="name" placeholder="Candidate First Name"><br>
	</div>
	<div id="email-text">
		<input type="text" name="surname" id="surname" placeholder="Candidate Last Name"><br>
	</div>
	<input type="submit" value="Submit" name="submit">
</form>
</body>
</html>