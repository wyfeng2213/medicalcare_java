<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {

        $('#userAddForm').form({
            url : '${path }/crm/user/add.do',
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
        <form id="userAddForm" method="post" enctype="multipart/form-data">
            <table class="grid" align="center">
                <tr hegith="30px">
                    <td>用户名</td>
                    <td><input name="username" type="text" placeholder="请输入登录名称" validType="length[1,128]" class="easyui-validatebox" data-options="required:true" value=""></td>
                </tr>
                <tr hegith="30px">
                    <td>昵称</td>
                    <td><input name="nickname" type="text" placeholder="请输入姓名" validType="length[1,128]" class="easyui-validatebox" data-options="required:true" value=""></td>
                
                </tr>
                <tr hegith="30px">
                    <td>电话</td>
                    <td>
                        <input type="text" name="mobile" class="easyui-numberbox" data-options="required:true" validType="length[1,11]" />
                    </td>
                </tr>
                <tr hegith="30px">
                    <td>邮箱</td>
                    <td><input type="text" name="email" class="easyui-validatebox" data-options="required:true" value="" validType="length[1,64]" /></td>
                
                </tr>
				<!--
                <tr>
                    <td>头像</td>
                    <td>
                    	<input  name="sourceFile" type="file" id="sourceFile" style="width:100%"/>
                    </td>
                </tr>
				-->
            </table>
        </form>
    </div>
</div>