<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>���� ���� �±� ����2</title>
</head>
<body>
<fmt:requestEncoding value="euc-kr"/>
<h3>Format �� ���ڸ� �Ϲ� ���ڷ� ����</h3>
<fmt:parseNumber value="20,000" var="num1" pattern="##,###"/>
<fmt:parseNumber value="10,000" var="num2" pattern="##,###"/>
��:${num1 + num2}<br>
��:<fmt:formatNumber value="${num1 + num2}" pattern="##,###"/><br>
��:<fmt:formatNumber value="${num1 + num2}"
		pattern="##,###" var="num3"/>${num3}<br>
��:${num3}
<h3>Format �� ��¥�� �Ϲ� ��¥�� ����</h3>
<fmt:parseDate value="2020-05-08 12:00:00"
		pattern="yyyy-MM-dd HH:mm:ss" var="day"/>
��¥: ${day}<br>
<h3>����: 2020-05-09�� ���� ����ϱ�</h3>
<fmt:parseDate value="2020-05-09"
		pattern="yyyy-MM-dd" var="test"/>
<fmt:formatDate value="${test}" pattern="yyyy-MM-dd E����"/>

</body>
</html>