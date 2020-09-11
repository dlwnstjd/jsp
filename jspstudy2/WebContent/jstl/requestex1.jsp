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
이름 : ${param.name }<br>
나이 : ${param.age }<br>
성별 : ${param.gender.equals("1")?"남":"여" }<br>
취미 : ${param.hobby }<br>
출생년도 : ${param.year }<br>
<h3>취미 정보 모두 조회하기</h3>
취미 :<c:forEach var="j" items="${paramValues.hobby }">
	${j }&nbsp;&nbsp;&nbsp;
</c:forEach> <br>
<h3>모든 파라미터 정보 조회하기</h3>
<table><tr><th>파라미터이름</th><th>파라미터값</th></tr>
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