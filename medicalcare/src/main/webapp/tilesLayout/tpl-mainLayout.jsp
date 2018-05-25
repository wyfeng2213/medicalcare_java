<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${pageTitle}</title>
</head>
<body>
<table border="1" style="width: 100%">
  <tbody>
  	<tr>
  		<td colspan="2" height="80px"><tiles:insertAttribute name="header" /></td>
  	</tr>
  	<tr>
  		<td style="min-height:700px;width:200px;positon:relative"><tiles:insertAttribute name="leftNavigation" /></td>
  		<td><tiles:insertAttribute name="content" /></td>
  	</tr>
  	<tr >
  		<td style="position:absolute; bottom:0px;"  colspan="2" width="100%"><tiles:insertAttribute name="footer" /></td>
  	</tr>
</table>
</body>
</html>