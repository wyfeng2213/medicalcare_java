<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/commons/mobile/global_mobile.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/mobile/basejs_mobile.jsp" %>
<title>优酷播放器</title>
<base href="${pageContext.request.contextPath}/view/mobile/" />
<!--<script type="text/javascript" src="js/base.js"></script>-->
<style type="text/css">
*{ margin:0; padding:0;}
body {
	background-color: rgb(247, 248, 249);
	height:100%; 
	width:100%; 
}
</style>
<script type="text/javascript" src="http://player.youku.com/jsapi"></script>
<script type="text/javascript">
	window.onload = bodyInit; 
	function bodyInit(){
		document.getElementById("youkuplayer").style.height=document.getElementById("youkuplayer").offsetWidth*3/4+"px";
	}

	player = new YKU.Player('youkuplayer',{ 
		styleid: '0',
		client_id: '72b230ff5250f97a',
		vid: '${vid}',
		newPlayer: true,
		autoplay: true,
		show_related: false,
		events:{
			onPlayEnd: function(){ /*your code*/ }
		}
	});
</script>
</head>

<body>
<div id="youkuplayer" style="width:100%;height:400px"></div>
</body>
</html>
