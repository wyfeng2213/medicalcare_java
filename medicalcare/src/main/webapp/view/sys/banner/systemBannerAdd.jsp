<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {

        $('#systemBannerAddForm').form({
            url : '${path }/sys/systemBanner/add.action',
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
        <form id="systemBannerAddForm" method="post"
			enctype="multipart/form-data">
			<table class="grid" style="width: 100%">
				<tr>
					<td style="width: 30%">图片种类:</td>
					<td style="width: 70%">
					<select name="type" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" data-options="required:true">
                            <option value=1 >粤健康首页</option>
                            <option value=2 >健康优品</option>
                    </select>
				</tr>
				<tr>
					<td>标题:</td>
					<td><textarea name="title" rows="4" cols="4" class="easyui-validatebox" data-options="required:true"  style="width:100%" validtype="length[0,250]"></textarea>
					</td>
				</tr>
				<tr>
					<td>图片地址:</td>
					<td>
					<textarea name="imgSrc" rows="4" cols="4" class="easyui-validatebox" data-options="required:true"  style="width:100%" validtype="length[0,250]"></textarea>
					</td>
				</tr>
				<tr>
					<td>图片文件:</td>
					<td><input type="file" name="file"class="easyui-validatebox" value="" /></td>
				</tr>
				<tr>
					<td>内容地址:</td>
					<td>
					<textarea name="pageUrl" rows="4" cols="4" class="easyui-validatebox" data-options="required:true"  style="width:100%" validtype="length[0,512]"></textarea>
					</td>
				</tr>
			</table>
		</form>
    </div>
</div>