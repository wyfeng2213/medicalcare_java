<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/appwap/global.jsp" %>
<!DOCTYPE html>
<head>
<%@ include file="/commons/appwap/basejs.jsp" %>
<link rel="stylesheet" type="text/css" href="./css/idangerous.swiper.css">
<link rel="stylesheet" type="text/css" href="./css/style.css">
<script type="text/javascript" src="./js/idangerous.swiper.min.js"></script>
<script type="text/javascript" src="./js/base.js"></script>
<title>我</title>
</head>
<body>
<div class="head">
	<div class="top">
    	<a href="#" class="down"><img src="./images/icon_down.png" alt=""></a>
    	<a href="#" class="search"><img src="./images/icon_search.png" alt=""></a>
    </div>
	<div class="con">
    	<div class="user">
        	<div class="user_pic">
            	<img src="./images/user.png" alt="">
            </div>
            <div class="user_text">
            	<a href="#">点击登录</a>
                <p>看片更容易</p>
            </div>
        </div>
        <div class="email">
        	<a href="#" class="email_a"><img src="./images/icon_email.png" alt=""></a>
        </div>
    </div>
</div>
<div class="lists">
	<ul class="item">
    	<li><i class="jl"><img src="./images/icon_jl.png" alt=""></i><a href="#"><span><img src="./images/icon_sj.png" alt=""></span>播放记录</a></li>
    	<li><i class="sc"><img src="./images/icon_sc.png" alt=""></i><a href="#"><span><img src="./images/icon_sj.png" alt=""></span>我的收藏</a></li>
    	<li><i class="qy"><img src="./images/icon_qy.png" alt=""></i><a href="#"><span><img src="./images/icon_sj.png" alt=""></span>我的权益</a></li>
    </ul>
	<ul class="item">
    	<li><i class="rw"><img src="./images/icon_rw.png" alt=""></i><a href="#"><span><img src="./images/icon_sj.png" alt=""></span>我的任务</a></li>
    	<li><i class="jf"><img src="./images/icon_jf.png" alt=""></i><a href="#"><span><img src="./images/icon_sj.png" alt=""></span>我的积分</a></li>
    	<li><i class="hb"><img src="./images/icon_hb.png" alt=""></i><a href="#"><span><img src="./images/icon_sj.png" alt=""></span>我的红包</a></li>
    </ul>
	<ul class="item">
    	<li><i class="fk"><img src="./images/icon_fk.png" alt=""></i><a href="#"><span><img src="./images/icon_sj.png" alt=""></span>产品反馈</a></li>
    </ul>
</div>

<jsp:include page="inc/footer.jsp">
    <jsp:param name="index" value="3"/>
</jsp:include>

</body>
</html>

