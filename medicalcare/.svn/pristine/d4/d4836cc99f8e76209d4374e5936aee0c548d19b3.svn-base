<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	String base = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
%>
<c:set var="ctx" value="<%=basePath%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>上传文件</title>
<script type="text/javascript" src="${ctx }/skin/js/jquery-1.8.3.js"></script>
</head>
<body >
<div class="listcon">
 	<form action="${ctx }/pav/packageVersionPatient/add.action" method="post" enctype="multipart/form-data">
      <input name="file" type="file"/>
      <input type="submit" value="上传" />
    </form>
</div>

</body>
</html>


