<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {

		$('#editPageForm').form({
			url : '${path }/sys/systemHealthyProducts/edit.action',
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
		<form id="editPageForm" method="post"
			enctype="multipart/form-data">
			<table class="grid" style="width: 100%">
				<tr>
					<td><input type="hidden" name="id" class="easyui-validatebox"
						value="${healthyProducts.id}" data-options="required:true" /></td>
				</tr>
				<tr>
					<td style="width: 30%">图片种类:</td>
					<td style="width: 70%">
					<select name="type" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" data-options="required:true">
                            <option value=1 ${healthyProducts.type==1?"selected='selected'":""} ">健康自测</option>
                            <option value=2 ${healthyProducts.type==2?"selected='selected'":""}>体验套餐</option>
                    </select>
				</tr>
				<tr>
					<td>标题:</td>
					<td><textarea name="title" class="easyui-validatebox" data-options="required:true"  style="width:100%" validtype="length[0,250]">${healthyProducts.title}</textarea>
					</td>
				</tr>
				<tr>
					<td>图片地址:</td>
					<td>
					<textarea name="imgSrc" rows="4" cols="4" class="easyui-validatebox" data-options="required:true"  style="width:100%" validtype="length[0,250]">${healthyProducts.imgSrc}</textarea>
					</td>
				</tr>
				<tr>
					<td>图片文件:</td>
					<td><input type="file" name="file"class="easyui-validatebox" value="" /></td>
				</tr>
				<tr>
					<td>内容地址:</td>
					<td>
					<textarea name="pageUrl" rows="4" cols="4" class="easyui-validatebox" data-options="required:true"  style="width:100%" validtype="length[0,250]" >${healthyProducts.pageUrl}</textarea>
					</td>
				</tr>
				<tr>
					<td>简介:</td>
					<td><textarea name="introduction" class="easyui-validatebox" style="width:100%" validtype="length[0,250]">${healthyProducts.introduction}</textarea>
					</td>
				</tr>
				<tr>
					<td>价格:</td>
					<td><textarea name="price" class="easyui-validatebox" style="width:100%" validtype="length[0,250]">${healthyProducts.price}</textarea>
					</td>
				</tr>
				<tr>
					<td>创建时间:</td>
					<td><input type="datetime" disabled="disabled" name="" size="50"
						class="easyui-validatebox"
						value="<fmt:formatDate  value="${healthyProducts.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
				</tr>
				<tr>
					<td>修改时间:</td>
					<td><input type="datetime" disabled="disabled" name="" size="50"
						class="easyui-validatebox"
						value="<fmt:formatDate  value="${healthyProducts.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
				</tr>
			</table>
		</form>
	</div>
</div>