<%@page import="model.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>��й�ȣã��</title>
</head>
<body>
<table>
<tr><th>��й�ȣ</th><td>${pw }</td></tr>
<tr><td colspan="2"><input type="button" 
		value="�ݱ�" onclick="location.href='loginForm.me'"></td></tr>
</table>
</body>
</html>