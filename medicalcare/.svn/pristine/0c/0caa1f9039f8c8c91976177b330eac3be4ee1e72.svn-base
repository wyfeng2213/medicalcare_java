<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {

        $('#favorMediaAddForm').form({
            url : '${path }/crm/favorMedia/add.do',
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
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
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
        <form id="favorMediaAddForm" method="post" enctype="multipart/form-data">
            <table class="grid">
                <tr>
                    <td>用户</td>
                    <td><input name="accountid" type="text" placeholder="请输入用户ID" class="easyui-validatebox" data-options="required:true" value="" validType="length[0,32]" invalidMessage="用户ID不能超过32个字符！" ></td>
                    <td>商家ID</td>
                    <td><input name="mchtId" type="text" placeholder="请输入商家ID" class="easyui-numberbox" data-options="required:true" value=""></td>
                </tr>
                 <tr>
                    <td>频道ID</td>
                    <td><input name="categoryId" type="text" placeholder="请输入频道ID" class="easyui-numberbox" data-options="required:true" value="0"></td>
                    <td>视频ID</td>
                    <td><input name="mediaId" type="text" placeholder="请输入视频ID" class="easyui-numberbox" data-options="required:true" value="0"></td>
                </tr>
                <tr>
                    <td>优先级</td>
                    <td><input name="rank" value="1" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false"></td>
                    <td>收藏时间</td>
                    <td><input id="favorTime" name="favorTime" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" /></td>
                </tr>
                <tr>
                    <td>描述</td>
                    <td colspan="3"><input type="text" name="remark" class="easyui-validatebox" style="width: 345px; height: 29px;" validType="length[0,255]" invalidMessage="描述不能超过255个字符！" /></td>
                </tr>
            </table>
        </form>
    </div>
</div>