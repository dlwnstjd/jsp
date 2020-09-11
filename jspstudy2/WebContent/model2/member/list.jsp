<%@page import="java.awt.Graphics2D"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="java.io.File"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="java.util.List"%>
<%@page import="model.MemberDao"%>
<%@page import="model.Member"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>ȸ�� ��� ����</title>
<script type="text/javascript">
	function allchkbox(allchk){
		$("input[name=mailchk]").prop("checked",allchk.checked)
	}
</script>
<link rel="stylesheet" href="../../css/main.css">
</head>
<body>
<form action="mailform.me" method="post">
<table><caption>ȸ�� ���</caption>
<tr><th>���̵�</th><th>����</th><th>�̸�</th><th>����</th><th>��ȭ</th>
<th>�̸���</th><th>&nbsp;</th>
<th><input type="checkbox" name="alchk" onchange="allchkbox(this)"></th>
</tr>
<c:forEach var="m" items="${list }">231
<tr><td><a href="info.me?id=${m.id }">${m.id }</a></td>
<td><img src="picture/sm_${m.picture }" width="20" height="20" id="pic"></td>
<td>${m.name }</td><td>${m.gender==1?"��":"��"}</td>
<td>${m.tel }</td><td>${m.email }</td>
<td><a href="updateForm.me?id=${m.id }">[����]</a>
<c:if test="${m.id != 'admin' }">
<a href="delete.me?id=${m.id }">[����Ż��]</a>
</c:if></td>
<td><input type="checkbox" name="mailchk" value="${m.email }"></td>
</tr></c:forEach>
<tr><td colspan="8" align="center">
	<input type="submit" value="��������"></td></tr>
</table></form></body></html>






