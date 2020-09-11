<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html lang="en">
<title><decorator:title /></title>
<script type="text/javascript" 
	src="http://www.chartjs.org/dist/2.9.3/Chart.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
html,body,h1,h2,h3,h4,h5,h6 {font-family: "Roboto", sans-serif;}
.w3-sidebar {
  z-index: 3; 
  width: 250px;
  top: 43px;
  bottom: 0;
  height: inherit;
}
</style>
<script type="text/javascript"
	src="http://cdn.ckeditor.com/4.5.7/full/ckeditor.js"></script>
<decorator:head />
<body>
<!-- Navbar -->
 <table>
	<tr bgcolor="black"><td align="right">
		<span style="float:right">
		<c:if test="${empty sessionScope.login }">
			<a href="${path}/model2/member/loginForm.me">�α���</a>
			<a href="${path}/model2/member/joinForm.me">ȸ������</a>
		</c:if>
		<c:if test="${!empty sessionScope.login }">
			<a href="${path}/model2/member/logout.me">�α׾ƿ�</a>
		</c:if></span>
	</td></tr>		
</table>

<!-- Sidebar -->
<nav class="w3-sidebar w3-bar-block w3-collapse w3-large w3-theme-l5 w3-animate-left" id="mySidebar">
  <a href="javascript:void(0)" onclick="w3_close()" class="w3-right w3-xlarge w3-padding-large w3-hover-black w3-hide-large" title="Close Menu">
    <i class="fa fa-remove"></i>
  </a>
  <h4 class="w3-bar-item"><b>Menu</b></h4>
  <a class="w3-bar-item w3-button w3-hover-black" href="${path}/model2/member/main.me">ȸ������</a>
  <a class="w3-bar-item w3-button w3-hover-black" href="${path}/model2/board/list.do">�Խ���</a>
  <a class="w3-bar-item w3-button w3-hover-black" href="${path}/model2/chat/chatform.do">ä��</a>

	<div class="w3-container">
		<%--ajax�� ���� ���� ȯ�� ���� ���� ��� --%>
		<div id="exchange">
		</div>
	</div>
	<div class="w3-container">
		<%--KEB�ϳ����� ���� ����ϱ�:
		USD, JPY, EUR, CNY: �Ÿű�����, ������Ƕ�, �����ĽǶ� --%>
		<div id="exchange2">
		</div>
	</div>
</nav>

<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- Main content: shift it to the right by 250 pixels when the sidebar is visible -->
<div class="w3-main" style="margin-left:250px">

  <div class="w3-row w3-padding-64">
  
	<div class="w3-row-padding w3-margin-bottom">
		<div class="w3-half">
			<div class="w3-container w3-padding-16">
				<div id="piecontatiner" style="width:80%; border: 1px solid #000000">
				<canvas id="canvas1" style="width:100%;"></canvas>
				</div>			
			</div>
		</div>
		<div class="w3-half">
			<div class="w3-container w3-padding-16">
				<div id="barcontatiner" style="width:80%; border: 1px solid #000000">
				<canvas id="canvas2" style="width:100%;"></canvas>
				</div>			
			</div>
		</div>
	</div>
	
    <div class="w3-twothird w3-container">
      <decorator:body/>
    </div>
    
  </div>
  


  <footer id="myFooter">
    <div class="w3-container w3-theme-l2 w3-padding-32">
      <h4>�����ī���� Since 2016</h4>
    </div>

    <div class="w3-container w3-theme-l1">
      <p>Powered by <a href="http://www.gdu.co.kr/main/main.html" target="_blank">�����ī����</a></p>
    </div>
  </footer>

<!-- END MAIN -->
</div>

<script>
// Get the Sidebar
var mySidebar = document.getElementById("mySidebar");

// Get the DIV with overlay effect
var overlayBg = document.getElementById("myOverlay");

// Toggle between showing and hiding the sidebar, and add overlay effect
function w3_open() {
  if (mySidebar.style.display === 'block') {
    mySidebar.style.display = 'none';
    overlayBg.style.display = "none";
  } else {
    mySidebar.style.display = 'block';
    overlayBg.style.display = "block";
  }
}

// Close the sidebar with the close button
function w3_close() {
  mySidebar.style.display = "none";
  overlayBg.style.display = "none";
}
</script>
<script type="text/javascript">
	var randomColorFactor = function(){
		return Math.round(Math.random() * 255);
	}
	var randomColor = function(opa){
		return "rgba(" + randomColorFactor() + ","
				+ randomColorFactor() + ","
				+ randomColorFactor() + ","
				+ (opa || '.3') + ")";
	}
	$(function(){
		piegraph();
		bargraph();
		exchangeRate();	//ȯ�������� ajax�� ���� ũ�Ѹ��ϱ�.
		exchangeRate2();
	})
	
	function exchangeRate(){
		$.ajax("${path}/model2/ajax/exchange.do",{
			success: function(data){
				$("#exchange").html(data);
			},
			error: function(e){
				alert("ȯ�� ��ȸ�� ���� ����:" + e.status);
			}
		})
	}
	
	function exchangeRate2(){
		$.ajax("${path}/model2/ajax/exchange2.do",{
			success: function(data){
				$("#exchange2").html(data);
			},
			error: function(e){
				alert("ȯ�� ��ȸ�� ���� ����:" + e.status);
			}
		})
	}
	
	function piegraph(){
		$.ajax("${path}/model2/ajax/graph.do",{
			success: function(data){
				pieGraphPrint(data);
			},
			error: function(e){
				alert("���� ����:" + e.status);
			}
		})
	}
	function bargraph(){
		$.ajax("${path}/model2/ajax/graph2.do",{
			success: function(data){
				barGraphPrint(data);
			},
			error: function(e){
				alert("���� ����:" + e.status);
			}
		})
	}
	function pieGraphPrint(data){
		console.log(data)
		var rows = JSON.parse(data);
		var names = []
		var datas = []
		var colors = []
		$.each(rows,function(index,item){
			names[index] = item.name;
			datas[index] = item.cnt;
			colors[index] = randomColor(1);
		})
		var config = {
			type: 'pie',
			data: {
				datasets: [{
					data: datas,
					backgroundColor: colors
				}],
				labels: names
			},
			options: {
				responsive: true,
				legend: {position: 'top'},
				title:{
					display: true,
					text: '�۾��� �� �Խ��� ��� �Ǽ�',
					position: 'bottom'
				}
			}		
		}
		var ctx = document.getElementById("canvas1").getContext("2d");
		new Chart(ctx,config);
	}
	function barGraphPrint(data){
		console.log(data)
		var rows = JSON.parse(data);
		var dates = []
		var datas = []
		var colors = []
		$.each(rows,function(index,item){
			dates[index] = item.date;
			datas[index] = item.cnt;
			colors[index] = randomColor(1);
		})
		var config = {
				type: 'bar',
				data: {	labels: dates,
						datasets:[
						{
							type: 'line',
							borderColor: colors,
							borderWidth: 2,
							label: '�Ǽ�',
							fill: false,
							data: datas
						},{
							type: 'bar',
							label: '�Ǽ�',
							backgroundColor: colors,
							data: datas,
							borderWidth: 2
						}]
				},
				options:{
					responsive: true,
					title: {display: true,
							text: '�ϼ� �� �Խ��� ��� �Ǽ�',
							position: 'bottom'
					},
					legend: {display: false},
					scales:{
						xAxes:[{
							display: true,
							scaleLabel: {
								display: true,
								labelString: "�Խù� �ۼ���"
							},
							stacked: true	
						}],
						yAxes: [{
							display: true,
							scaleLabel: {
								display: true,
								labelString: "�Խù� �ۼ��Ǽ�"
							},
							stacked: true	//�⺻�� 0���� ����
						}]
					}
				}
			};
			var ctx = document.getElementById("canvas2").getContext("2d");
			new Chart(ctx, config);
	}
</script>
</body>
</html>
