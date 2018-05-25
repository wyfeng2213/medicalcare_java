<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#lableEditForm').form({
            url : '${path }/crm/mediaCategory/lable/edit.do',
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
        
        
        $("#description").val('${role.description}');
        
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="lableEditForm" method="post">
            <table class="grid">
               <tr>
                    <td>频道ID</td>
                    <td >
                        <select id="categoryId" name="categoryId" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" value="${media.parentId }">
                          <c:forEach var="id" items="${ids}">
                           		<c:choose>
						           <c:when test="${id.id==lable.categoryId }">
						                <option value="${id.id}"  selected>${id.categoryName}</option>
						           </c:when>
						           <c:otherwise>
						                <option value="${id.id}">${id.categoryName}</option>
						           </c:otherwise>
						        </c:choose> 
                           </c:forEach>
                        </select>
                    </td>
                    <td><input name="id" type="hidden"  value="${lable.id}">
                </tr>
                <tr>
                    <td>标签类别名</td>
                    <td>
                    	<input name="labelType" value="${lable.labelType}" class="easyui-validatebox span2" required="required" >
                    </td>
                </tr>
                <tr>
                    <td>标签KEY</td>
                    <td>
                    	<input name="labelKey" value="${lable.labelKey}" class="easyui-validatebox span2"  required="required">
                   	</td>
                </tr>
				<tr>
                    <td>显示顺序</td>
                    <td>
                    	<input name="rank" value="${lable.rank}" class="easyui-validatebox span2"  required="required">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>