<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%--
	1. sesssion 로그인 정보 제거.
	2. loginForm.jsp 페이지 이동
 --%>
<%
	session.invalidate();
	response.sendRedirect("loginForm.jsp");
%>