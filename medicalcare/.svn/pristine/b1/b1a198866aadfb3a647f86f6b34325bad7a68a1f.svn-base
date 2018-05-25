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
		<form id="inquiryLogQueryForm" method="post"
			enctype="multipart/form-data">
			<table class="grid">
				<tr>
					<td>发送用户loginid:</td>
					<td><input size="50" name=title type="text" class="easyui-validatebox" 
						value="${inquiryLog.fromUserLoginId}"></td>
				</tr>
				<tr>
					<td>发送用户名字:</td>
					<td><input size="50" name=title type="text" class="easyui-validatebox" 
						value="${inquiryLog.fromUserName}"></td>
				</tr>
				<tr>
					<td>发送用户手机号:</td>
					<td><input size="50" name="subheading" type="text" class="easyui-validatebox" 
						value="${inquiryLog.fromUserPhone}"></td>
				</tr>
				<tr>
					<td>发送用户类型:</td>
					<c:choose>
						<c:when test="${inquiryLog.fromUserType==1}">
							<td><input size="50" name="source" type="text" class="easyui-validatebox" value="医生"></td>
						</c:when>
						<c:when test="${inquiryLog.fromUserType==2}">
							<td><input size="50" name="source" type="text" class="easyui-validatebox" value="患者"></td>
						</c:when>
						<c:otherwise>
							<td><input size="50" name="source" type="text" class="easyui-validatebox" value="秘书"></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td>接收用户loginid:</td>
					<td><input size="50" name=title type="text" class="easyui-validatebox" 
						value="${inquiryLog.toUserLoginId}"></td>
				</tr>
				<tr>
					<td>接收用户名字:</td>
					<td><input size="50" name=title type="text" class="easyui-validatebox" 
						value="${inquiryLog.toUserName}"></td>
				</tr>
				<tr>
					<td>接收用户手机号:</td>
					<td><input size="50" name="subheading" type="text" class="easyui-validatebox" 
						value="${inquiryLog.toUserPhone}"></td>
				</tr>
				<tr>
					<td>接收用户类型:</td>
					<c:choose>
						<c:when test="${inquiryLog.toUserType==1}">
							<td><input size="50" name="source" type="text" class="easyui-validatebox" value="医生"></td>
						</c:when>
						<c:when test="${inquiryLog.toUserType==2}">
							<td><input size="50" name="source" type="text" class="easyui-validatebox" value="患者"></td>
						</c:when>
						<c:when test="${inquiryLog.toUserType==3}">
							<td><input size="50" name="source" type="text" class="easyui-validatebox" value="秘书"></td>
						</c:when>
						<c:otherwise>
							<td><input size="50" name="source" type="text" class="easyui-validatebox" value="聊天室"></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td>消息:</td>
					<td><input size="50" name="subheading" type="text" class="easyui-validatebox" 
						value="${inquiryLog.messagecontent}"></td>
				</tr>
				<tr>
					<td>消息类型:</td>
					<c:choose>
						<c:when test="${inquiryLog.messageType==1}">
							<td><input size="50" name="source" type="text" class="easyui-validatebox" value="文字"></td>
						</c:when>
						<c:when test="${inquiryLog.messageType==2}">
							<td><input size="50" name="source" type="text" class="easyui-validatebox" value="图片"></td>
						</c:when>
						<c:when test="${inquiryLog.messageType==3}">
							<td><input size="50" name="source" type="text" class="easyui-validatebox" value="语音"></td>
						</c:when>
						<c:when test="${inquiryLog.messageType==4}">
							<td><input size="50" name="source" type="text" class="easyui-validatebox" value="视频"></td>
						</c:when>
						<c:when test="${inquiryLog.messageType==5}">
							<td><input size="50" name="source" type="text" class="easyui-validatebox" value="问诊表"></td>
						</c:when>
						<c:when test="${inquiryLog.messageType==6}">
							<td><input size="50" name="source" type="text" class="easyui-validatebox" value="随访计划"></td>
						</c:when>
						<c:otherwise>
							<td><input size="50" name="source" type="text" class="easyui-validatebox" value="其他"></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td>发送时间:</td>
					<td><input type="datetime" size="50" class="easyui-validatebox"
						value="<fmt:formatDate  value="${inquiryLog.sendtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
				</tr>
				<tr>
					<td>创建时间:</td>
					<td><input type="datetime" size="50" 
						class="easyui-validatebox"
						value="<fmt:formatDate  value="${inquiryLog.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
				</tr>
				<tr>
					<td>修改时间:</td>
					<td><input type="datetime" size="50" 
						class="easyui-validatebox"
						value="<fmt:formatDate  value="${inquiryLog.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
				</tr>
			</table>
		</form>
	</div>
</div>