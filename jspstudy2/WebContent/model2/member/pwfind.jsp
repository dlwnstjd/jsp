<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>��й�ȣã��</title>
<link rel="stylesheet" href="../../css/main.css">
<script type="text/javascript">
	function inputcheck(f){
		if(f.email.value==''){
			alert("�̸��ϸ� �Է��ϼ���.");
			return false;
		}
		if(f.tel.value==''){
			alert("��ȭ��ȣ�� �Է��ϼ���.");
			return false;
		}
		if(f.id.value==''){
			alert("���̵� �Է��ϼ���.");
			return false;
		}
		return true;
	}
</script>
</head>
<body>
<form action="pwfind2.me" method="post" name="f" onsubmit="return inputcheck(this)">
<table>
	<tr><th>���̵�</th><td><input type="text" name="id"></td></tr>
	<tr><th>�̸���</th><td><input type="text" name="email"></td></tr>
	<tr><th>��ȭ��ȣ</th><td><input type="text" name="tel"></td></tr>
	<tr><td colspan ="2">
		<input type="submit" value="��й�ȣã��"></td></tr>
</table>
</form>
</body>
</html>