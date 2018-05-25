<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	String base = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort();
%>
<c:set var="ctx" value="<%=path%>" />

<html lang="en">
<head>
<meta charset="UTF-8"/>
<meta http-equiv="Content-Language" content="zh-CN" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<title>粤健康</title>
<link rel="stylesheet" href="css/style.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/touch.js"></script>
<script src="js/function.js"></script>
</head>
<style>
	 html,body{height:100%}
	 .containe{min-height: 100%;}
</style>
<body>
<div class="containe">
	<div class="back">
			<a href="javascript:history.go(-1)">
	  		<img src="images/img_back.png">
  		</a>
  		<p>粤健康</p>
  	</div>
  	<!-- <iframe id='inner_frame' src="index2.html"  style="width:7.5rem;height: 13.5rem;margin-top:0.85rem;">	
  	</iframe> -->
  	<iframe id='inner_frame' src="index3.jsp"  style="width:7.5rem;height: 13.5rem;margin-top:0.85rem;">	
  	</iframe>
</div>
<script>
function showBar(){
$('#inner_frame').css('margin-top','0.85rem');
$('.back').show();
}
function hideBar(){
$('#inner_frame').css('margin-top','0rem');
$('.back').hide();
}

</script>
</body>
</html>