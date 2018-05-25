<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
    	$("#downUrlText").hide();
        $('#versionAddForm').form({
            url : '${path }/crm/version/add.do',
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
        
        $(document).ready(function () {
        	$("#appType").combobox({
        		onChange: function (n,o) {
       				if(n==0){        				
        				$("#uploadfile").show();
        				$("#downUrlText").hide();
        			}else if(n==1){
        				$("#downUrlText").show();
        				$("#uploadfile").hide();
        			}       			      			
        		}
        	});

        });
    });
    
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
        <form id="versionAddForm" method="post" enctype="multipart/form-data">
            <table class="grid">
                <tr>
                    <td>标题</td>
                    <td><input name="title" type="text" placeholder="请输入版本标题" validType="length[1,255]" style="width: 100%;" class="easyui-validatebox span2" data-options="required:true" value=""></td>
                </tr>
                <tr>
                    <td>版本号</td>
                    <td><input name="version" type="text" placeholder="请输入版本号,如1.1.1.0" validType="length[1,32]" style="width: 100%;" class="easyui-validatebox span2" data-options="required:true" value=""></td>
                </tr>
                <tr>
                    <td>更新内容</td>
                    <td colspan="3"><textarea name="content" rows="3" cols="" style="width: 100%;" maxlength="1024"></textarea></td>
				</tr>                
                <tr>
                	<td>是否强制版</td>
                    <td >
                        <select id="isforce" name="isforce" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                    </td>
                </tr>
                <tr>
                	<td>终端类型</td>
                    <td >
                        <select id="appType" name="appType" class="easyui-combobox"  data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="0">android</option>
                            <option value="1">ios</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>下载地址</td>
                    <td>
                    	<input id="downUrlText" name="downUrl" type="text" placeholder="请输入下载地址" style="width: 100%;"  data-options="required:true" validtype="url" value="">
                    	<input id="uploadfile" name="sourceFile" type="file" style="width: 100%;" data-options="prompt:'请选择一个文件'">
                    </td>
				</tr>                
            </table>
        </form>
    </div>
</div>