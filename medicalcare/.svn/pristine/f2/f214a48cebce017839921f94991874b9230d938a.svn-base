<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(function() {

    $('#hospitalAddForm').form({
        url : '${path }/sys/hospital/add.action',
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
            if (result.code==200) {
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
		<form id="hospitalAddForm" method="post"
			enctype="multipart/form-data">
			<table class="grid">
				<tr>
					<td>机构名称:</td>
					<td><input type="text" name="name"
						class="easyui-validatebox" value="" data-options="required:true"/></td>
				</tr>
				<tr>
					<td>机构电话:</td>
					<td><input name="hospitalPhone" type="text"
						class="easyui-validatebox" value="" data-options="required:true"/>></td>

				</tr>
				<tr>
					<td>机构地址:</td>
					<td><input type="text" cols="28" name="hospitalAddr"
						class="easyui-validatebox" data-options="required:true"
						value="" /></td>
				</tr>

			<!-- 	<tr>
					<td>创建时间:</td>
					<td><input type="datetime" name="createtime"
						class="easyui-validatebox" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
						value="" /></td>
				</tr> -->
				<tr>
					<td>省份:</td>
					<td><input name="province" type="text"
						class="easyui-validatebox" value="" data-options="required:true"></td>
				</tr>
				<tr>
					<td>市区:</td>
					<td><input type="text" name="cityName"
						class="easyui-validatebox" value="" data-options="required:true"/></td>
				</tr>

				<tr>
					<td>区县:</td>
					<td><input name="area" type="text"
						class="easyui-validatebox" value="" data-options="required:true"></td>
				</tr>
				<tr>
					<td>医院图片:</td>
					<td><input name="file" type="file" placeholder="请输入图片" class="easyui-validatebox" value=""></td>
				</tr>
				<tr>
					<td>简介:</td>
					<td>
						<%--  <input type="textarea" name="status"  value="${doctorRecord.introduction}" validtype="length[0,255]"/> --%>
						<textarea name="introduction" cols="28" rows="6"></textarea>
					</td>
				</tr>


			</table>
		</form>
	</div>
</div>