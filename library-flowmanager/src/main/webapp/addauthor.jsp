<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Author</title>
</head>
<body>
	<a href="/library">Home</a>
		<p>
		<form action="addauthor" method="post">
			<table>
				<tr>
					<td>Forename:</td>
					<td>${author.forename} <input name="forename" value="${author.forename}" /></td>
					
				</tr>
				<tr>
					<td>Lastname:</td>
					<td><input name="lastname" value="${author.lastname}"/></td>
				</tr>
		
			</table>
			<br>
			<div>
				<input type="submit" value="Create" name="submit">
			</div>
		</form>		
</body>
</html>