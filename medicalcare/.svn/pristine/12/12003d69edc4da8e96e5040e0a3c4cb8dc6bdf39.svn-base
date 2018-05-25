<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#productEditForm').form({
            url : '${path }/crm/product/edit.do',
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
        <form id="productEditForm" method="post">
            <table class="grid">
            	<tr>
            		<td>产品ID</td>
                    <td><input name="productId" type="text" placeholder="请输入产品ID" validType="length[1,32]" class="easyui-validatebox span2" data-options="required:true" value="${product.productId}"></td>
                    <td>产品名称</td>
                    <td><input name="productName" type="text" placeholder="请输入产品名称" validType="length[1,64]" class="easyui-validatebox span2" data-options="required:true" value="${product.productName}"></td>
            	</tr>
            	 <tr>                
                    <td>产品说明</td>
                    <td colspan="3"><textarea name="productDetail" validType="length[1,1024]" placeholder="请输入产品说明" class="easyui-validatebox span2" data-options="required:false" style="width:90%;" rows="3" >${product.productDetail}</textarea></td>
                </tr>
                <tr>
                    <td>流量大小（M）</td>
                    <td><input name="flowSize" type="text" placeholder="请输入流量大小" validType="length[1,22]" class="easyui-validatebox span2" data-options="required:true" value="${product.flowSize}"></td>
                    <td>价格</td>
                    <td><input name="price" type="text" placeholder="请输入价格" class="easyui-validatebox span2" data-options="required:true" value="${product.price}"></td>
                </tr>
                <tr>
                	<td>有效开始时间</td>
                    <td><input id="validStartTime" name="validStartTime" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" value="<fmt:formatDate  value="${product.validStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
                	<td>有效结束时间</td>
                   <td> <input  id="validEndTime" name="validEndTime" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" value="<fmt:formatDate  value="${product.validEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/></td>
                </tr>
                <tr>
                    <td>有效时长（天）</td>
                    <td><input id="effectDuration" name="effectDuration" type="text" validType="length[1,11]" placeholder="" class="easyui-validatebox span2" data-options="required:true" value="${product.effectDuration}" ></td>
                    <td>优先级</td>
                    <td><input name="rank" value="${product.rank}" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false"></td>
                </tr>
                <tr>
                    <td>状态</td>
                    <td >
                        <select id="status" name="status" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
				                <option value="0" <c:if test="${product.status=='0'}">selected='selected'</c:if>> 有效</option>
				                <option value="1" <c:if test="${product.status=='1'}">selected='selected'</c:if>>过期</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3"><textarea name="remark" rows="3" cols="" style="width:90%;" maxlength="255">${product.remark}</textarea></td>
                </tr>
                <tr>
                	<td><input name="id" type="hidden"  value="${product.id}">
                </td>
            </table>
        </form>
    </div>
</div>