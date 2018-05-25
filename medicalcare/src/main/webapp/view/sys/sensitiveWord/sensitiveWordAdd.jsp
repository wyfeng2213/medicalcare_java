<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {

        $('#sensitiveWordAddForm').form({
            url : '${path }/sys/sysSensitiveWord/add.action',
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
        <form id="sensitiveWordAddForm" method="post" enctype="multipart/form-data">
			<table class="grid" style="width: 100%">
				<tr>
					<td>词语:</td>
					<td><textarea name="text" class="easyui-validatebox" data-options="required:true"  style="width:100%"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 30%">等级:</td>
					<td style="width: 70%">
					<select name="level" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" data-options="required:true">
                            <option value=A >A</option>
                            <option value=B >B</option>
                    </select>
                    </td>
				</tr>
				<tr>
					<td style="width: 30%">敏感词类型:</td>
					<td style="width: 70%">
					<select name="type" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" data-options="required:true">
                            <option value=1 >内容过滤</option>
                            <option value=2 >通信诈骗</option>
                    </select>
                    </td>
				</tr>
			</table>
		</form>
    </div>
</div>