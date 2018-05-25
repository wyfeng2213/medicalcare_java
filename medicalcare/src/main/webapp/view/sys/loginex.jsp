<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>用户登录</title>
<meta name="viewport" content="width=device-width">
<%@ include file="/commons/basejs.jsp" %>    
<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
<meta name="description" content="Login and Registration Form with HTML5 and CSS3" />
<meta name="keywords" content="html5, css3, form, switch, animation, :target, pseudo-class" />
<meta name="author" content="Codrops" />
<link rel="shortcut icon" href="../favicon.ico"> 
<link rel="stylesheet" type="text/css" href="${staticPath }/plugin/loginpage/css/demo.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/plugin/loginpage/css/style.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/plugin/loginpage/css/animate-custom.css" />

<script type="text/javascript" src="${staticPath }/js/common.js" charset="utf-8"></script>
<script type="text/javascript">
	$(function () {
		// 登录
		$('#loginform').form({
			url:'${path }/sys/login.do',
			onSubmit : function() {
				progressLoad();
				var isValid = $(this).form('validate');
				if(!isValid){
					progressClose();
				}
				return isValid;
			},
			success:function(result){
				progressClose();
				result = $.parseJSON(result);
				if (result.success) {
					window.location.href='${path }/sys/index.do';
				}else{
					 $.messager.show({
						title:'提示',
						msg:'<div class="light-info"><div class="light-tip icon-tip"></div><div>'+result.msg+'</div></div>',
						showType:'show'
					});
				}
			}
		});
	});
	function submitLoginForm(){
		$('#loginform').submit();
	}
	function clearForm(){
		$('#loginform').form('clear');
	}
	//回车登录
	function enterlogin(){
		if (event.keyCode == 13){
			event.returnValue=false;
			event.cancel = true;
			$('#loginform').submit();
		}
	}

	function submitRegisterForm() {
		var url = "${path }/sys/register.do";
		var formdata = $("#registerform").serializeArray();
		var postData = {};
		$.each(formdata, function() 
		{
			postData[this.name] = this.value;
		});
		//alert(JSON.stringify(postData));
		//alert(formdata);
		$.messager.progress({title : '提示', text : '数据处理中，请稍后....'});
		invokeService(url, postData, function(result) {
			if (result.retcode == 0) {
				window.location.href='${path }/sys/index.do';				
			} else {
				$.messager.alert('消息',result.retmsg);
			}
			$.messager.progress('close');
		});
	}
</script>
</head>
<body onkeydown="enterlogin();">
	<div class="container">
		<header style="height:5em">
		</header>
		<section>				
			<div id="container_demo" >
				<!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
				<a class="hiddenanchor" id="toregister"></a>
				<a class="hiddenanchor" id="tologin"></a>
				<div id="wrapper">
					<div id="login" class="animate form">
						<form method="post" id="loginform">
							<h1>Log in</h1> 
							<p> 
								<label for="username" class="uname" data-icon="u" > Your username </label>
								<input id="username" name="username" required="required" type="text" placeholder="username, eg. admin"/>
							</p>
							<p> 
								<label for="password" class="youpasswd" data-icon="p"> Your password </label>
								<input id="password" name="password" required="required" type="password" placeholder="password, eg. 123456" /> 
							</p>
							<p class="keeplogin"> 
								<input type="checkbox" name="loginkeeping" id="loginkeeping" value="loginkeeping" /> 
								<label for="loginkeeping">Keep me logged in</label>
							</p>
							<p class="login button"> 
								<input type="submit" value="Login" onclick="submitLoginForm()"/> 
							</p>
							<p class="change_link">
								Not a member yet ?
								<a href="#toregister" class="to_register">Join us</a>
							</p>
						</form>
					</div>

					<div id="register" class="animate form">
						<form method="post" id="registerform"> 
							<h1> Sign up </h1> 
							<p> 
								<label for="usernamesignup" class="uname" data-icon="u">Your username</label>
								<input id="usernamesignup" name="usernamesignup" required="required" type="text" placeholder="username" />
							</p>
							<p> 
								<label for="emailsignup" class="youmail" data-icon="e" > Your email</label>
								<input id="emailsignup" name="emailsignup" required="required" type="email" placeholder="mysupermail@mail.com"/> 
							</p>
							<p> 
								<label for="passwordsignup" class="youpasswd" data-icon="p">Your password </label>
								<input id="passwordsignup" name="passwordsignup" required="required" type="password" placeholder="eg. X8df!90EO"/>
							</p>
							<p> 
								<label for="passwordsignup_confirm" class="youpasswd" data-icon="p">Please confirm your password </label>
								<input id="passwordsignup_confirm" name="passwordsignup_confirm" required="required" type="password" placeholder="eg. X8df!90EO"/>
							</p>
						</form>
							<p class="signin button"> 
								<input type="button" value="Sign up" onclick="submitRegisterForm()"/> 
							</p>						
							<p class="change_link">  
								Already a member ?
								<a href="#tologin" class="to_register"> Go and log in </a>
							</p>
						
					</div>
					
				</div>
			</div>  
		</section>
	</div>
</body>
</html>