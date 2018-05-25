<!doctype html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<html>
<head>
<meta charset="utf-8">
<title>粤健康</title>
	<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="stylesheet" type="text/css"
		href="lib/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="lib/font-awesome/css/font-awesome.css">
	<link rel="stylesheet" type="text/css" href="stylesheets/theme.css">
	<link rel="stylesheet" type="text/css" href="stylesheets/premium.css">
	<link href="stylesheets/style2.css" rel="stylesheet"
		type="text/css">
	<script type="text/javascript" src="${ctx }/skin/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${ctx }/skin/js/jQuery.md5.js"></script>

<script type="text/javascript">
	function checklogin() {
		var userName = document.getElementById("userName");
		var password = document.getElementById("userPassword");
		if (userName.value == "") {
			alert("请输入账号！");
			userName.focus();
			return false;
		}
		if (!checkValid(userName.value)) {
			alert("输入的内容包含非法字符，请仔细检查！");
			userName.select();
			return false;
		}
		if (password.value == "") {
			alert("请输入密码！");
			password.focus();
			return false;
		}
		
		$.ajax({
					type : "post",
					url : "${ctx }/manage/systemUser/login.action",
					data : {
						"userName" : userName.value,
						"userPassword" : $.md5(password.value)
					},
					dataType: "json",
					success : function(data) {
						if (data.code == 200) {
							/* self.location = "${ctx }/manage/main.jsp"; */
							self.location = "${ctx }/view/sys/main.jsp";
						} else if (data.code == 204) {
							alert("账号、密码或验证码错误，请重新登录！");
							document.getElementById('imgVcode').src = '${ctx}/imgVcode?'
									+ new Date();
							userName.focus();
						} else if (data.code == 201) {
							alert("账号已冻结，请稍后重试！");
						}
					}
				});
	}
	
	function checkValid(string) {
		var reglur = /^[^\\\|"'<=>;\-+%@#&$]*$/;
		if (reglur.test(string)) {
			return true;
		} else {
			return false;
		}
	}
	
	//安全规则
	function security_prompt() {
		OpenWindow=window.open("", "newwindow", "left=200, top=100, height=700, width=900,toolbar=no,scrollbars=yes,menubar=no");
		OpenWindow.document.write("<HTML>");
		OpenWindow.document.write("<title>安全规则</title>");
		OpenWindow.document.write("<BODY BGCOLOR=#ffffff>"); 
		OpenWindow.document.write("<h1>安全规则</h1>"); 
		OpenWindow.document.write("<p>一、遵守国家法律和行政法规，不得利用国际联网危害国家安全、泄露国家秘密，不得侵犯国家的、社会的、集体的利益和公民的合法权益，不得从事违法犯罪活动。</p>");
		OpenWindow.document.write("<p>二、不得利用本网站平台、系统制作、发布和传播下列违法信息：</p>");
		OpenWindow.document.write("<p> （一）煽动抗拒、破坏宪法和法律、行政法规实施的；</p>");
		OpenWindow.document.write("<p> （二）煽动颠覆国家政权，推翻社会主义制度的；</p>");
		OpenWindow.document.write("<p> （三）煽动分裂国家、破坏国家统一的；</p>");
		OpenWindow.document.write("<p> （四）煽动民族仇恨、民族歧视，破坏民族团结的</p>");
		OpenWindow.document.write("<p> （五）捏造或者歪曲事实，散布谣言，扰乱社会秩序的；</p>");
		OpenWindow.document.write("<p> （六）宣扬封建迷信、淫秽、色情、赌博、暴力、凶杀、恐怖，教唆犯罪的；</p>");
		OpenWindow.document.write("<p> （七）公然侮辱他人或者捏造事实诽谤他人的；</p>");
		OpenWindow.document.write("<p> （八）损害国家机关信誉的；</p>");
		OpenWindow.document.write("<p> （九）其他违反宪法和法律、行政法规的。</p>");
		OpenWindow.document.write("<p>三、不得利用网站平台、系统从事下列危害计算机信息网络安全的活动：</p>");
		OpenWindow.document.write("<p> （一）未经允许，进入计算机信息网络或者使用计算机信息网络资源的；</p>");
		OpenWindow.document.write("<p> （二）未经允许，对计算机信息网络功能进行删除、修改或者增加的；</p>");
		OpenWindow.document.write("<p> （三）未经允许，对计算机信息网络中存储、处理或者传输的数据和应用程序进行删除、修改或者增加的；</p>");
		OpenWindow.document.write("<p> （四）故意制作、传播计算机病毒等破坏程序的；</p>");
		OpenWindow.document.write("<p> （五）其他危害计算机信息网络安全的。</p>");
		OpenWindow.document.write("</BODY>");
		OpenWindow.document.write("</HTML>");
		OpenWindow.document.close();
	}
	
</script>

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
</head>
<body class=" theme-blue">

	<div class="dialog">
		<div class="panel panel-default">
			<p class="panel-heading no-collapse">粤健康管理平台</p>
			<div class="panel-body">
				<form>
					<div class="form-group">
						<label>用户名</label> <input type="text" class="form-control span12"
							id="userName" name="userName">
					</div>
					<div class="form-group">
						<label>密码</label> <input type="password"
							class="form-control span12" id="userPassword"
							name="userPassword">
					</div>
					<a href="javascript:checklogin()" class="btn btn-primary pull-right">登录</a>
				</form>
			</div>
		</div>
	</div>
	
	<div class="bottom_box">
		<table class="bottom_box1">
			<tr>
				<td><a href="#" onclick="security_prompt()"/>安全规则 </a> &nbsp;|</td>
				<!-- <td>新闻中心 &nbsp;|</td>
				<td>法律申明 &nbsp;|</td>
				<td>关于移动</td> -->
			</tr>

		</table>
		<table class="bottom_box2">
			<tr>
				<td class="left_box" style="text-align: right;">粤ICP备08034647号-22
					&nbsp; &nbsp;&nbsp;|</td>
				<td class="right_box" style="text-align: left;">&nbsp;
					&nbsp;&nbsp;广东粤发健康信息科技有限公司版权所有</td>
			</tr>
		</table>
	</div>

</body>
</html>
