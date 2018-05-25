<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/wap/global_wap.jsp" %>
<!DOCTYPE html>
<head>
<%@ include file="/commons/wap/basejs_wap.jsp" %>
<link rel="stylesheet" href="${path}/view/wap/css/style_youku_1.css">
<script language="JavaScript">

var mobilestatus='${mobilestatus}';//0:valid, 1:invalid mobile
var productstatus='${productstatus}';//0:valid, 1:,2:product over time

$(function() {  
	if(productstatus=='0')
	{
		//
	}
	else //if(productstatus=='1')
	{
		//alert('no exist mcht product');
		$("#iddiv_buy").html('<a href="#" class="yes_handle">活动结束</a>');
	}
	
	$("#selectproductname_1").html('${mchtproduct.product.productName}');
	//$("#selectproductname_2").html('${mchtproduct.product.productName}');

	$("#${mchtproduct.product.productId}").addClass("product_checked");
	$("#productid").val('${mchtproduct.product.productId}');
	$("#mchturl").val('${mchtproduct.mchtUrl}');
	$("#mchtproductid").val('${mchtproduct.id}');

	
	mobilestatus ='${mobilestatus}';
	if(mobilestatus=='0')
	{
		$("#divshowinputmobile").css('display', 'none');
		$("#divshowmobile").css('display','block');
		$("#iddiv_usages").css('display','none');
	}
	else
	{
		$("#divshowinputmobile").css('display','block');;
		$("#divshowmobile").css('display','none');
		$("#iddiv_usages").css('display','block');
	}
});

function getMobileSmsno()
{
	var mobile = $("#inputmobile").val();
	var reg = /1+[0-9]{10}/;
	if(mobile.length != 11 || reg.test(mobile) == false){
		alert("必须是11位移动手机号码,确定后重新输入...");
		return false;
	}
	var jsondata = {
		"msgid":"10001003",
		"country_code":"86",
		"mobile": mobile
	};
	
	$("#resultsms").html('');
	openWindowTips();

	var url = 'wap/getAuthSmsCode.do';
	invokeService(url, jsondata, function(result) {
		if (result.retcode == 0)
		{
			$("#mobile").val(mobile);
			//alert("请注意查收短信");
		}
		else
		{
			//alert(result.retmsg);
			$("#resultsms").html(result.retmsg);
		}
		closeWindowTips();
	});
}
function checkMobileSmsno()
{
	var mobile = $("#inputmobile").val();
	var reg = /1+[0-9]{10}/;
	if(mobile.length != 11 || reg.test(mobile) == false){
		alert("必须是11位移动手机号码,确定后重新输入...");
		return false;
	}
	
	var password = $("#smsno").val();
	if(password.length<=0)
	{
		alert("请输入短信验证码");
		return;
	}
	var jsondata = {
		"msgid":"10001005",
		"country_code":"86",
		"mobile": mobile,
		"password":password
	};
	
	$("#resultsms").html('');
	openWindowTips();

	var url = 'wap/checkAuthSmsCode.do';
	invokeService(url, jsondata, function(result) {
		if (result.retcode == 0)
		{
			mobilestatus='0';
			$("#divshowinputmobile").css('display','none');
			$("#divshowmobile").css('display','block');
			$("#divshowmobile").html("您的号码为："+mobile);
			$("#iddiv_usages").css('display','none');
		}
		else
		{
			//alert(result.retmsg);
			mobilestatus='1';
			$("#resultsms").html(result.retmsg);
			//$("#divshowmobile").html("您的号码："+result.retmsg);
			$("#iddiv_usages").css('display','block');
		}
		closeWindowTips();
	});
}


function subscributeProduct()
{
	if(productstatus=='1')
	{
		alert('对不起，活动已结束');
		return;
	}
	var mobile = $("#mobile").val();
	if(mobilestatus!='0' || mobile == "")
	{
		alert('获取手机号失败!');
		return;
	}
	if(mobile == "")
	{
		mobile = $("#inputmobile").val();//;
	}
	if(mobile=="")
	{
		alert('请输入手机号');
		return;
	}
	
	var productid = $("#productid").val();
	if(productid=="")
	{
		alert('请选择您好订购的流量包产品');
		return;
	}
	//alert(productid);
	var productname='${mchtproduct.product.productName}';
	if(confirm("确认要订购《"+productname+"》吗？")==false)
	{
		return;
	}
	var mchtproductid = $("#mchtproductid").val();
	var jsondata = {
		"mchtproductid":mchtproductid,
		"mobile": mobile,
		"product_id": productid
	};
	
	openWindowTips();

	var url = 'wap/subscribeProduct.do';
	invokeService(url, jsondata, function(result) {
		if (result.retcode == 0 || result.retcode == 6 || result.retcode == 7)
		{
			//alert(result.retmsg);
			$("#divtipscontent").html(result.product_name+"办理成功<br>前往《<a href='"+result.mcht_url+"' class='pro_return'>获取会员权益</a>》");
		} 
		else if(result.retcode == 1)
		{
			$("#divtipscontent").html(result.product_name+"已开通，无需重复办理<br>前往《<a href='"+result.mcht_url+"' class='pro_return'>获取会员权益</a>》");
		}
		else if(result.retcode == 422)//timeout
		{
			$("#divtipscontent").html(result.retmsg+"<br>前往《<a href='"+result.mcht_url+"' class='pro_return'>获取会员权益</a>》");
		}
		else {
			var theErrMsg = result.retmsg || '未知错误类型';
			$("#divtipscontent").html(theErrMsg);
		}
	});
	
}
function openWindowTips()
{
	$("#divtranslucent").css('display','block');
	$("#divprompt").css('display','block');
	$("#divtipscontent").html("<img src='${path}/view/wap/images/load.gif'/><br>请耐心等候，正在处理中... ");
}

function closeWindowTips()
{
	$("#divtranslucent").css('display','none');
	$("#divprompt").css('display','none');
}



var timer1;
function closeTimerAndTipsWindows()
{
	closeWindowTips();
	clearInterval(timer1);
}
function queryMyCurrentOrder()
{
	var mobile = $("#mobile").val();
	if(mobilestatus!='0' || mobile == "")
	{
		alert('获取手机号失败!');
		return;
	}
	if(mobile == "")
	{
		mobile = $("#inputmobile").val();//;
	}
	if(mobile=="")
	{
		alert('请输入手机号');
		return;
	}
	var productid = $("#productid").val();
	if(productid=="")
	{
		alert('请选择您好订购的流量包产品');
		return;
	}
	
	timer1 = setInterval(closeTimerAndTipsWindows, 10000);
	var mchtcode="${mchtproduct.mchtCode}";
	var jsondata = {
		"msgid":"10010008",
		"mobile": mobile,
		"mcht_code":mchtcode,
		"productid": productid
	};	
	
	$("#id_myproductlist").html('');
	openWindowTips();

	var url = 'wap/queryMyCurrentOrder.do';
	invokeService(url, jsondata, function(result) {

		closeWindowTips();
		if (result.retcode == 0) {
			
			var ncount=0;
			var outhtml="";
			var obj = result.body;
			$.each(obj, function (n, value) {
				var product_id = value.product_id;					
				var product_name = value.product_name;
				var mcht_url = value.mcht_url;

				outhtml+="<div class='myproduct_div_left'><span>您已订购:"+product_name+"</span></div>";
				outhtml+="<div class='myproduct_div_right'><a href='"+mcht_url+"'><span>获取会员权益</span></a></div>";
				
				if($("#productid").val()==product_id)
				{
					$("#hasproudct_icon").css('display','block');;
				}
			
				ncount++;
			});
			$("#id_myproductlist").html(outhtml);
			if(ncount == 0)
			{
				$("#id_myproductlist").html("<div class='myproduct_div_left'><span>尊敬的用户，您暂未订购${mchtproduct.product.productTitle}</span></div>");
			}
		} else {
			var theErrMsg = result.retmsg || '未知错误类型';
			$("#id_myproductlist").html(theErrMsg);
		}			
	});
}

function gotoMchtUrl()
{
	var url = $("#mchturl").val();
	window.location.href=url;
}

</script>
<title>订购流量快餐</title>
</head>

<body>
	<input type="hidden" id="mchtproductid" name="mchtproductid" value="${mchtproduct.id}">
	<input type="hidden" id="productid" name="productid" value="${mchtproductid.product.productId}">
	<input type="hidden" id="mobile" name="mobile" value="${mobile}">
	<input type="hidden" id="mchturl" name="mchturl" value="${mchtproduct.mchtUrl}">

	<div class="block_div">
		<img src="${path}/view/wap/images/youku/top_1.png" width="100%" height="100%" alt="" >
	</div>
	<div class="block_div">
		<div class="discount">
			<!--<div id='hasproudct_icon' class="already_handled" style='display:block'></div>-->
			<div id='hasproudct_icon' class="already_handled" style='display:none'></div>
			<div class="llth">
				<img src="${path}/view/wap/images/youku/youku_logo.png" width="74" height="74" alt="" >
			</div>
			<div class="l_name">
				<p class="title" id="selectproductname_1"><!--南方会员权益流量特惠包--></p>					
				<p class="abstract">全省 | 全球通 神州行 动感地带</p>
				<div id="divshowmobile" style="display:block">您的号码为：${mobile}</div>
				<div id="divshowinputmobile" style="display:none">
					<p class="abstract">
						<input type="number" class="mobileinput" id="inputmobile" name="inputmobile" value="${mobile}" oninput="if(value.length>11)value=value.slice(0,11)">
						<input type="button" class="btn_getsms" value="获取短信" onclick="javascript:getMobileSmsno();">
					</p>
					<p class="abstract">
						<input type="number" class="smsnoinput" id="smsno" name="smsno" value="" oninput="if(value.length>11)value=value.slice(0,11)">
						<input type="button" class="btn_getsms" value="短信验证" onclick="javascript:checkMobileSmsno();">
					</p>
					<span id="resultsms"></span>
				</div>
			</div>
		</div>
	</div>
	<div class="block_div" id="iddiv_usages">
		<div class="contentdiv">
			<p>获取号码失败：第一步：输入自己的手机号码、点击“获取短信”；第二步：输入短信验证码，并点击“短信验证”；成功后即可进行业务办理。”</p>
		</div>
	</div>
	<div class="block_div">
		<div class="handle_btn" id="iddiv_buy">
			<a href="javascript:subscributeProduct();" class="yes_handle">马上购</a>
		</div>
	</div>
	<div class="block_div">
		<div class="contentdiv">
			<p>温馨提示：若订购成功后，当时未领取优酷会员权益，<br>
			可点击下面按钮找回权益！
			</p>
		</div>
	</div>
	<!-- 获取会员权益-begin -->
	<div class="block_div">
		<div class="handle_btn">
				<a href="javascript:queryMyCurrentOrder();" class="yes_handle">找回权益</a>
			</div>
		<!-- 我订购的流量包-begin -->
		<div class="myproduct" id="id_myproductlist">
			
		</div>
		<!-- 我订购的流量包-end -->
	</div>
	<!-- 获取会员权益-end -->
	<div class="block_div">
		<div class="divtipcontent">
		
		</div>
	</div>
	<div class="block_div">
		<div class="busdetail_head">
			<span class="divleft"><img src="${path}/view/wap/images/youku/line.png" width="100%" height="0.625em" alt="" >
				</span>业务详情
			<span class="divright"><img src="${path}/view/wap/images/youku/line.png" width="100%" height="0.625em" alt="" ></span>
		</div>
		<div class="busdetail_title">			
	 		<span>9.99元1G优酷视频权益流量包，内含1G省内通用流量以及优酷黄金会员权益（免广告、海量会员大片、1080P高清等专属权益），有效期7天。</span>
		</div>
		<div class="busdetail_content">	
			<span>1、产品扣费规则：</span>
			9.99元1G优酷视频权益流量包在活动有效期内限办理1次，仅能通过基本账户进行扣费。<br>
			<span>2、产品生效规则：</span>办理立即生效，9.99元优酷视频权益流量包有效期7天，流量包内所含流量为省内2/3/4G通用流量。<br>
			<span>3、产品并存规则：</span><br>
			  (1)4G随心王客户不可办理。如需办理，需先主动取消上述套餐。<br>
			  (2)与其他流量套餐可并存。<br>
			<span>4、流量封顶规则：</span>流量包所含流量遵循套外500元、流量15G双封顶，总流量50G封停规则（本产品不计入套外500元范围内）。<br>
			<span>5、使用周期：</span>在指定期限内流量有效，不受月结日影响，超过指定有效期流量清零。<br>
			<span>6、视频会员权益获取：</span><br>
			（1）客户通过短信方式订购成功后，即可收到系统下发的订购生效短信，内含优酷视频黄金会员权益领取链接，点击该链接即可进入优酷视频黄金会员权益领取页面，确认权益获取手机号并点击“立即领取”按钮后即领取成功，稍后将收到由优酷土豆系统端口下发的权益密码短信，获取短信后使用订购手机号及权益密码登陆优酷客户端或网页会员专区，即可查询/使用会员权益。<br>
			（2）客户通过10086APP、线上H5活动页面等方式订购订购成功后，将直接跳转到优酷视频黄金会员权益领取页面，确认权益获取手机号并点击“立即领取”按钮后即领取成功，稍后将收到由优酷土豆系统端口下发的权益密码短信，获取短信后使用订购手机号及权益密码登陆优酷客户端或网页会员专区，即可查询/使用会员权益。<br>
		
		</div>
	</div>
	<div class="block_div">
		<div class="divtipcontent">
		
		</div>
	</div>
	<!-- 办理成功灰色背景框-begin -->
	<div id="divtranslucent" class="translucent" style="display:none"></div><!-- 办理成功灰色背景框-end -->
	<!-- 办理成功提示框-begin -->
	<div id="divprompt" class="prompt" style="display:none">
		<p>提示</p>
		<div class="pro_content" id="divtipscontent">
			<!--9.99元1G南方会员权益流量特惠包办理成功。前往<a href="javascript:void(0);">前往获取会员权益。</a>-->
			<img src="${path}/view/wap/images/load.gif"/><br>请耐心等候，正在处理中... 
		</div>
		<div class="pro_button">
			<a href="javascript:closeWindowTips()" class="pro_close">关闭</a>         
		</div>
	</div>
	<!-- 办理成功提示框-end -->
	<div class="block_div">
		<img src="${path}/view/wap/images/youku/bottom_1.png" width="100%" height="100%" alt="" >
	</div>
</body>
</html>
