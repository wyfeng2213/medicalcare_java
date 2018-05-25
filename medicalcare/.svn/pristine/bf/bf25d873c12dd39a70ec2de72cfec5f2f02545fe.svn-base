<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#mediaSubsetEditForm').form({
            url : '${path }/crm/subset/edit.do',
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
        <form id="mediaSubsetEditForm" method="post">
            <table class="grid">
            	<tr>
            		<td>视频ID</td>
                    <td colspan="3"><input id="mediaPlayId" name="mediaPlayId" type="text" style="width:100%;" placeholder="请输入视频ID" class="easyui-validatebox span2" data-options="required:true" value="${mediaSubset.mediaPlayId}"></td>
            	</tr>
            	<tr>
            		<td>视频名</td>
                    <td colspan="3"><input id="name" name="name" type="text" style="width:100%;" placeholder="请输入视频名" class="easyui-validatebox span2" data-options="required:true" value="${mediaSubset.name}"></td>
				</tr>   
				<tr>
                    <td>视频URL</td>
                    <td colspan="3"><input id="mediaPlayUrl" name="mediaPlayUrl" type="text" style="width:100%;" validType="length[1,1024]" placeholder="请输入视频URL" class="easyui-validatebox span2" data-options="required:false" value="${mediaSubset.mediaPlayUrl}"></td>
				</tr>
            	<tr>
                	<td>缩略图</td>
                	<td colspan="3">
                		<input type="text" id="thumbnail" name="thumbnail" style="width:100%;" placeholder="请输入视频缩略图地址" class="easyui-validatebox span2" value="${mediaSubset.thumbnail}">
                	</td>
            	</tr>
            	<tr>
            		<td>视频说明</td>
            		<td colspan="3"><textarea  id="detail" name="detail" rows="4" cols="" maxlength="1024" style="width: 100%;"  placeholder="请输入视频说明">${mediaSubset.detail}</textarea></td>
            	</tr>
                <tr>
                	<td>发布日期</td>
                    <td><input id="issueDate" name="issueDate" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" value="<fmt:formatDate  value="${mediaSubset.issueDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
                    <td>时长</td>
                    <td><input id="duration" name="duration" type="text" placeholder="请输入视频时长" validType="length[1,11]" class="easyui-validatebox span2" data-options="required:true" value="${mediaSubset.duration}"></td>
                </tr>
                <tr>
                    <td>优先级</td>
                    <td><input name="mediaIndex" value="${mediaSubset.mediaIndex }" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false"></td>
                    <td>付费类型</td>
                    <td >
                        <select id="chargeType" name="chargeType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
				                <option value="0" <c:if test="${mediaSubset.chargeType=='0'}">selected='selected'</c:if>>免费</option>
				                <option value="1" <c:if test="${mediaSubset.chargeType=='1'}">selected='selected'</c:if>>付费</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3"><textarea name="remark" rows="4" style="width: 100%;" cols="" maxlength="255">${mediaSubset.remark}</textarea></td>
                    <td><input name="id" type="hidden"  value="${mediaSubset.id}"></td>
                </tr>
            </table>
        </form>
    </div>
</div>