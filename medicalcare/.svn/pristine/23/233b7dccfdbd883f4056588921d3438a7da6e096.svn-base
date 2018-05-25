<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function() {
    $('#doctorRecordEditForm').form({
        url : '${path }/sys/doctorRecord/edit.action',
        onSubmit : function() {
            progressLoad();
            var isValid = $(this).form('validate');
            if (!isValid) {
                progressClose();
            }
            return isValid;
        },
        success : function(result) {
        	//alert('123');
            progressClose();
            result = $.parseJSON(result);
            if (result.code==200) {
                parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                parent.$.modalDialog.handler.dialog('close');
            } else {
                parent.$.messager.alert('错误', result.message, 'error');
            } 
        }
    });
    
});

     
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="doctorRecordEditForm" method="post" enctype="multipart/form-data">
            <table class="grid">
            	<tr>
					<td>医生头像:</td>
					<td><img src="${doctorRecord.headUrl}" height="100" width="100" alt="医生头像" /></td>
				</tr>
				<tr>
					<td>医生ID:</td>
					<td><input name=loginId type="text"   disabled="false"
						class="easyui-validatebox" value="${doctorRecord.loginId}"></td>
					<td>姓名:</td>
					<td><input type="text" name="name"  disabled="false"
						class="easyui-validatebox" value="${doctorRecord.name}" /></td>
				</tr>
				<tr>
					<td>手机号:</td>
					<td><input name="phone" type="text"   disabled="false"
						class="easyui-validatebox" value="${doctorRecord.phone}"></td>
					<td>就职机构:</td>
					<td><input type="text" name="hospital"   disabled="false"
						class="easyui-validatebox" value="${doctorRecord.hospital}" /></td>
				</tr>
				<tr>
					<td>所在科室:</td>
					<td><input name="department" type="text" 
						class="easyui-validatebox" value="${doctorRecord.department}"></td>
					<td>职称:</td>
					<td><input type="text" name="title"
						class="easyui-validatebox" value="${doctorRecord.title}" /></td>
				</tr>
				<tr>
					<td>性别:</td>
					<td><input name="sex" type="text"
						class="easyui-validatebox" value="${doctorRecord.sex}"></td>
					<td>开始工作时间:</td>
					<td><input type="datetime" name="startWorkingTime"
						class="easyui-validatebox" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"
						value="<fmt:formatDate  value="${doctorRecord.startWorkingTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
				</tr>
				<tr>
					<td>科室电话:</td>
					<td><input name="departmentTelephone" type="text"
						class="easyui-validatebox"
						value="${doctorRecord.departmentTelephone}"></td>
					<%-- <td>创建时间:</td>
					<td><input type="datetime" name="createtime"   
						class="easyui-validatebox" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" value="<fmt:formatDate  value="${doctorRecord.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td> --%>
				</tr>
				<tr>
					<td>医生类型:</td>
					<c:choose>
						<c:when test="${doctorRecord.doctortype==1}">
							<td><input type="text"  disabled="false"
								class="easyui-validatebox" value="专科医生"></td>
						</c:when>
						<c:otherwise>
							<td><input type="text"  disabled="false"
								class="easyui-validatebox" value="家庭医生"></td>
						</c:otherwise>
					</c:choose>

					<td>推荐人:</td>
					<td><input type="text" name="recommended"
						class="easyui-validatebox" value="${doctorRecord.recommended}" /></td>
				</tr>
				<tr>
					<td>省份:</td>
					<td><input name="province" type="text"
						class="easyui-validatebox" value="${doctorRecord.province}"></td>
					<td>市区:</td>
					<td><input type="text" name="cityName"
						class="easyui-validatebox" value="${doctorRecord.cityName}" /></td>
				</tr>

				<tr>
					<td>区县:</td>
					<td><input name="area" type="text"
						class="easyui-validatebox" value="${doctorRecord.area}"></td>
					<td>图文咨询:</td>
					<c:choose>
						<c:when test="${doctorRecord.graphicConsulting==1}">
							<td><input  type="text"  disabled="false"
								class="easyui-validatebox"
								value="是"></td>
						</c:when>
						<c:otherwise>
							<td><input type="text"  disabled="false"
								class="easyui-validatebox"
								value="否"></td>
						</c:otherwise>
					</c:choose>


				</tr>
				<tr>
                    <td>头像：</td>
                    <td><a href="${doctorRecord.headUrl}" target="_blank">图片</a></td>
                    <td>证件：</td>
               		<td><a href="${doctorRecord.certificateUrl}" target="_blank">图片</a></td>	
                </tr>
                <tr>
					<td>更新头像</td>
					<td><input name="file" type="file" placeholder="请输入图片"
						class="easyui-validatebox" value=""></td>
					<td>更新证件</td>
					<td><input name="certificate" type="file" placeholder="请输入图片"
						class="easyui-validatebox" value=""></td>
				</tr>
				<td>排序序号:</td>
					<td><input name="sequence" type="text"
						class="easyui-validatebox" value="${doctorRecord.sequence}"></td>
				<tr>
					<td>简介:</td>
					<td>
						<%--  <input type="textarea" name="status"  value="${doctorRecord.introduction}" validtype="length[0,255]"/> --%>
						<textarea name="introduction" cols="28" rows="6">${doctorRecord.introduction}</textarea>
					</td>
				</tr>

				<tr>
                    <td><input name="id" type="hidden"  value="${doctorRecord.id}">
                    <td><input name="password" type="hidden"  value="${doctorRecord.password}">
                </tr>
                
                <tr>
                    <td><input name="headUrl" type="hidden"  value="${doctorRecord.headUrl}">
                    <td><input name="certificateUrl" type="hidden"  value="${doctorRecord.certificateUrl}">
                </tr>
	
				<tr>
                    <td><input name="updatetime" type="hidden"  value="<fmt:formatDate  value="${doctorRecord.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
                    <td><input name="isPhoneConsultation" type="hidden"  value="${doctorRecord.isPhoneConsultation}">
                </tr>
				
				<tr>
                    <td><input name="isVideoConsultation" type="hidden"  value="${doctorRecord.isVideoConsultation}">
                    <td><input name="easemobUuid" type="hidden"  value="${doctorRecord.easemobUuid}">
                </tr>
				
				<tr>
                    <td><input name="isPhoneConsultation" type="hidden"  value="${doctorRecord.isPhoneConsultation}">
                	<td><input type="hidden" name="doctortype" value="${doctorRecord.doctortype}"></td>
                	<td><input type="hidden" name="graphicConsulting" value="${doctorRecord.graphicConsulting}"></td>
                </tr>
				
			</table>
        </form>
    </div>
</div>