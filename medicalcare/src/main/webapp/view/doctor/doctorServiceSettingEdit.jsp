<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		$('#doctorServiceSettingEditForm').form({
			url : '${path }/sys/doctorServiceSetting/edit.action',
			onSubmit : function() {
				progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();
				}
				return isValid;
			},
			success : function(result) {
				//alert('123');
				progressClose();
				result = $.parseJSON(result);
				if (result.code == 200) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.message, 'error');
				}
			}
		});

	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden; padding: 3px;">
		<form id="doctorServiceSettingEditForm" method="post"
			enctype="multipart/form-data">
			<table class="grid">
				<tr>
					<td>姓名:</td>
					<td><input type="text" name="name" disabled="false"
						class="easyui-validatebox" value="${doctorRecord.name}" /></td>
				</tr>
				<tr>
					<td>手机号:</td>
					<td><input name="phone" type="text" disabled="false"
						class="easyui-validatebox" value="${doctorRecord.phone}"></td>
				</tr>
				<tr>
					<td>是否视频咨询:</td>
					<c:choose>
						<c:when test="${doctorRecord.isVideoConsultation==1}">
							<td><select name="isVideoConsultation"> <option value = "1"  selected ="selected">开启</option>
								<option value = "0">关闭</option> </select></td>
						</c:when>
						<c:otherwise>
							<td><select name="isVideoConsultation"> <option value = "1" >开启</option>
								<option value = "0" selected ="selected">关闭</option> </select></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td>是否图文咨询:</td>
					<c:choose>
						<c:when test="${doctorRecord.graphicConsulting==1}">
							<td><select name="graphicConsulting"> <option value = "1"  selected ="selected">开启</option>
								<option value = "0">关闭</option> </select></td>
						</c:when>
						<c:otherwise>
							<td><select  name="graphicConsulting"> <option value = "1" >开启</option>
								<option value = "0" selected ="selected">关闭</option> </select></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td>是否快速咨询:</td>
					<c:choose>
						<c:when test="${doctorRecord.isQuickConsulting==1}">
							<td><select  name="isQuickConsulting"> <option value = "1"  selected ="selected">开启</option>
								<option value = "0">关闭</option> </select></td>
						</c:when>
						<c:otherwise>
							<td><select name="isQuickConsulting"> <option value = "1" >开启</option>
								<option value = "0" selected ="selected">关闭</option> </select></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td><input name="id" type="hidden" value="${doctorRecord.id}">
				</tr>


			</table>
		</form>
	</div>
</div>