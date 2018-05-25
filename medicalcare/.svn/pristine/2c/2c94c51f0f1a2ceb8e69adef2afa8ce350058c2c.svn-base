<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {

        $('#mchtAddForm').form({
            url : '${path }/crm/mcht/add.do',
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
                    parent.$.messager.alert('提示', result.msg, 'warning');
                }
            }
        });
        
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="mchtAddForm" method="post" enctype="multipart/form-data">
            <table class="grid">
                <tr>
                    <td>商家号码</td>
                    <td><input name="mchtCode" type="text" placeholder="请输入商家号码" class="easyui-validatebox" data-options="required:true" value=""  validtype="length[0,32]"></td>
                    <td>商家名称</td>
                    <td><input name="mchtName" type="text" placeholder="请输入商家名称" class="easyui-validatebox" data-options="required:true" value="" validtype="length[0,128]"></td>
                </tr>
                <tr>
                  	<td>app_id</td>
                    <td colspan="3">
                        <input type="text" name="appId" class="easyui-validatebox" data-options="required:true"  style="width:100%" validtype="length[0,128]"/>
                    </td>
                </tr>
                <tr>
                    <td>secret_key</td>
                    <td colspan="3"><input type="text" name="secretKey" class="easyui-validatebox" data-options="required:true"  style="width:100%" validtype="length[0,128]"/></td>   
                </tr>
                <tr>
                    <td>优先级</td>
                    <td><input name="rank" value="0" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false" validtype="length[0,11]"></td>
                    <td>描述</td>
                    <td><textarea  name="remark" rows="" cols="" validtype="length[0,255]"></textarea></td>                 
                </tr>
            </table>
        </form>
    </div>
</div>