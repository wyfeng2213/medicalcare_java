<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {

        $('#assignRolePageForm').form({
            url : '${path }/sys/systemUser/assignRole.action',
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
        <form id="assignRolePageForm" method="post">
            <table class="grid">
                <tr>
                    <td>登录名<input name="id" type="hidden"  value="${user.id}"></td>
                    <td><input name="userName" type="text" readonly="readonly" placeholder="请输入登录名称" class="easyui-validatebox" data-options="required:true" value="${user.userName}"></td>
                    <td>真实姓名</td>
                    <td><input name="userRealname" type="text" readonly="readonly"  placeholder="请输入姓名" class="easyui-validatebox" data-options="required:true" value="${user.userRealname}"></td>
                </tr>
                <tr>
                    <td>角色</td>
                    <td>
						<select id="userRole" name="userRole" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" data-options="required:true">
							<c:forEach var="systemRole" items="${systemRoleList}" varStatus="cf">
								<option value="${systemRole.id}" >${systemRole.name}</option>
							</c:forEach>                  
						</select>
					</td>
                    <!-- <td>
                        <select name="userRole" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" data-options="required:true">
                            <option value="onerole" selected="selected">一级管理员</option>
                            <option value="secondrole" >二级管理员</option>
                            <option value="thridrole" >三级管理员</option>
                            <option value=1 selected="selected">一级管理员</option>
                            <option value=2 >二级管理员</option>
                            <option value=3 >三级管理员</option>
                        </select>
                    </td> -->
                    <td>联系方式</td>
                    <td><input name="userTel" readonly="readonly"  type="text" placeholder="联系方式" class="easyui-validatebox" data-options="required:true" value="${user.userTel}"></td>
                </tr>
            </table>
        </form>
    </div>
</div>