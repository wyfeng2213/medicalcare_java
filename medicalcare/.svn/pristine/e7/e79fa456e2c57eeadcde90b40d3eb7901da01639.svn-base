<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {

		$('#scienceColumnAddForm').form({
			url : '${path }/sys/scienceColumn/add.action',
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
		<form id="scienceColumnAddForm" method="post">
			 <table class="grid">
				<tr>
					<td>标题:</td>
					<td><input type="text" name="columnHeading" 
						class="easyui-validatebox" value="" /></td>
				</tr>
				<tr>
					<td>次序号（从左往右）:</td>
					<td><input type="text" name="sequence" 
						class="easyui-validatebox" value="" /></td>
				</tr>
				
				<tr>
                	
                </tr>
				
			</table>
		</form>
	</div>
</div>