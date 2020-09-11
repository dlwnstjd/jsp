<%@page import="javax.swing.text.Document"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<form name="f" method="post">
x:<input type="text" name="x" value="${param.x}">
<select name="op">
	<option>+</option><option>-</option>
	<option>*</option><option>/</option>
</select>
y:<input type="text" name="y" value="${param.y}">
<input type="submit" value="=">
</form>
<c:choose>
	<c:when test="${param.op=='+'}">
		<h1>${param.x} ${param.op} ${param.y} = ${param.x + param.y}</h1>
	</c:when>
	<c:when test="${param.op=='-'}">
		<h1>${param.x} ${param.op} ${param.y} = ${param.x - param.y}</h1>
	</c:when>
	<c:when test="${param.op=='*'}">
		<h1>${param.x} ${param.op} ${param.y} = ${param.x * param.y}</h1>
	</c:when>
	<c:when test="${param.op=='/'}">
		<h1>${param.x} ${param.op} ${param.y} = ${param.x / param.y}</h1>
	</c:when>
</c:choose>
</body>
</html>