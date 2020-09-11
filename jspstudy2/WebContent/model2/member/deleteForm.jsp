<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%--
 1. id 파라미터 저장하기.
 2. login 여부 검증하기
 	로그아웃 상태인 경우 로그인하세요. 메세지 출력 후 loginForm.jsp 페이지이동
 3. 현재 화면 출력하기
 --%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>회원 탈퇴 비밀번호 입력</title>
<link rel="stylesheet" href="../../css/main.css">
</head>
<body>
<form action="delete.me" method="post">
	<input type="hidden" name="id" value="${param.id }" >
<table>
	<caption>회원비밀번호 입력</caption>
	<tr><th>비밀번호</th><td><input type="password" name="pass"/></td></tr>
	<tr><td colspan="2"><input type="submit" value="탈퇴하기"/></td></tr>
</table></form></body></html>