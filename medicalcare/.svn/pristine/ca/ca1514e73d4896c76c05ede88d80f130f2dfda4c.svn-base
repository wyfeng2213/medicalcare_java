<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		$('#scienceArticleEditForm').form({
			url : '${path }/sys/scienceArticle/edit.action',
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
				 if (result.code==200) {
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
		<form id="scienceArticleEditForm" method="post"
			enctype="multipart/form-data">
			<table class="grid">
				<tr>
					<td>文章标题:</td>
					<td><input size="50" name=title type="text"
						class="easyui-validatebox" value="${scienceArticle.title}"></td>
					<td><input size="50" name=id type="hidden"
						value="${scienceArticle.id}"></td>	
				</tr>
				<tr>
					<td>文章副标题:</td>
					<td><input size="50" name="subheading" type="text"
						class="easyui-validatebox" value="${scienceArticle.subheading}"></td>
				</tr>
				<tr>
					<td>文章来源:</td>
					<td><input size="50" name="source" type="text"
						class="easyui-validatebox" value="${scienceArticle.source}"></td>
				</tr>
				<tr>
					<td>文章链接:</td>
					<td><input size="50" name="articleUrl" type="text"
						class="easyui-validatebox" value="${scienceArticle.articleUrl}"></td>
				</tr>
				<tr>
					<td>封面图片:</td>
					<td><a href="${scienceArticle.imageUrl}" target="view_window">图片</a></td>
					<td><input size="50" type="hidden" name="imageUrl"
						value="${scienceArticle.imageUrl}" /></td>	
				</tr>
				<tr>
					<td>修改封面：</td>
					<td><input name="file" size="50" type="file"
						placeholder="请输入图片"></td>
				</tr>
				<tr>
					<td>所属栏目:</td>
					<td><input size="50" type="text" name="columnHeading"
						class="easyui-validatebox" value="${scienceColumn.columnHeading}" /></td>
					<td><input size="50" type="hidden" name="columnId"
						value="${scienceColumn.id}" /></td>	
				</tr>
				<tr>
					<th>修改所属栏目:</th>
					<td><select id="columnId2" name="columnId2"
						class="easyui-combobox"
						data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="">未选择</option>
							<c:forEach var="item" items="${scienceColumnList}"
								varStatus="status">
								<option value="${item.id}">${item.columnHeading}</option>
							</c:forEach>
					</select></td>
				</tr>
			</table>
		</form>
	</div>
</div>