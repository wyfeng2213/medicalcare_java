<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/appwap/global.jsp" %>
<!DOCTYPE html>
<head>
<%@ include file="/commons/appwap/basejs.jsp" %>
<link rel="stylesheet" type="text/css" href="./css/idangerous.swiper.css">
<link rel="stylesheet" type="text/css" href="./css/style.css">
<script type="text/javascript" src="./js/idangerous.swiper.min.js"></script>
<script type="text/javascript" src="./js/base.js"></script>
<script language="javascript">
$(function(){
	/*
	$('.swiper-slide .title .btn').click(function(){
		MessageBox.confirm("确定订购流量包","确定订购19.9元7天20G里约奥运流量包吗？");
	})
	*/
	$('#divyouku9').click(function(){
		window.location.href="http://gd.10086.cn/yst/index.html?from=youku&productid=1GYTHYTHB";
	})
})
</script>
<title>热点</title>
</head>

<body>
<!-- 轮播 -->
<div class="swiper-container">
    <div class="swiper-wrapper">
        <div class="swiper-slide blue-slide">
            <img src="./images/banner/hot01.png" alt="">
            <div class="title" id="divyouku9">
                <a href="javascript:void(0)" class="btn red">立即开通</a>
            </div>
        </div>
    </div>
    <div class="pagination"></div>
</div>

<script>
var mySwiper = new Swiper('.swiper-container',{
    pagination: '.pagination',
    paginationClickable: true,
	autoplay : 2000
  })
</script>

<div class="list">
	<div class="title"><h2>赛事直播</h2></div>
    <ul>
		<!--
    	<li>
        	<a href="#">
            	<img src="./images/videocover/hot/hot01.png" alt="">
                <span><em>#2016里约奥运会#</em>五大期望</span>
            </a>
        </li>
    	<li>
        	<a href="#">
            	<img src="./images/videocover/hot/hot02.png" alt="">
                <span><em>#2016里约奥运会#</em>攻略</span>
            </a>
        </li>
		-->
		<li>
        	<a href="http://v.youku.com/v_show/id_XMTY3ODgzNTE0MA==.html">
            	<img src="./images/videocover/hot/hot_01.png" alt="">
                <span><em>#2016里约奥运会#</em>女足</span>
            </a>
        </li>
    	<li>
        	<a href="http://v.youku.com/v_show/id_XMTY4NjU0NDQwNA==.html">
            	<img src="./images/videocover/hot/hot_02.png" alt="">
                <span><em>#2016里约奥运会#</em>傅园慧</span>
            </a>
        </li>
    	<li>
        	<a href="#">
            	<img src="./images/videocover/hot/hot03.png" alt="">
                <span><em>#2016奥运会#</em>游泳冠军竞猜</span>
            </a>
        </li>
    	<li>
        	<a href="#">
            	<img src="./images/videocover/hot/hot04.png" alt="">
                <span><em>#2016奥运会#</em>周末赛事精选</span>
            </a>
        </li>
    </ul>
</div>
<div class="list">
	<div class="title"><h2>奥运热点</h2></div>
    <ul>
    	<li>
        	<a href="#">
            	<img src="./images/videocover/hot/hot11.png" alt="">
                <span><em>#里约奥运会#</em>人们欢呼</span>
            </a>
        </li>
    	<li>
        	<a href="#">
            	<img src="./images/videocover/hot/hot12.png" alt="">
                <span>人们在里约</span>
            </a>
        </li>
    	<li>
        	<a href="#">
            	<img src="./images/videocover/hot/hot13.png" alt="">
                <span><em>#里约奥运会#</em>冠军竞猜</span>
            </a>
        </li>
    	<li>
        	<a href="#">
            	<img src="./images/videocover/hot/hot14.png" alt="">
                <span><em>#里约奥运会#</em>周末赛事精选</span>
            </a>
        </li>
    </ul>
</div>
<div class="list">
	<div class="title"><h2>精彩回放</h2></div>
    <ul>
    	<li>
        	<a href="#">
            	<img src="./images/videocover/hot/hot21.png" alt="">
                <span>博尔特又拿金牌</span>
            </a>
        </li>
    	<li>
        	<a href="#">
            	<img src="./images/videocover/hot/hot22.png" alt="">
                <span>里约奥运会开幕式</span>
            </a>
        </li>
    	<li>
        	<a href="#">
            	<img src="./images/videocover/hot/hot23.png" alt="">
                <span>金牌的味道</span>
            </a>
        </li>
    	<li>
        	<a href="#">
            	<img src="./images/videocover/hot/hot24.png" alt="">
                <span>漂洋过海来看你</span>
            </a>
        </li>
    </ul>
</div>
<jsp:include page="inc/footer.jsp">
    <jsp:param name="index" value="1"/>
</jsp:include>
<%@ include file="inc/dialog.jsp"%>
</body>
</html>

