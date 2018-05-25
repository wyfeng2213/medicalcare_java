<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {

	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden; padding: 3px;">
		<form id="mediaLogQueryForm" method="post"
			enctype="multipart/form-data">
			<table class="grid">
				<tr>
					<td>名字:</td>
					<td><input size="50" name=title type="text" disabled="false"
						class="easyui-validatebox" value="${mediaLog.userName}"></td>
				</tr>
				<tr>
					<td>登录ID:</td>
					<td><input size="50" name="subheading" type="text"
						disabled="false" class="easyui-validatebox"
						value="${mediaLog.userLoginid}"></td>
				</tr>
				<tr>
					<td>电话:</td>
					<td><input size="50" name="source" type="text"
						disabled="false" class="easyui-validatebox"
						value="${mediaLog.userPhone}"></td>
				</tr>
				<tr>
					<td>大小(字节B):</td>
					<td><input size="50" name="source" type="text"
						disabled="false" class="easyui-validatebox"
						value="${mediaLog.size}"></td>
				</tr>
				<tr>
					<td>后缀:</td>
					<td><input size="50" name="source" type="text"
						disabled="false" class="easyui-validatebox"
						value="${mediaLog.suffix}"></td>
				</tr>
				<tr>
					<td>路径:</td>
					<td><input size="50" name="source" type="text"
						disabled="false" class="easyui-validatebox"
						value="${mediaLog.path}"></td>
				</tr>
				<tr>
					<td>来自服务器:</td>
					<td><input size="50" name="source" type="text"
						disabled="false" class="easyui-validatebox"
						value="${mediaLog.fromServer}"></td>
				</tr>
				<tr>
					<td>存储服务器:</td>
					<td><input size="50" name="source" type="text"
						disabled="false" class="easyui-validatebox"
						value="${mediaLog.server}"></td>
				</tr>
				<tr>
					<td>外网ip:</td>
					<c:choose>
						<c:when
							test="${mediaLog.outerUrl!=null&& mediaLog.outerUrl!=''}">  
							<td><a href="${mediaLog.outerUrl}" target="view_window">链接</a></td>
						</c:when>
						<c:otherwise> 
    						<td><a href="" target="view_window"></a></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td>内网ip:</td>
					<c:choose>
						<c:when
							test="${mediaLog.innerUrl!=null&& mediaLog.innerUrl!=''}">  
							<td><a href="${mediaLog.innerUrl}" target="view_window">链接</a></td>
						</c:when>
						<c:otherwise> 
    						<td><a href="" target="view_window"></a></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td>创建时间:</td>
					<td><input type="datetime" size="50" disabled="false"
						class="easyui-validatebox"
						value="<fmt:formatDate  value="${mediaLog.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
				</tr>
				<tr>
					<td>修改时间:</td>
					<td><input type="datetime" size="50" disabled="false"
						class="easyui-validatebox"
						value="<fmt:formatDate  value="${mediaLog.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
				</tr>
			</table>
		</form>
	</div>
</div>