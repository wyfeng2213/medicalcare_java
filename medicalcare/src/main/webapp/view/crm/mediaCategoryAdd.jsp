<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#mediaCategoryAddForm').form({
            url : '${path }/crm/mediaCategory/add.do',
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
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
        <form id="mediaCategoryAddForm" method="post"  enctype="multipart/form-data">
            <table class="grid">
                <tr>
                    <td>频道名称</td>
                    <td><input name="categoryName" type="text" placeholder="请输入频道名称" validType="length[1,128]" class="easyui-validatebox span2" data-options="required:true" value=""></td>
                </tr>
                <tr>
                    <td>优先级</td>
                    <td><input name="rank" value="0" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false"></td>
                </tr>
                <tr>
                	<td>视频排列样式</td>
                    <td >
                        <select id="styleType" name="styleType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                             <option value="0">横排</option>
                             <option value="1">竖排</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>频道图片</td>
                    <td><input  name="sourceFile" type="file" id="sourceFile" /></td>
                </tr>
                <tr>
                    <td>频道图标</td>
                    <td><input  name="sourceIcon" type="file" id="sourceIcon" /></td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3"><textarea name="remark" maxlength="255" rows="" cols="" ></textarea></td>
                </tr>
            </table>
        </form>
    </div>
</div>