<%@page import="java.util.List"%>
<%@page import="model.MemberDao"%>
<%@page import="model.Member"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>id찾기</title>
</head>
<body>
<table>
<tr><th>아이디</th><td>${id }</td></tr>
<tr><td colspan="2"><input type="button" 
		value="아이디찾기" onclick="location.href='loginForm.me'"></td></tr>
</table>
</body>
</html>