<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {

        $('#systemMessageRemindingAddForm').form({
            url : '${path }/sys/systemMessageReminding/add.action',
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
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('提示', result.message, 'warning');
                }
            }
        });
        
    });
</script>
<div style="padding: 3px;" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="systemMessageRemindingAddForm" method="post"
			enctype="multipart/form-data">
			<table class="grid" style="width: 100%">
				<tr>
					<td>姓名</td>
					<td><input type="text" name="patientName" class="easyui-validatebox"
						value="" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>手机号</td>
					<td><input name="patientPhone" type="text" class="easyui-validatebox"
						value="" data-options="required:true"></td>
				</tr>
				<tr>
					<td>消息名称</td>
					<td><input name="messageName" type="text"
						class="easyui-validatebox" value="" data-options="required:true"></td>
				</tr>
				<tr>
					<td>消息内容</td>
					<td>
						<textarea name="messageContent" class="easyui-validatebox" cols="28" rows="6" data-options="required:true"></textarea>
					</td>
				</tr>
				<tr>
					<td>消息链接</td>
					<td><input name="messageLink" type="text"
						class="easyui-validatebox" value="" data-options="required:true"></td>
				</tr>
				<tr>
					<td>按钮名称</td>
					<td><input type="text" name="messageButton"
						class="easyui-validatebox" value="" data-options="required:true"/></td>
				</tr>
				<tr>
					<td>图标链接</td>
					<td><input name="file" type="file" placeholder="请输入图片"
						class="easyui-validatebox" value=""></td>
				</tr>
			</table>
		</form>
    </div>
</div>