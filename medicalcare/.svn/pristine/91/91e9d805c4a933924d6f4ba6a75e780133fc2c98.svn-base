<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {

		$('#scienceArticleAddForm').form({
			url : '${path }/sys/scienceArticle/add.action',
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
		<form id="scienceArticleAddForm" method="post"
			enctype="multipart/form-data">
			<table class="grid">
				<tr>
					<td>文章标题:</td>
					<td><input type="text" size="50" name="title"
						class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>文章副标题:</td>
					<td><input name="subheading" size="50" type="text"
						class="easyui-validatebox" data-options="required:true"></td>
				</tr>
				<tr>
					<td>文章来源：</td>
					<td><input name="source" size="50" type="text"
						class="easyui-validatebox" data-options="required:true"></td>
				</tr>
				<tr>
					<td>文章链接：</td>
					<td><input name="articleUrl" size="50" type="text"
						class="easyui-validatebox" data-options="required:true"></td>
				</tr>
				<tr>
					<th>所属栏目:</th>
					<td><select id="columnId" size="50" name="columnId"
						class="easyui-combobox">
							<c:forEach var="item" items="${scienceColumnList}"
								varStatus="status">
								<option value="${item.id}">${item.columnHeading}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>封面链接：</td>
					<td><input name="file" size="50" type="file"
						placeholder="请输入图片" class="easyui-validatebox" ></td>
				</tr>

			</table>
		</form>
	</div>
</div>