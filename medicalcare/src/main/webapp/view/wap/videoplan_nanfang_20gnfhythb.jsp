<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/wap/global_wap.jsp" %>
<!DOCTYPE html>
<head>
<%@ include file="/commons/wap/basejs_wap.jsp" %>
<link rel="stylesheet" href="${path}/view/wap/css/style.css">
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
	$("#selectproductname_3").html("${mchtproduct.product.productName}");
	$("#selectproductdetail_3").html('${mchtproduct.product.productDetail}');
	
	$("#selectproductname_1").html('${mchtproduct.product.productName}');
	$("#selectproductname_2").html('${mchtproduct.product.productName}');

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

/*第一种形式 第二种形式 更换显示样式*/
function clickTab_1(){
	$("#one1").addClass('hover');
	$("#one2").removeClass();;
	$("#con_one_1").css('display','block');
	$("#con_one_2").css('display','none');
}
function clickTab_2(){
	$("#one2").addClass('hover');
	$("#one1").removeClass();
	$("#con_one_2").css('display','block');
	$("#con_one_1").css('display','none');
}
function chooseproduct(pid)
{
	
	$("div[name='divproductname']").each(function(index,item){
		$(item).attr('class', 'product_unchecked');
		//alert("fuck");
	});
	/*
	var elems = document.getElementsByName("divproductname");
	for(i = 0; i < elems.length; i++) {
		elems[i].className='product_unchecked';
	}
	*/
	$("#"+pid).addClass('product_checked');
	if('${mchtproduct.product.productId}' == pid)
	{
		//alert('${mplist.product.productId}');
		$("#selectproductname_3").html('${mchtproduct.product.productName}');
		$("#selectproductdetail_3").html('${mchtproduct.product.productDetail}');
		$("#selectproductname_1").html('${mchtproduct.product.productName}');
		$("#selectproductname_2").html('${mchtproduct.product.productName}');

		$("#productid").val('${mchtproduct.product.productId}');
		$("#mchturl").val('${mchtproduct.mchtUrl}');
		$("#mchtproductid").val('${mchtproduct.id}');
	}

	$("#hasproudct_icon").css('display','none');
	$.each(myprodlist, function (n, value) {
		if(pid==myprodlist[n])
		{
			$("#hasproudct_icon").css('display','block');
		}
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
	if($("#checkprotocol").prop("checked") != true)
	{
		alert("您必须同意流量包购买协议条款");
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


var myprodlist = new Array();
function queryMyOrder()
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
	
	timer1 = setInterval(closeTimerAndTipsWindows, 10000);

	var jsondata = {
		"msgid":"10010008",
		"mobile": mobile
	};
	$("#id_myproductlist").html('');
	openWindowTips();

	var url = 'wap/queryMobileOrder.do';
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
				myprodlist.push(product_id);

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
				myprodlist.push(product_id);

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
	<div class="whole_div">
    	<!-- 头部-begin -->
    	<div class="header">
        	<!-- logo-begin -->
        	<div class="logo">
            	<img src="${path}/view/wap/images/ydlogo.png" width="28" height="28" alt="" />
                <span>广东移动</span>
            </div><!-- logo-end -->
            <!-- 登录-begin -->
            <div class="login">
            	<a href="javascript:void(0);">登录</a><span>|</span><a href="javascript:void(0);" class="search"></a>
            </div><!-- 登录-end -->
            <div class="clear"></div>
        </div><!-- 头部-end -->
        <!-- 中部-begin -->
        <div class="central">
        	<!-- 位置-begin -->
            <div class="location">
                <ul>
                    <li class="home"></li>
                    <li class="li_1"><a href="http://wap.gd.10086.cn/nwap/index.shtml">首页</a></li>
                    <li class="nav_arrow"></li>
                    <li class="li_1"><a href="http://wap.gd.10086.cn/nwap/products/products/index.jsps">办理</a></li>
                    <li class="nav_arrow"></li>
                    <li class="li_2" id="selectproductname_2">南方会员权益流量特惠包</li>
                    <p class="clear"></p>
                </ul>
            </div><!-- 位置-end -->
            <!-- 南方会员权益流量特惠包-begin -->
            <div class="discount">
				<!--<div id='hasproudct_icon' class="already_handled" style='display:block'></div>-->
				<div id='hasproudct_icon' class="already_handled" style='display:none'></div>
            	<div class="llth">
                    <img src="${path}/view/wap/images/llth.png" width="74" height="74" alt="" >
                </div>
                <div class="l_name">
                    <p class="title" id="selectproductname_1">南方会员权益流量特惠包</p>					
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
            <div class="tip_usages" id="iddiv_usages">
                <p class="usages">获取号码失败：第一步：输入自己的手机号码、点击“获取短信”；第二步：输入短信验证码，并点击“短信验证”；成功后即可进行业务办理。”</p>
            </div>
            <!-- 南方会员权益流量特惠包-begin -->
            <!-- 业务办理、业务详情-begin -->
            <div class="functional">
                <div class="survicer"></div>
                <!-- 点击选择框-begin -->
                <div class="Menubox">
                    <ul>
                        <li id="one1" onclick="clickTab_1()" class="hover">业务办理</li>
                        <li id="one2" onclick="clickTab_2()">业务详情</li>
                        <p class="clear"></p>
                    </ul>
                </div><!-- 点击选择框-end -->
                <!-- 显示选择框-begin -->
                <div class="Contentbox">
                    <!-- 业务办理显示框-begin -->
                    <div id="con_one_1" class="hover">
                        <div class="choice">请选择套餐档次:</div>
                        <!-- 按钮-begin -->
                        <div class="but_div">
							<div class="product_checked" id="${mchtproduct.product.productId}" name='divproductname' onclick="javascript:chooseproduct('${mchtproduct.product.productId}')">${mchtproduct.product.productTitle}</div>
                            <p class="clear"></p>
                        </div><!-- 按钮-end -->
                        <!-- 选择的套餐-begin -->
                        <div class="dottedline">
                            <p>您已选择：<span id='selectproductname_3'><!--9.99元1G南方会员权益流量特惠包--></span></p>
							<p>产品介绍：<span id='selectproductdetail_3' style="color:#666"><!--9.99元1G南方会员权益流量特惠包--></span></p>
                            <!--<p style="letter-spacing:4.5px">有限期：<span id='id_selectproductdays'></span><span>天</span></p>-->
                        </div><!-- 选择的套餐-end -->
                        
                        <!-- 详细内容请阅读-begin -->
						<div class="read">
							<span class="span_1">详细内容请阅读</span>
							<span class="span_2">《</span>业务详情<span class="span_2">》</span>
							<input type="checkbox" id="checkprotocol" style="width:15px; height:15px;" /><span class="span_1">我已阅读</span>
						</div><!-- 详细内容请阅读-end -->
						<!-- 立即办理按钮-begin -->
						<div class="handle_btn" id="iddiv_buy">
							<a href="javascript:subscributeProduct();" class="yes_handle">立即办理</a>
						</div>
					</div><!-- 立即办理按钮-end -->
				</div><!-- 业务办理显示框-end -->
				<!-- 业务详情框-begin -->
				<div id="con_one_2" style="display:none"><!-- 业务详情框-end -->
					<div id="divproductdetail" class="flow_content">
					<span>1、扣费规则：</span>19.99元 “指点一夏” 视频权益流量在有效期内限办理1次，仅能通过基本账户进行扣费。<br>					
					<span>2、生效规则：</span>办理立即生效，19.99元 “指点一夏” 视频权益流量包有效期14天，流量包内所含流量为省内10G通用流量。<br>					
					<span>3、并存规则：</span><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1）4G随心王客户不可办理。如需办理，需先主动取消上述套餐。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2）与其他流量套餐可并存。<br>
					<span>4、流量封顶规则：</span>流量包所含流量遵循套外500元、流量15G双封顶，总流量50G封停规则（本产品订购消费不计入套外500元的统计范围内）。<br>
					<span>5、使用周期：</span>在指定期限内流量有效，不受月结日影响，超过指定有效期流量清零。<br>
					<span>6、权益规则：</span><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（1）客户通过短信方式订购成功后，即可收到系统下发的订购生效短信，内含南方传媒“指点一夏”会员权益领取链接，点击该链接即可进入“指点一夏”视频主页。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（2）客户通过10086APP、线上H5活动页面等方式订购订购成功后，将直接跳转到南方传媒“指点一夏”视频主页。<br>
					<span>7、视频会员权益使用：</span>客户进入登录页，输入订购手机号码即可使用南方传媒“指点一夏”会员身份登陆，会员权益有效期7天。<br>
					</div>
				</div>
				
            </div><!-- 业务办理、业务详情-end -->
        </div><!-- 中部-end -->
        
        <!-- 获取会员权益-begin -->
		<div class="equity">
			<a href="javascript:queryMyCurrentOrder();"> <img title='查询我的订购的流量包' style="cursor:pointer;" src='${staticPath}/images/view.gif' border='0'/>
			<span id="myproductmchturl">视频权益专区</span></a>
		</div><!-- 获取会员权益-end -->
		<!-- 我订购的流量包-begin -->
		<div class="myproduct" id="id_myproductlist">
			
		</div>
		<!-- 我订购的流量包-end -->

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
        </div><!-- 办理成功提示框-end -->

    </div>
	<!-- 底部-begin -->
        <div class="bottom">
        	<div>
            	<a href="http://gd.10086.cn/wap2012/notice/zsymqd.shtml?WT.mc_id=GDALAPPX0521W0I0001">客户端</a>
                <a href="http://wap.gd.10086.cn/nwap/index.shtml">电脑版</a>
                <a href="http://wap.gd.10086.cn/nwap/index/serviceAndSupport.jsps">服务与支持</a>
            </div>
            <p>京ICP备05002571 | 中国移动版权</p>
        </div>
		<!-- 底部-end -->
</body>
</html>
