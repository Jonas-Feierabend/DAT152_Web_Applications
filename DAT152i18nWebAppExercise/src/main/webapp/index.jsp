<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Exercise F25 - Globalization and Custom Tags</title>
</head>
<body>
    <fmt:setBundle basename="no.hvl.dat152.i18n.Config" var="config" />
	
	<h1 style="text-align: center;margin-top:10px">
		<fmt:message key="title" bundle="${config}"/>
	</h1>
	
	
	
	<div>
		<p>
			<fmt:message key="today_date" bundle="${config}"/>
		</p>
		
		<p>
			<fmt:message key="course_info" bundle="${config}"/>
		</p>
		
		<table border="1">
        <tr>
            <th><fmt:message key="table_h1" bundle="${config}"/></th>
       
        </tr>
        <tr>
            <td><fmt:message key="table_c11" bundle="${config}"/></td>
            <td><fmt:message key="table_c12" bundle="${config}"/></td>
        </tr>
        <tr>
             <td><fmt:message key="table_c21" bundle="${config}"/></td>
            <td><fmt:message key="table_c22" bundle="${config}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="table_c31" bundle="${config}"/></td>
            <td><fmt:message key="table_c32" bundle="${config}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="table_c41" bundle="${config}"/></td>
            <td><fmt:message key="table_c42" bundle="${config}"/></td>
        </tr>
      
    </table>
    
    
    
	</div>
	<a href="./description.jsp"> <td><fmt:message key="description_link" bundle="${config}"/></a>
	
	
		<a href="configlang?locale=en_US">EN</a>  
	<a href="configlang?locale=de_DE">DE</a> 
</body>
</html>