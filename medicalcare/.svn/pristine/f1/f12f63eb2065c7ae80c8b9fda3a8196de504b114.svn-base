<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Comet4J Hello World</title>
<script type="text/javascript" src="../resource/js/comet4j.js"></script>
<script type="text/javascript">
function init(){
	var kbDom = document.getElementById('kb');
	JS.Engine.on({
		channel_message : function(kb){//侦听一个channel
			kbDom.innerHTML = kb;
		}
	});

	JS.Engine.start('../../pushMessage');

	JS.Engine.on('start',function(cId,channelList,engine){
		alert('连接已建立，连接ID为：' + cId);
	});
}
</script>
</head>
<body onload="init()">
        剩余内存：<span id="kb">...</span>KB
</body>
</html>