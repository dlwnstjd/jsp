<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<fmt:requestEncoding value="euc-kr"/>
�̸� : ${param.name }<br>
���� : ${param.age }<br>
���� : ${param.gender.equals("1")?"��":"��" }<br>
��� : ${param.hobby }<br>
����⵵ : ${param.year }<br>
<h3>��� ���� ��� ��ȸ�ϱ�</h3>
��� :<c:forEach var="j" items="${paramValues.hobby }">
	${j }&nbsp;&nbsp;&nbsp;
</c:forEach> <br>
<h3>��� �Ķ���� ���� ��ȸ�ϱ�</h3>
<table><tr><th>�Ķ�����̸�</th><th>�Ķ���Ͱ�</th></tr>
<c:forEach var="t" items="${paramValues}">
  <tr><td>${t.key}</td>
  <td>
    <c:forEach var="v" items="${t.value}">
        ${v}&nbsp;&nbsp;
    </c:forEach>
  </td></tr>
</c:forEach>
</table>
</body>
</html>