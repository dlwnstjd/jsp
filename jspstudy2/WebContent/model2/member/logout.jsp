<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%--
	1. sesssion �α��� ���� ����.
	2. loginForm.jsp ������ �̵�
 --%>
<%
	session.invalidate();
	response.sendRedirect("loginForm.jsp");
%>