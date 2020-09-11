<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>JSTL 형식화 태그 예제</title>
</head>
<body>
<h3>숫자 관련 형식 지정하기</h3>
<fmt:formatNumber value="500000" type="currency" currencySymbol="￦"/><br>
<fmt:formatNumber value="0.15" type="percent"/><br>
<fmt:formatNumber value="50000.345" pattern="###,###.00" /><br>
<fmt:setLocale value="en_US"/>	<%--지역설정 --%>
<fmt:formatNumber value="500000" type="currency"/><br>
<fmt:setLocale value="ko_KR"/>
<fmt:formatNumber value="500000" type="currency" currencySymbol="￦"/><br>
<h3>날짜 관련 형식 지정하기</h3>
<c:set var="today" value="<%=new java.util.Date() %>"/>
년원일: <fmt:formatDate value="${today}" type="date"/><br>
시분초: <fmt:formatDate value="${today}" type="time"/><br>
일   시: <fmt:formatDate value="${today}" type="both"/><br>
일   시1: <fmt:formatDate value="${today}" type="both"
			timeStyle="short" dateStyle="short"/><br>
일   시2: <fmt:formatDate value="${today}" type="both"
			timeStyle="long" dateStyle="long"/><br>
일   시3: <fmt:formatDate value="${today}" type="both"
			timeStyle="full" dateStyle="full"/><br>
일   시4: <fmt:formatDate value="${today}" type="both"
			timeStyle="full" dateStyle="full" timeZone="GMT"/><br>
형식지정: <fmt:formatDate value="${today }"
			pattern="yyyy년MM월dd일HH:mm:ss a E"/><br>
</body>
</html>