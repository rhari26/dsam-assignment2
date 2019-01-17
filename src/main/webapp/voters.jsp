<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Voters</title>
</head>
<body>
<form action="/voters" method="post" id="voter-form">
	<div id="email-text">
		<input type="text" name="email[]" id="email" placeholder="Voter's Email"><br>
	</div>
	<a href="javascript:void(0)" id="add-email">Add</a>
	<input type="submit" value="Submit" name="submit">
</form>
</body>
<script type="text/javascript">
	$(document).on("click", "#add-email", function(){
		var element = '<div><input type="text" name="email[]" id="email" placeholder="Voters Email"><span id="remove-email" style="cursor: pointer">remove</span><br></div>';
		$('#email-text').append(element);
	});
	
	$(document).on("click", "#remove-email", function(){
		$(this).parent().remove();
	});
</script>
</html>