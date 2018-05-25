<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat,java.util.Date" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/basejs.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	String base = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
%>
<c:set var="ctx" value="<%=path%>" />
<%
SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
String date= format.format(new Date());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	body{color:black;
	      font-size:62.5%;
          width:auto; 
	      max-width: 1920px;
		  margin: 0 auto;
		 }
		 
	.btn_on{background:black; color:white;}
	.btn_off{background:white; color:black;}
	
</style>
<script type="text/javascript" src="${ctx }/skin/js/jquery-1.10.2.min.js">
</script>

<script type="text/javascript">
	$(function(){
		var showType = $('.btn_on').val();
		var startTime = $('#startTime').val();
		var endTime = $('#endTime').val();
		$.ajax({
			type: "post",
            url: "${ctx}/chart/getActiveUser.action",
            data: {showType:showType,startTime:startTime,endTime:endTime},
            async:false,
            dataType: "json",
            success: function(data){
	             var base = "<%=path%>"+data.data;
	             $('#activeUserBar').append('<div id="activeImg"><img src="'+base+'" width=700 height=400 border=0 /></div>');
            },
            error:function(data){alert("error");
        }});
		$.ajax({
			type: "post",
            url: "${ctx}/chart/getAddUser.action",
            data: {showType:showType,startTime:startTime,endTime:endTime},
            async:false,
            dataType: "json",
            success: function(data){
	             var base = "<%=path%>"+data.data;
	             $('#addUserBar').append('<div id="addImg"><img src="'+base+'" width=700 height=400 border=0 /></div>');
            },
            error:function(data){alert("error");
        }});

		//平台选择点击事件
		$('#searchBtn').click(function(){
			var showType = $('.btn_on').val();
			var startTime = $('#startTime').val();
			var endTime = $('#endTime').val();
			$.ajax({type: "post",
				url: "${ctx}/chart/getActiveUser.action",
	            data: {showType:showType,startTime:startTime,endTime:endTime},
	            async:false,
	            dataType: "json",
	            success: function(data){
		             var base = "<%=path%>"+data.data;
		             var activeImg = $('#activeImg');
		             if(activeImg){
						$('#activeImg').html('<img src="'+base+'" width=700 height=400 border=0 /></div>');
			         }else{
			        	 $('#activeUserBar').append('<div id="activeImg"><img src="'+base+'" width=700 height=400 border=0 /></div>');
				     }
	            },
	            error:function(data){alert("error");},
			});
			$.ajax({type: "post",
				url: "${ctx}/chart/getAddUser.action",
				data: {showType:showType,startTime:startTime,endTime:endTime},
	            async:false,
	            dataType: "json",
	            success: function(data){
		             var base = "<%=path%>"+data.data;
		             var addImg = $('#addImg');
		             if(addImg){
						$('#addImg').html('<img src="'+base+'" width=700 height=400 border=0 /></div>');
			         }else{
			        	 $('#addUserBar').append('<div id="addImg"><img src="'+base+'" width=700 height=400 border=0 /></div>');
				     }
	            },
	            error:function(data){alert("error");},
			});
		});
		
		//展示方式选择事件——天
		$('#dayBtn').click(function(){
			$('#dayBtn').removeClass("btn_off").addClass("btn_on");
			$('#weekBtn').removeClass("btn_on").addClass("btn_off");
			$('#monthBtn').removeClass("btn_on").addClass("btn_off");
		});
		//展示方式选择事件——周
		$('#weekBtn').click(function(){
			$('#dayBtn').removeClass("btn_on").addClass("btn_off");
			$('#weekBtn').removeClass("btn_off").addClass("btn_on");
			$('#monthBtn').removeClass("btn_on").addClass("btn_off");
		});
		//展示方式选择事件——月
		$('#monthBtn').click(function(){
			$('#dayBtn').removeClass("btn_on").addClass("btn_off");
			$('#weekBtn').removeClass("btn_on").addClass("btn_off");
			$('#monthBtn').removeClass("btn_off").addClass("btn_on");
		});
			
	});
</script>

</head>

<body>
<div id="platBar0">
	<span>运营>运营统计</span>
</div>
<hr/>
<div id="platBar0">
	<span>展示方式：</span>
	<span><input type="button" value="按天" id="dayBtn" class="btn_on"/></span>
	<span><input type="button" value="按周" id="weekBtn" class="btn_off"/></span>
	<span><input type="button" value="按月" id="monthBtn" class="btn_off"/></span>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<span>时间范围：</span>
	<span><input type="text" id="startTime" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="readonly" value="<%=date%>" /></span>
	<span>~</span>
	<span><input type="text" id="endTime" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="readonly" value="<%=date%>" /></span>
	<span><input type="button" value="查询" id="searchBtn"/></span>
</div>
<hr/>
<div id="activeUserBar">
</div>
<hr/>
<div id="addUserBar">
</div>
</body>
</html>