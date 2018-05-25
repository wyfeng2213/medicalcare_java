<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/wap/global_wap.jsp" %>
<!DOCTYPE html>
<head>
<%@ include file="/commons/wap/basejs_wap.jsp" %>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<meta name="apple-touch-fullscreen" content="yes" />
<link rel="stylesheet" href="${path}/view/wap/css/style.css">
<script language="JavaScript">

$(function() {  
    <c:forEach items="${productlist}" var="mplist" varStatus="vs">
		//alert('${mplist.product.productId}');
		if(${vs.index==0})
		{
			//alert('${mplist.product.productId}');
			document.getElementById('selectproductname_3').innerHTML='${mplist.product.productName}';
			document.getElementById('id_selectproductdays').innerHTML='${mplist.product.effectDuration}';
			document.getElementById('selectproductname_1').innerHTML='${mplist.product.productName}';
			document.getElementById('selectproductname_2').innerHTML='${mplist.product.productName}';
				

			document.getElementById('${mplist.product.productId}').className="product_checked";
			document.getElementById('inputproductid').value='${mplist.product.productId}';
			//alert(document.getElementById('inputproductid').value);
			document.getElementById('mpid').value='${mplist.id}';
		}
	</c:forEach> 
});

	/*第一种形式 第二种形式 更换显示样式*/
	function clickTab_1(){
		document.getElementById('one1').className="hover";
		document.getElementById('one2').className="";
		var con=document.getElementById("con_one_1").style.display="block";
		var con=document.getElementById("con_one_2").style.display="none";
	}
	function clickTab_2(){
		document.getElementById('one2').className="hover";
		document.getElementById('one1').className="";
		var con=document.getElementById("con_one_2").style.display="block";
		var con=document.getElementById("con_one_1").style.display="none";
	}
	function chooseproduct(pid)
	{
		var elems = document.getElementsByName("divproductname");
		for(i = 0; i < elems.length; i++) {
			elems[i].className='product_unchecked';
		}
		document.getElementById(pid).className="product_checked";

		<c:forEach items="${productlist}" var="mplist" varStatus="vs">
			//alert('${mplist.product.productId}');
			if('${mplist.product.productId}' == pid)
			{
				//alert('${mplist.product.productId}');
				document.getElementById('selectproductname_3').innerHTML='${mplist.product.productName}';
				document.getElementById('selectproductname_1').innerHTML='${mplist.product.productName}';
				document.getElementById('selectproductname_2').innerHTML='${mplist.product.productName}';
				
				document.getElementById('id_selectproductdays').innerHTML='${mplist.product.effectDuration}';
				document.getElementById('divproductdetail').innerHTML='${mplist.product.productDetail}';
				

				document.getElementById('inputproductid').value='${mplist.product.productId}';
				document.getElementById('mpid').value='${mplist.id}';
			}
		</c:forEach> 
		/*
		if(pid=='1')
		{
			document.getElementById('product1').className="product_checked";
			document.getElementById('product2').className="product_unchecked";
		}
		if(pid=='2')
		{
			document.getElementById('product1').className="product_unchecked";
			document.getElementById('product2').className="product_checked";
		}
		*/
	}

	function subscributeProduct()
	{
		var mobile = document.getElementById('mobile').value;
		if(mobile=="")
		{
			alert('获取手机号失败!');
			return;
		}
		if($("#checkprotocol").prop("checked") != true)
		{
			alert("您必须同意流量包购买协议条款");
			return;
		}
		var pid = document.getElementById('inputproductid').value;
		if(pid=="")
		{
			alert('请选择您好订购的流量包产品');
			return;
		}
		//alert(pid);
		
		var mpid = document.getElementById('mpid').value;
		var jsondata = {
			"mpid":mpid,
			"mobile": mobile,
			"product_id": pid
		};
		document.getElementById("divshowmessage").style.display="block";
		var url = 'wap/subscribeProduct.do';
		invokeService(url, jsondata, function(result) {
			if (result.retcode == 0) {
				//alert(result.retmsg);
				document.getElementById("tdsucceed").innerHTML="您的流量包订购成功<br>前往<a href='"+result.mcht_url+"' class='pro_return'>"+result.mcht_name+"</a>获取会员权益";
			} else {
				var theErrMsg = result.retmsg || '未知错误类型';
				document.getElementById("tdsucceed").innerHTML=theErrMsg;
			}
			//document.getElementById("divshowmessage").style.display="none";
		});
		/*
		//alert(JSON.stringify(jsondata));
		$.ajax({
			url: 'wap/subscribeProduct.do',
			type: "POST",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(jsondata),			
			dataType: "json",
			success : function(e) {
				var result = JSON.parse(e);
				//callback(theResult);
				if(result.retcode==0)
				{
				}
				else
				{
					alert(result.retmsg);
				}
			},
			error : function(e) {
				alert("网络连接错误!");
			}
		});
		*/
	}
	function closeShowMessage()
	{
		document.getElementById("divshowmessage").style.display="none";
	}


	function queryMyOrder()
	{
		var mobile = document.getElementById('mobile').value;
		if(mobile=="")
		{
			alert('获取手机号失败!');
			return;
		}
		var jsondata = {
			"msgid":10010008,
			"mobile": mobile
		};
		var url = 'wap/queryMobileOrder.do';
		invokeService(url, jsondata, function(result) {
			if (result.retcode == 0) {
				//alert(result.retmsg);
				document.getElementById("myproductname").innerHTML="已办理："+result.order.product_name;
				document.getElementById("myproductmchturl").innerHTML="<a href='"+result.order.mcht_url+"'>获取会员权益</a>";
				document.getElementById("hasproudct").className="already_handled";
				
			} else {
				var theErrMsg = result.retmsg || '未知错误类型';
				document.getElementById("myproductname").innerHTML=result.retmsg;
				document.getElementById("tdsucceed").innerHTML=theErrMsg;
			}
			//document.getElementById("divshowmessage").style.display="none";
		});
	}
</script>
<title>订购流量快餐</title>
</head>

<body>
	<input type="hidden" id="mpid" name="mpid" value="">
	<input type="hidden" id="inputproductid" name="inputproductid" value="">
	<input type="hidden" id="mobile" name="mobile" value="${mobile}">
	<div id="whole_div">
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
                    <li class="li_1">首页</li>
                    <li class="nav_arrow"></li>
                    <li class="li_1">办理</li>
                    <li class="nav_arrow"></li>
                    <li class="li_2" id="selectproductname_2">南方会员权益流量特惠包</li>
                    <li class="nav_arrow"></li>
                    <p class="clear"></p>
                </ul>
            </div><!-- 位置-end -->
            <!-- 南方会员权益流量特惠包-begin -->
            <div class="discount">
				<!--<div id='hasproudct' class="already_handled" style='display:block'></div>-->
				<div id='hasproudct' style='display:block'></div>
            	<div class="llth">
                    <img src="${path}/view/wap/images/llth.png" width="74" height="74" alt="" >
                </div>
                <div class="l_name">
                    <p class="title" id="selectproductname_1">南方会员权益流量特惠包</p>
					<p class="abstract">${mobile}</p>
                    <p class="abstract">全省 | 全球通 神州行 动感地带</p>
                </div>
            </div>
            <!--<div class="discount">
                <div class="llth">
                    <img src="${path}/view/wap/images/llth.png" width="74" height="74" alt="" >
                </div>
                <div class="l_name">
                    <p class="title">南方会员权益流量特惠包</p>
                    <p class="abstract">全省 | 全球通 神州行 动感地带</p>
                </div>
                <p class="clear"></p>
            </div>-->
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
							<table class="table_white" align="center" width="100%" cellspacing="0" cellpadding="0">
							<c:forEach items="${productlist}" var="mplist" varStatus="vs">
							<c:if test="${vs.index%2==0}">
							<tr>
							</c:if>
								<td width="50%">
									<div class="product_unchecked" id="${mplist.product.productId}" name='divproductname' onclick="javascript:chooseproduct('${mplist.product.productId}')">${mplist.product.productTitle}</div>
								</td>
								<!--
								<td>
									<div class="product_unchecked" id="product2" onclick="javascript:choceproduct('2')">19.99元20G</div>
								</td>
								-->
							<c:if test="${vs.index%2==1}">
							</tr>
							</c:if>
							</c:forEach> 
							</table>
                            <p class="clear"></p>
                        </div><!-- 按钮-end -->
                        <!-- 选择的套餐-begin -->
                        <div class="dottedline">
                            <p>您已选择：<span id='selectproductname_3'><!--9.99元1G南方会员权益流量特惠包--></span></p>
                            <p style="letter-spacing:4.5px">有限期：<span id='id_selectproductdays'><!--7--></span><span>天</span></p>
                        </div><!-- 选择的套餐-end -->
                        <!-- 获取会员权益-begin -->
                        
                        </div><!-- 立即办理按钮-end -->
                    </div><!-- 业务办理显示框-end -->
                    <!-- 业务详情框-begin -->
                    <div id="con_one_2" style="display:none"><!-- 业务详情框-end -->
						<div id="divproductdetail" class="flow_content">业务详情内容</div>
					</div>
					<div class="equity">
						<img onclick="queryMyOrder();" title='查询我的订购的流量包' style="cursor:pointer;" src='${staticPath}/images/view.gif' border='0'/>			
						<span id="myproductname"><!--已办理：99元1G套餐--></span>
						<div id="myproductmchturl">获取会员权益</div>
					</div><!-- 获取会员权益-end -->
					<!-- 详细内容请阅读-begin -->
					<div class="read">
						<span class="span_1">详细内容请阅读</span>
						<span class="span_2">《</span><a href="href="javascript:void(0);"">业务详情</a><span class="span_2">》</span>
						<input type="checkbox" id="checkprotocol" style="width:15px; height:15px;" /><span class="span_1">我已阅读</span>
					</div><!-- 详细内容请阅读-end -->
					<!-- 立即办理按钮-begin -->
					<div class="handle_btn">
						<a href="javascript:subscributeProduct();" class="yes_handle">立即办理</a>
                </div><!-- 显示选择框-end -->
            </div><!-- 业务办理、业务详情-end -->
        </div><!-- 中部-end -->
        <!-- 底部-begin -->
        <div class="bottom">
        	<div>
            	<a href="javascript:void(0);">客户端</a>
                <a href="javascript:void(0);">电脑版</a>
                <a href="javascript:void(0);">服务与支持</a>
            </div>
            <p>京ICP备05002571 | 中国移动版权</p>
        </div><!-- 底部-end -->
        
		<!-- 办理成功灰色背景框-begin -->
        <div id="divtranslucent" class="translucent" style="display:none"></div><!-- 办理成功灰色背景框-end -->
        <!-- 办理成功提示框-begin -->
        <div id="divprompt" class="prompt" style="display:none">
        	<p>提示</p>
            <div class="pro_content">
            	9.99元1G南方会员权益流量特惠包办理成功。前往<a href="javascript:void(0);">前往获取会员权益。</a>
            </div>
            <div class="pro_button">
            	<a href="javascript:void(0);" class="pro_return">返回</a>
                <a href="javascript:void(0);" class="pro_confirm">确认</a>
                <div class="clear"></div>
                <div class="pr0_k"></div>
            </div>
        </div><!-- 办理成功提示框-end -->


		<div id="divshowmessage" class="prompt_message" style="display:none">
        	<span>提示</span>
            <div class="message_load">
            	<table class="table_white" align="center" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td id="tdsucceed"><img src="${path}/view/wap/images/load.gif"/><br>正在为您处理中，请耐心等候</td>
				</tr>
				</table>
            </div>
            <div class="product_buy_button" onclick="javascript:closeShowMessage()">关闭</div>
        </div><!-- 办理成功提示框-end -->
    </div>
</body>
</html>
