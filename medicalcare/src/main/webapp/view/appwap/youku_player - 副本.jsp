<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/commons/mobile/global_mobile.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/mobile/basejs_mobile.jsp" %>
<title>优酷播放器</title>
<base href="${pageContext.request.contextPath}/view/mobile/" />
<!--<script type="text/javascript" src="js/base.js"></script>-->
<style type="text/css">
*{ margin:0; padding:0;}
body {
	background-color: rgb(247, 248, 249);
	height:100%; 
	width:100%; 
}
</style>
<script type="text/javascript">
	function iFrameHeight()
	{
		var bodyw=document.body.clientWidth;
		document.getElementById("ifr_youku").style.height=bodyw*3/4+"px";
	}
</script>
</head>

<body>
<!--
<embed src="http://player.youku.com/player.php/sid/XNDMyODY1OTEy/v.swf?VideoIDS=XNDA3OTM4NA=&isAutoPlay=true&isShowRelatedVideo=false&embedid=-&showAd=0 " allowFullScreen="true" quality="high" width="100%" height="400" align="middle" allowScriptAccess="always" type="application/x-shockwave-flash"></embed>
-->
<div style="height:100%">
<iframe width="100%" height="90%" src="http://player.youku.com/embed/XMTM3MzY3ODU2MA==" frameborder="0" allowfullscreen="true" webkitallowfullscreen="true"  
id="ifr_youku" onLoad="iFrameHeight()"></iframe>
</div>
<!--
<embed
type="application/x-shockwave-flash"
src="http://player.youku.com/player.php/sid/${vid}/v.swf"
id="movie_player"
name="movie_player"
bgcolor="#FFFFFF"
quality="high"
allowfullscreen="true"
flashvars="isShowRelatedVideo=false&showAd=0&show_pre=1&show_next=1&isAutoPlay=true&isDebug=false&UserID=&winType=interior&playMovie=true&MMControl=false&MMout=false&RecordCode=1001,1002,1003,1004,1005,1006,2001,3001,3002,3003,3004,3005,3007,3008,9999"
pluginspage="http://www.macromedia.com/go/getflashplayer" width="480" height="400">
-->
</body>
</html>
