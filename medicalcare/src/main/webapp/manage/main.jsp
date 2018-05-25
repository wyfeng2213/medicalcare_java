<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.cmcc.medicalcare.model.SystemUser"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%-- <%@ include file="/common/include.jsp"%> --%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	String base = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort();
	SystemUser systemUser = (SystemUser) request.getSession()
			.getAttribute("systemUser");
%>
<c:set var="ctx" value="<%=path%>" />
<html lang="en">
<head>
<meta charset="utf-8">
	<title>粤健康</title>
	<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="stylesheet" type="text/css" href="lib/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="lib/font-awesome/css/font-awesome.css">
	<script type="text/javascript" src="${ctx }/skin/js/jquery-1.8.3.js"></script>
	<script src="lib/jQuery-Knob/js/jquery.knob.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			$(".knob").knob();
		});

		function setFrameSrc(url) {
			$("#iframe-page-content").attr("src", url);

		}
		
		function logout() {
			if (confirm('系统提示，您确定要退出本次登录吗?')) {  
	            location.href = "${ctx}/manage/systemUser/logout.action";  
	        }
		}
		
	</script>

	<link rel="stylesheet" type="text/css" href="stylesheets/theme.css">
	<link rel="stylesheet" type="text/css" href="stylesheets/premium.css">
</head>
<body class=" theme-blue">

	<style type="text/css">
		#line-chart {
			height: 300px;
			width: 800px;
			margin: 0px auto;
			margin-top: 1em;
		}

		.navbar-default .navbar-brand, .navbar-default .navbar-brand:hover {
			color: #fff;
		}
</style>

	<script type="text/javascript">
		$(function() {
			var uls = $('.sidebar-nav > ul > *').clone();
			uls.addClass('visible-xs');
			$('#main-menu').append(uls.clone());
		});
	</script>

	<div class="navbar navbar-default" role="navigation">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="" href="index.html"><span class="navbar-brand"><span
					class="fa fa-paper-plane"></span> 粤健康</span></a>
		</div>

		<div class="navbar-collapse collapse" style="height: 1px;">
			<ul id="main-menu" class="nav navbar-nav navbar-right">
				<li class="dropdown hidden-xs"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"> <span
						class="glyphicon glyphicon-user padding-right-small"
						style="position: relative; top: 3px;"></span> <%=systemUser.getUserRealname()%>
						<i class="fa fa-caret-down"></i>
				</a>

					<ul class="dropdown-menu">
						<li><a tabindex="-1" href="#" id="logout" onclick="logout();">注销</a></li>
					</ul></li>
			</ul>

		</div>
	</div>

	<div class="sidebar-nav">
		<ul>
			<li><a href="#" data-target=".dashboard-menu_1"
				class="nav-header collapsed" data-toggle="collapse"> <i
					class="fa fa-fw fa-dashboard"></i>系统管理<i class="fa fa-collapse"></i></a>
			</li>
			<li><ul class="dashboard-menu_1 nav nav-list collapse in">
					<li><a href="#"
						onclick="setFrameSrc('./doctor/doctorlist.html');"><span
							class="fa fa-caret-right"></span> 机构管理</a></li>
					<li><a href="#" onclick="setFrameSrc('index.html');"><span
							class="fa fa-caret-right"></span> 角色管理</a></li>
					<li><a href="#" onclick="setFrameSrc('index.html');"><span
							class="fa fa-caret-right"></span> 管理员管理</a></li>
					<li><a href="#" onclick="setFrameSrc('index.html');"><span
							class="fa fa-caret-right"></span> 日志管理</a></li>
				</ul></li>

			<li><a href="#" data-target=".dashboard-menu_2"
				class="nav-header collapsed" data-toggle="collapse"> <i
					class="fa fa-fw fa-legal"></i>医生管理<i class="fa fa-collapse"></i></a></li>
			<li><ul class="dashboard-menu_2 nav nav-list collapse">
					<li><a href="index.html"><span class="fa fa-caret-right"></span>
							医生档案</a></li>
					<li><a href="user.html"><span class="fa fa-caret-right"></span>
							注册医生审核</a></li>
					<li><a href="media.html"><span class="fa fa-caret-right"></span>
							医生团队管理</a></li>
					<li><a href="media.html"><span class="fa fa-caret-right"></span>
							医生服务记录</a></li>
				</ul></li>

			<li><a href="#" data-target=".dashboard-menu_3"
				class="nav-header collapsed" data-toggle="collapse"> <i
					class="fa fa-fw fa-dashboard"></i>患者管理<i class="fa fa-collapse"></i></a>
			</li>
			<li><ul class="dashboard-menu_3 nav nav-list collapse">
					<li><a href="#" onclick="setFrameSrc('${ctx}/manage/adminPatientUser/getPatientInfo.action');"><span class="fa fa-caret-right"></span>
							患者档案</a></li>
					<li><a href="user.html"><span class="fa fa-caret-right"></span>
							患者消息管理</a></li>
					<li><a href="media.html"><span class="fa fa-caret-right"></span>
							签约管理</a></li>
				</ul></li>

			<li><a href="#" data-target=".dashboard-menu_4"
				class="nav-header collapsed" data-toggle="collapse"> <i
					class="fa fa-fw fa-dashboard"></i>助理管理<i class="fa fa-collapse"></i></a>
			</li>
			<li><ul class="dashboard-menu_4 nav nav-list collapse">
					<li><a href="index.html"><span class="fa fa-caret-right"></span>
							助理申请</a></li>
					<li><a href="user.html"><span class="fa fa-caret-right"></span>
							助理档案</a></li>
					<li><a href="media.html"><span class="fa fa-caret-right"></span>
							助理服务记录</a></li>
				</ul></li>

			<li><a href="#" data-target=".dashboard-menu_5"
				class="nav-header collapsed" data-toggle="collapse"> <i
					class="fa fa-fw fa-dashboard"></i>通知<i class="fa fa-collapse"></i></a>
			</li>
			<li><ul class="dashboard-menu_5 nav nav-list collapse">
					<li><a href="index.html"><span class="fa fa-caret-right"></span>
							通知</a></li>
				</ul></li>


			<li><a href="#" data-target=".dashboard-menu_6"
				class="nav-header collapsed" data-toggle="collapse"> <i
					class="fa fa-fw fa-dashboard"></i>运营<i class="fa fa-collapse"></i></a>
			</li>
			<li><ul class="dashboard-menu_6 nav nav-list collapse">
					<li><a href="#" onclick="setFrameSrc('chartsStatistics.jsp');"><span class="fa fa-caret-right"></span>
							数据分析</a></li>
				</ul></li>

		</ul>

	</div>

	<div class="content" style="height: 100%">
		<iframe id="iframe-page-content" src="./userslist.html" width="100%"
			height="100%" frameborder="no" border="0" marginwidth="0"
			marginheight=" 0" scrolling="no" allowtransparency="yes"
			style="height:"></iframe>

	</div>


	<script src="lib/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript">
		$("[rel=tooltip]").tooltip();
		$(function() {
			$('.demo-cancel-click').click(function() {
				return false;
			});
		});
	</script>


	<style>
html, body {
	height: 100%;
}
</style>

</body>
</html>
