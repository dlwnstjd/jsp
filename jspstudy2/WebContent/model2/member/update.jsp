<%@page import="model.Member"%>
<%@page import="model.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<%--
 1. 모든 파라미터 정보를 Member 객체에 저장
 2. 입력된 비밀번호와, db에 저장된 비밀번호를 비교
 	- 비밀번호가 같지 않을경우: 비밀번호 오류 메세지 출력 updateForm.jsp 페이지 이동
 3. 파라미터를 저장하고 있는 Member객체를 이용하여 db 정보 수정
 	- int MemberDao.update(Member)
 결과가 0이하면 수정실패 메세지 출력 후, updateForm.jsp 페이지 이동
 	1이상이면 수정 성공	info.jsp 페이지 이동
 --%>
 <% request.setCharacterEncoding("euc-kr"); %>
	<jsp:useBean id="mem" class="model.Member"/>
	<jsp:setProperty property="*" name="mem"/>
<%
	MemberDao dao = new MemberDao();
	String login = (String)session.getAttribute("login");
	Member dbmem = dao.selectOne(mem.getId());
	String msg = "비밀번호가 틀렸습니다.";
	String url = "updateForm.me?id=" + mem.getId();
	if(login.equals("admin") || mem.getPass().equals(dbmem.getPass())){
		int result = dao.update(mem);
		if(result > 0){
			response.sendRedirect("info.me?id=" + mem.getId());
		}else{
			msg = "수정실패";
		}
	}%>
<script>
	alert("<%=msg%>")
	location.href="<%=url%>"
</script>