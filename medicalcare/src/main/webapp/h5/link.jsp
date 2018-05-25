<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%-- <%@ include file="/common/include.jsp"%> --%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	String base = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
%>
<c:set var="ctx" value="<%=path%>" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
	<Script language="javascript">
    var referenceLoginId = getQueryString('referenceLoginId');
	//var doctorType = getQueryString('doctorType'); 
	/* var doctorsLoginId = mui.getQueryString('doctorsLoginId');
	var doctorType = mui.getQueryString('doctorType'); */
	jumpPage(referenceLoginId);
	
	function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}
	
	function jumpPage(referenceLoginId){
		//alert(doctorsLoginId);
		window.location.href = "${ctx}/h5/login.html?referenceLoginId="+referenceLoginId;
	}
	</Script>

	<script src="js/mui.min.js"></script>
	<script src="js/zepto.min.js"></script>
	<script src="js/config.js"></script>
	<script src="js/mui.picker.min.js"></script>
	<script src="js/base.js"></script>
	<script src="lib/makeThumb/binaryajax.js"></script>
	<script src="lib/makeThumb/exif.js"></script>
	<script src="lib/makeThumb/megapix-image.js"></script>
	<script src="lib/makeThumb/jquery.make-thumb.js"></script>
</body>
</html>