<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%--
	EL(ǥ�����): ǥ������ ��ü ���.
				${��}
				�������� ǥ�� ���� => �Ӽ��̳� �Ķ���͸� ǥ�� ����
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>elEx1.jsp�� ��� ȭ��</title>
</head>
<body>
<%
	request.setCharacterEncoding("euc-kr");
	String tel = "010-1111-2222"; 	
	String test = "ddddddd";
	pageContext.setAttribute("tel", tel);
	pageContext.setAttribute("test", "pageContext test �Ӽ�");
	request.setAttribute("test", "request test �Ӽ�");
	application.setAttribute("test", "application test �Ӽ�");
%>
<h3>JSP�� ��ũ��Ʈ�� �̿��Ͽ� �Ķ���Ϳ� �Ӽ��� ���</h3>
pageContext test �Ӽ���: <%=pageContext.getAttribute("test") %><br>
session test �Ӽ���: <%=session.getAttribute("test") %><br>
today �Ӽ���: <%=session.getAttribute("today") %><br>
name �Ķ���Ͱ�: <%=request.getParameter("name") %><br>
tel ������: <%=tel %><br>
tel �Ӽ���: <%=pageContext.getAttribute("tel") %><br>
noAttr �Ӽ���: <%=pageContext.getAttribute("noAttr") %><br>
noparam �Ķ���Ͱ�: <%=request.getAttribute("noparam") %><br>

<h3>JSP�� EL(ǥ��)�� �̿��Ͽ� �Ķ���Ϳ� �Ӽ��� ���</h3>
test �Ӽ���: ${test}<br>
session test �Ӽ���: ${sessionScope.test}<br>
today �Ӽ���: ${today}<br>
name �Ķ���Ͱ�: ${param.name}<br>
<%--�Ϲݺ����� <%=%> ����� --%>
tel ������: EL�� ǥ�� ����. <%=tel %><br>
tel �Ӽ���: ${tel}<br>
noAttr �Ӽ���: ${noAttr}<br>
noparam �Ӽ���: ${param.noparam}<br>
<br>
<h3>������ �����Ͽ� test �Ӽ��� ���</h3>
\${test}=${test}<br>
\${pageScope.test}=${pageScope.test}<br>
\${requestScope.test}=${requestScope.test}<br>
\${sessionScope.test}=${sessionScope.test}<br>
\${applicationScope.test}=${applicationScope.test}<br>
\${tel}=${tel}<br>
\${requestScope.tel}=${requestScope.tel}<br>
<%--
	${test}: ������� ��ü�� ����� �Ӽ� �� �̸��� test�� ���� ����
	1. pageContext ��ü�� ����� �Ӽ� �� �̸��� test�� �Ӽ��� ���� ����
	2. 1���� �ش��ϴ� �Ӽ��� ���� ���
		request ��ü�� ����� �Ӽ� �� �̸��� test�� �Ӽ��� ���� ����
	3. 1,2���� �ش��ϴ� �Ӽ��� ���� ���
		session ��ü�� ����� �Ӽ� �� �̸��� test�� �Ӽ��� ���� ����
	4. 1,2,3���� �ش��ϴ� �Ӽ��� ���� ���
		application ��ü�� ����� �Ӽ� �� �̸��� test�� �Ӽ��� ���� ����
	5. 1,2,3,4���� �ش��ϴ� �Ӽ��� ���� ���
		null�� �ƴϰ�, �ƹ��͵� ��¾���. �����߻� �ȵ�.
	- ���� ���� ��簴ü�� �Ӽ��� �����Ͽ� ����ϱ�
		pageContext ��ü�� �Ӽ� �� test��: ${pageScope.test}
		request ��ü�� �Ӽ� �� test��: ${requestScope.test}
		session ��ü�� �Ӽ� �� test��: ${sessionScope.test}
		application ��ü�� �Ӽ� �� test��: ${applicationScope.test}
 --%>
</body>
</html>