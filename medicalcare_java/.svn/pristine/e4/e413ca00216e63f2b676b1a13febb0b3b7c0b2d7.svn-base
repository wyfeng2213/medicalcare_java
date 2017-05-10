<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
%>
<c:set var="ctx" value="<%=path%>" />
<!-- <!doctype html> -->
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
	<title>无标题文档</title>
	<link href="${ctx }/skin/css/style2.css" rel="stylesheet"
		type="text/css" media="">
		<script type="text/javascript" src="${ctx }/skin/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="${ctx }/skin/js/jQuery.md5.js"></script>
		<script type="text/javascript" language="JavaScript">
			function checklogin() {
				var userName = document.getElementById("userName");
				var password = document.getElementById("userPassword");
				var verifyCode = document.getElementById("verifyCode");
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
				if (verifyCode.value == "") {
					alert("请输入验证码！");
					verifyCode.focus();
					return false;
				}
				$
						.ajax({
							type : "post",
							url : "${ctx }/web/secretaryLoginInfo/login.action",
							data : {
								"hUserName" : userName.value,
								"hUserPassword" : password.value,
								"verifyCode" : verifyCode.value
							},
							success : function(data) {
								if (data == "success") {
/*  								window.location.href = "${ctx }/web/secretaryLoginInfo/userGoto.action";
 */  							alert("成功");
 								}else if (data == "error") {
									alert("账号、密码或验证码错误，请重新登录！");
									document.getElementById('imgVcode').src='${ctx}/imgVcode?'+new Date();
									userName.focus();
								}else if(data == "frozen"){
									alert("账号已冻结，请稍后重试！");
								}
							}
						});
			}

			//重置
			function loginreset() {
				$("#userName").val("");
				$("#userPassword").val("");
				$("#userName").focus();
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

			//给一个 键盘事件
			function thisok(event) {
				event = event ? event : (window.event ? window.event : null);
				if (event.keyCode == 13) {
					checklogin();
				}
			}
			document.onkeydown = thisok;

			/**
			 * 验证字符串是否合法
			 * 
			 * @param string 需要验证的字符串
			 */
			function checkValid(string) {
				var reglur = /^[^\\\|"'<=>;\-+%@#&$]*$/;
				if (reglur.test(string)) {
					return true;
				} else {
					return false;
				}
			}
			$(function() {
				$("#userName").focus();
			});
		</script>
</head>

<body background="img/bgimg2.png"
	style="background-color: #999; background-size: cover;">

	<div class="big_box">
		<div class="title_box">
			<div class="welcomback">欢迎回来</div>

		</div>
		<div class="login_box">
			<div class="userbox">
				<img src="img/user2.png" /> <input type="text"
					placeholder="Username" id="userName" name="userName" />
			</div>
			<div class="passwordbox">
				<img src="img/passeword2.png" /> <input type="password"
					placeholder="Password" id="userPassword" name="userPassword" />
			</div>
			<div class="codebox">
				<input type="text" placeholder="Verifycode" id="verifyCode"
					name="verifyCode" /> <a href="javascript:void(0);" title="点击即可刷新"
					onclick="document.getElementById('imgVcode').src='${ctx}/imgVcode?'+new Date()">
					<img alt="点击即可刷新" align="absmiddle" style="display: inline;"
					id="imgVcode" width="110" height="50" />
				</a>
			</div>

			<div class="login_button">

				<input onclick="checklogin()" class="login" type="button" value="登陆"
					style="color: #ffffff;"> <input onclick="loginreset()"
					class="replacement" type="button" value="重置"
					style="color: #ffffff;">
			</div>

		</div>
	</div>

	<div class="bottom_box">
		<table class="bottom_box1">
			<tr>
				<td><a href="#" onclick="security_prompt()"/>安全规则 </a> &nbsp;|</td>
				<td>新闻中心 &nbsp;|</td>
				<td>法律申明 &nbsp;|</td>
				<td>关于移动</td>
			</tr>

		</table>
		<table class="bottom_box2">
			<tr>
				<td class="left_box" style="text-align: right;">京ICP备05002571号
					&nbsp; &nbsp;&nbsp;|</td>
				<td class="right_box" style="text-align: left;">&nbsp;
					&nbsp;&nbsp;中国移动通信版权所有</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript" language="JavaScript">
		document.getElementById('imgVcode').src='${ctx}/imgVcode?'+new Date();
	</script>
</body>
</html>
