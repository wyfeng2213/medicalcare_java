<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {

		$('#sensitiveWordEditForm').form({
			url : '${path }/sys/sysSensitiveWord/edit.action',
			onSubmit : function() {
				progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();
				}
				return isValid;
			},
			success : function(result) {
				result = $.parseJSON(result);
				progressClose();
				if (result.code == 200) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.messager.alert('提示', result.message, 'info');
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('提示', result.message, 'warning');
				}
			}
		});

	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden; padding: 3px;">
		<form id="sensitiveWordEditForm" method="post"
			enctype="multipart/form-data">
			<table class="grid" style="width: 100%">
				<tr>
					<td><input type="hidden" name="id" class="easyui-validatebox"
						value="${sensitiveWord.id}" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>词语:</td>
					<td><textarea name="text" class="easyui-validatebox" data-options="required:true"  style="width:100%">${sensitiveWord.text}</textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 30%">等级:</td>
					<td style="width: 70%">
					<select name="level" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" data-options="required:true">
                            <option value=A ${sensitiveWord.level==A?"selected='selected'":""}>A</option>
                            <option value=B ${sensitiveWord.level==B?"selected='selected'":""}>B</option>
                    </select>
                    </td>
				</tr>
				<tr>
					<td style="width: 30%">敏感词类型:</td>
					<td style="width: 70%">
					<select name="type" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" data-options="required:true">
                            <option value=1 ${sensitiveWord.type==1?"selected='selected'":""}>内容过滤</option>
                            <option value=2 ${sensitiveWord.type==2?"selected='selected'":""}>通信诈骗</option>
                    </select>
                    </td>
				</tr>
				<tr>
					<td>创建时间:</td>
					<td><input type="datetime" disabled="disabled" name="" size="50"
						class="easyui-validatebox"
						value="<fmt:formatDate  value="${sensitiveWord.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
				</tr>
				<tr>
					<td>修改时间:</td>
					<td><input type="datetime" disabled="disabled" name="" size="50"
						class="easyui-validatebox"
						value="<fmt:formatDate  value="${sensitiveWord.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
				</tr>
			</table>
		</form>
	</div>
</div>