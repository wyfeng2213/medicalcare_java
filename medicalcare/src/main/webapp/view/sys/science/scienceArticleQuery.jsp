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
		<form id="scienceArticleQueryForm" method="post"
			enctype="multipart/form-data">
			<table class="grid">
				<tr>
					<td>文章标题:</td>
					<td><input size="50" name=title type="text"
						disabled="false" class="easyui-validatebox"
						value="${scienceArticle.title}"></td>
				</tr>
				<tr>
					<td>文章副标题:</td>
					<td><input size="50" name="subheading" type="text"
						disabled="false" class="easyui-validatebox"
						value="${scienceArticle.subheading}"></td>
				</tr>
				<tr>
					<td>文章来源:</td>
					<td><input size="50" name="source" type="text"
						disabled="false" class="easyui-validatebox"
						value="${scienceArticle.source}"></td>
				</tr>
				<tr>
					<td>文章链接:</td>
					<td><a href="${scienceArticle.articleUrl}"
						target="view_window">链接</a></td>
				</tr>
				<tr>
					<td>封面链接:</td>
					<td><a href="${scienceArticle.imageUrl}" target="view_window">图片</a></td>
				</tr>
				<tr>
					<td>所属栏目:</td>
					<td><input size="50" type="text"
						name="columnHeading" disabled="false" class="easyui-validatebox"
						value="${scienceColumn.columnHeading}" /></td>
				</tr>
				<tr>
					<td>创建时间:</td>
					<td><input type="datetime" size="50" disabled="false"
						class="easyui-validatebox"
						value="<fmt:formatDate  value="${scienceArticle.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
				</tr>
				<tr>
					<td>修改时间:</td>
					<td><input type="datetime" size="50" disabled="false"
						class="easyui-validatebox"
						value="<fmt:formatDate  value="${scienceArticle.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
				</tr>
			</table>
		</form>
	</div>
</div>