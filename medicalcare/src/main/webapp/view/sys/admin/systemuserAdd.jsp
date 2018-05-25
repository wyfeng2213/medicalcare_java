<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {

    	$(document).ready(function () {
    		$("#userRole").combobox({
    		  onChange: function (n,o) {
    			  if ($('#userRole').combobox('getText') == '医疗机构管理员') {
    				  document.getElementById("hospitalName_").style.visibility="visible";
  			 		  document.getElementById("hospitalId_").style.visibility="visible";
    			  } else {
    				  document.getElementById("hospitalName_").style.visibility="hidden";
  			 		  document.getElementById("hospitalId_").style.visibility="hidden";
    			  }
    			 	/* if(n==4){
    			 		document.getElementById("hospitalName_").style.visibility="visible";
    			 		document.getElementById("hospitalId_").style.visibility="visible";
    			 	} */
    			}
    		});
    		
    		$("#hospitalId").combobox({
      		  onChange: function (n,o) {
      			 	//alert($('#hospitalId').combobox('getText'));
      			 	document.getElementById("hospitalName").value=$('#hospitalId').combobox('getText');
      			}
      		});
    	});
    	
    	
        $('#systemuserAddForm').form({
            url : '${path }/sys/systemUser/add.action',
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
        <form id="systemuserAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>登录名</td>
                    <td><input name="userName" type="text" placeholder="请输入登录名称" class="easyui-validatebox" data-options="required:true" value=""></td>
                    <td>真实姓名</td>
                    <td><input name="userRealname" type="text" placeholder="请输入姓名" class="easyui-validatebox" data-options="required:true" value=""></td>
                </tr>
                <tr>
                    <td>角色</td>
                    <td>
                        <select id="userRole" name="userRole" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" data-options="required:true">
							<c:forEach var="systemRole" items="${systemRoleList}" varStatus="cf">
								<option value="${systemRole.id}" >${systemRole.name}</option>
							</c:forEach>                  
						</select>
						<!-- <select id="userRole" name="userRole" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" data-options="required:true">
                            <option value="onerole" selected="selected">一级管理员</option>
                            <option value="secondrole" >二级管理员</option>
                            <option value="thridrole" >三级管理员</option>
                            <option value=1 >一级管理员</option>
                            <option value=2 >二级管理员</option>
                            <option value=3 >三级管理员</option>
                            <option value=4 >医疗机构管理员</option>
                        </select> -->
                    </td>
                    <td>联系方式</td>
                    <td><input name="userTel" type="text" placeholder="联系方式" class="easyui-validatebox" data-options="required:true" value=""></td>
                </tr>
                 <tr>
                    <td id="hospitalName_" style="visibility:hidden">所属医疗机构</td>
                    <td id="hospitalId_" style="visibility:hidden"><select id="hospitalId" name="hospitalId" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" data-options="required:true">
                         <c:forEach var="doctorsTeam" items="${doctorsTeamList}" varStatus="cf">
							<option value="${doctorsTeam.id}" >${doctorsTeam.name}</option>
						</c:forEach>
                        </select></td>
                    <td><input id="hospitalName" name="hospitalName" type="hidden" value=""></td>    
                </tr>
            </table>
        </form>
    </div>
</div>