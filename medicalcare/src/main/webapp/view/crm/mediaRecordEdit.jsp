<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
    	
        $('#mediaRecordEditForm').form({
            url : '${path }/crm/mediaRecord/edit.do',
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
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="mediaRecordEditForm" method="post">
             <table class="grid">
              <tr>
                    <td>商家ID</td>
                    <td><input name="mchtId" type="text" placeholder="请输入商家ID" class="easyui-numberbox" data-options="required:true" value="${mediaRecord.mchtId}"></td>
                    <td>视频ID</td>
                    <td><input name="mediaId" type="text" placeholder="请输入视频ID" class="easyui-numberbox" data-options="required:true" value="${mediaRecord.mediaId}"></td>
                </tr>
                 <tr>
                    <td>点击数</td>
                    <td><input name="clickCount" type="text" placeholder="请输入点击数" class="easyui-numberbox" data-options="required:true" value="${mediaRecord.clickCount}"></td>
                    <td>播放数</td>
                    <td><input name="playCount" type="text" placeholder="请输入播放数" class="easyui-numberbox" data-options="required:true" value="${mediaRecord.playCount}"></td>
                </tr>
                <tr>
                    <td>收藏数</td>
                    <td><input name="favorCount" type="text" placeholder="请输入收藏数" class="easyui-numberbox" data-options="required:true" value="${mediaRecord.favorCount}"></td>
                    <td>日期</td>
                    <td><input id="actionDate" name="actionDate" placeholder="点击选择时间"  onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate  value="${mediaRecord.actionDate}" pattern="yyyy-MM-dd"/>" /></td>
                </tr>
                <tr>
                    <td>评论数</td>
                    <td><input name="feedbackCount" type="text" placeholder="请输入评论数" class="easyui-numberbox" data-options="required:true" value="${mediaRecord.feedbackCount}"></td>
                    <td>分享数</td>
                    <td><input name="shareCount" type="text" placeholder="请输入分享数" class="easyui-numberbox" data-options="required:true" value="${mediaRecord.shareCount}"></td>
                </tr>
                <tr>
               	 	<td><input name="id" type="hidden"  value="${mediaRecord.id}"></td>
                </tr>
            </table>
        </form>
    </div>
</div>