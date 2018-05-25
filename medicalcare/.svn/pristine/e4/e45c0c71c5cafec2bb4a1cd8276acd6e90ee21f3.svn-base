<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		$('#healthStepEditForm').form({
			url : '${path }/sys/healthStep/edit.action',
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
		<form id="healthStepEditForm" method="post"
			enctype="multipart/form-data">
			<table class="grid">
				<tr>
					<td>姓名:</td>
					<td><input type="text" name="name" disabled="false"
						class="easyui-validatebox" value="${healthCoinsLog.userName}" /></td>
				</tr>
				<tr>
					<td>手机号:</td>
					<td><input name="phone" type="text" disabled="false"
						class="easyui-validatebox" value="${healthCoinsLog.userPhone}"></td>
				</tr>
				<tr>
					<td>上一次健康币:</td>
					<td><input name="lcoins" type="text"
						class="easyui-validatebox" value="${healthCoinsLog.lcoins}"></td>
				</tr>
				<tr>
					<td>本次健康币:</td>
					<td><input name="mcoins" type="text"
						class="easyui-validatebox" value="${healthCoinsLog.mcoins}"></td>
				</tr>
				<tr>
					<td>差价:</td>
					<td><input name="gcoins" type="text"
						class="easyui-validatebox" value="${healthCoinsLog.gcoins}"></td>
				</tr>
				<tr>
					<td>收支明细:</td>
					<td><input name="detail" type="text"
						class="easyui-validatebox" value="${healthCoinsLog.detail}"></td>
				</tr>
				<tr>
					<td>收支类型:</td>
					<c:choose>
						<c:when test="${healthCoinsLog.type==1}">
							<td><select  name="type"> <option value = "1"  selected ="selected">收入</option>
								<option value = "2">支出</option> </select></td>
						</c:when>
						<c:otherwise>
							<td><select name="type"> <option value = "1" >收入</option>
								<option value = "2" selected ="selected">支出</option> </select></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td><input name="id" type="hidden" value="${healthCoinsLog.id}">
				</tr>


			</table>
		</form>
	</div>
</div>