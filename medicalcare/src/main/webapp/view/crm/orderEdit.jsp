<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
  	
			        $('#orderEditForm').form({
			            url : '${path }/crm/order/edit.do',
			            onSubmit : function() {
			                progressLoad();
			                var isValid = $(this).form('validate');
			                if (!isValid) {
			                    progressClose();
			                }
			                return isValid;
			            },
			            success : function(result) {
			                progressClose();
			                result = $.parseJSON(result);
			                if (result.success) {
			                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
			                    parent.$.modalDialog.handler.dialog('close');
			                } else {
			                    parent.$.messager.alert('错误', result.msg, 'error');
			                }
			            }
			        });
         

    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="orderEditForm" method="post">
            <!-- <div class="light-info" style="overflow: hidden;padding: 3px;">
                <div>密码不修改请留空。</div>
            </div> -->
            <table class="grid">
                <tr>
                    <td>原因</td>
                    <td><input name="id" type="hidden"  value="${order.id}">
                    <input name="retreatReason" type="text" placeholder="请输入原因" class="easyui-validatebox" data-options="required:true" value="${order.retreatReason}"></td>
                </tr>
            </table>
        </form>
    </div>
</div>