<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
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
<%
	request.setCharacterEncoding("EUC-KR");
	String now = new SimpleDateFormat("yyyy").format(new Date());
	pageContext.setAttribute("now", now);
%>
<c:set var="name" value="ȫ�浿" />
�̸�:${param.name}<br>
����:${param.age}<br>
����:${param.gender==1?"��":"��"}<br>
����⵵:${param.year}<br>
����:��${now - param.year}<br>
</body>
</html>