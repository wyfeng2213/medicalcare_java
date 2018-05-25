<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {

        $('#doctorApkAddForm').form({
            url : '${path }/sys/pav/doctorApk/add.action',
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
        <form id="doctorApkAddForm" method="post" enctype="multipart/form-data">
            <table class="grid">
                <tr>
                    <td>apk文件</td>
                    <td><input name="file" type="file" placeholder="请输入APK" class="easyui-validatebox" data-options="required:true" value=""></td>
                </tr>
            </table>
        </form>
    </div>
</div>