<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<%@ taglib prefix="DAT158" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    <fmt:setBundle basename="no.hvl.dat152.i18n.Config" var="config" />
<a href="./index.jsp">Home</a>


<p><fmt:message key="long_description" bundle="${config}"/></p>
<DAT158:styleText borderSize="2" width="600" borderColor="green"
background="orange" >

<fmt:message key="long_description" bundle="${config}"/>
</DAT158:styleText>
</body>
</html>