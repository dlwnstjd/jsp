<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�α��� ȭ��</title>
<link rel="stylesheet" href="../../css/main.css">
<script type="text/javascript">
	function inputcheck(f){
		if(f.id.value==''){
			alert("���̵� �Է��ϼ���.");
			return false;
		}
		if(f.id.value.length < 4){
			alert("���̵�� 4�ڸ� �̻� �Է��ϼ���.");
			return false;
		}
		if(f.pass.value==''){
			alert("��й�ȣ�� �Է��ϼ���.");
			return false;
		}
		return true;
	}
</script>
</head>
<body>
<form action="login.me" method="post" name="f" onsubmit="return inputcheck(this)">
	<table><caption>�α���</caption>
		<tr><th>���̵�</th><td><input type="text" name="id"></td></tr>
		<tr><th>��й�ȣ</th><td><input type="password" name="pass"></td></tr>
		<tr><td colspan="2">
		<input type="submit" value="�α���">&nbsp;
		<input type="button" value="ȸ������" onclick="location.href='joinForm.me'">&nbsp;
		<input type="button" value="���̵�ã��" onclick="location.href='idfind.me'">&nbsp;
		<input type="button" value="��й�ȣã��" onclick="location.href='pwfind.me'">
		</td></tr>
	</table>
</form>
</body>
</html>