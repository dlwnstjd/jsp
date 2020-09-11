<%@page import="model.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>비밀번호찾기</title>
</head>
<body>
<table>
<tr><th>비밀번호</th><td>${pw }</td></tr>
<tr><td colspan="2"><input type="button" 
		value="닫기" onclick="location.href='loginForm.me'"></td></tr>
</table>
</body>
</html>