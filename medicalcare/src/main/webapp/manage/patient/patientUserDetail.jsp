<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	String base = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
%>
<c:set var="ctx" value="<%=path%>" />
<!doctype html>
<!–[if IE]>
<meta http-equiv=”X-UA-Compatible” content=”IE=8″ /> 
<![endif]–>
<!–[if IE 7]> 
<meta http-equiv=”X-UA-Compatible” content=”IE=7″ /> 
<![endif]–> 
<!–[if IE 6]> 
<meta http-equiv=”X-UA-Compatible” content=”IE=6″ /> 
<![endif]–> 
<html>
<head>
<meta charset="utf-8">
<title>患者详细资料</title>

<link rel="stylesheet" type="text/css" href="lib/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="lib/font-awesome/css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="stylesheets/theme.css">
<link rel="stylesheet" type="text/css" href="stylesheets/premium.css">
<link rel="stylesheet" href="<%=path%>/skin/css/list.css">
<link rel="stylesheet" href="<%=path%>/skin/css/kkpager_blue.css">

<script src="lib/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="<%=path%>/skin/js/jquery-1.10.2.min.js"></script>
<script src="<%=path%>/skin/js/kkpager.min.js"></script>

<style>
	.left_box input{
		width:100px;
	}
</style>

</head>

<body>
hello world!!!!!!!!!!!
<input type="text" id="patientName_input" class="patientName_input" name="patientName_input" value="${empty patientInfoDetail.name?'':patientInfoDetail.name}" />
</body>
</html>
