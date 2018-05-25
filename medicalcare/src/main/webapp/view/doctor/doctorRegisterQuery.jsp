<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {

	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden; padding: 3px;">
		<form id="doctorRegisterQueryForm" method="post"
			enctype="multipart/form-data">
			<table class="grid">
			<tr>
					<td>医生头像:</td>
					<td><img src="${doctorRecord.headUrl}" height="100" width="100" alt="医生头像" /></td>
				</tr>
				<tr>
					<td>医生ID:</td>
					<td><input name=loginId type="text" disabled="false"
						class="easyui-validatebox" value="${doctorRecord.loginId}"></td>
					<td>姓名:</td>
					<td><input type="text" name="status" disabled="false"
						class="easyui-validatebox" value="${doctorRecord.name}" /></td>
				</tr>
				<tr>
					<td>手机号:</td>
					<td><input name="phone" type="text" disabled="false"
						class="easyui-validatebox" value="${doctorRecord.phone}"></td>
					<td>就职机构:</td>
					<td><input type="text" name="hospital" disabled="false"
						class="easyui-validatebox" value="${doctorRecord.hospital}" /></td>
				</tr>
				<tr>
					<td>所在科室:</td>
					<td><input name="department" type="text" disabled="false"
						class="easyui-validatebox" value="${doctorRecord.department}"></td>
					<td>职称:</td>
					<td><input type="text" name="title" disabled="false"
						class="easyui-validatebox" value="${doctorRecord.title}" /></td>
				</tr>
				<tr>
					<td>性别:</td>
					<td><input name="sex" type="text" disabled="false"
						class="easyui-validatebox" value="${doctorRecord.sex}"></td>
					<td>开始工作时间:</td>
					<td><input type="datetime" name="startWorkingTime"
						disabled="false" class="easyui-validatebox"
						value="<fmt:formatDate  value="${doctorRecord.startWorkingTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
				</tr>
				<tr>
					<td>科室电话:</td>
					<td><input name="departmentTelephone" type="text"
						disabled="false" class="easyui-validatebox"
						value="${doctorRecord.departmentTelephone}"></td>
					<td>申请时间:</td>
					<td><input type="datetime" name="createtime" disabled="false"
						class="easyui-validatebox" value="<fmt:formatDate  value="${doctorRecord.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
				</tr>
				<tr>
					<td>医生类型:</td>
					<c:choose>
						<c:when test="${doctorRecord.doctortype==1}">
							<td><input name="doctortype" type="text" disabled="false"
								class="easyui-validatebox" value="专科医生"></td>
						</c:when>
						<c:otherwise>
							<td><input name="doctortype" type="text" disabled="false"
								class="easyui-validatebox" value="家庭医生"></td>
						</c:otherwise>
					</c:choose>

					<td>推荐人:</td>
					<td><input type="text" name="recommended" disabled="false"
						class="easyui-validatebox" value="${doctorRecord.recommended}" /></td>
				</tr>
				<tr>
					<td>省份:</td>
					<td><input name="province" type="text" disabled="false"
						class="easyui-validatebox" value="${doctorRecord.province}"></td>
					<td>市区:</td>
					<td><input type="text" name="cityName" disabled="false"
						class="easyui-validatebox" value="${doctorRecord.cityName}" /></td>
				</tr>

				<tr>
					<td>区县:</td>
					<td><input name="area" type="text" disabled="false"
						class="easyui-validatebox" value="${doctorRecord.area}"></td>
					<td>图文咨询:</td>
					<c:choose>
						<c:when test="${doctorRecord.graphicConsulting==1}">
							<td><input name="graphicConsulting" type="text"
								disabled="false" class="easyui-validatebox"
								value="是"></td>
						</c:when>
						<c:otherwise>
							<td><input name="graphicConsulting" type="text"
								disabled="false" class="easyui-validatebox"
								value="否"></td>
						</c:otherwise>
					</c:choose>


				</tr>
				<tr>
					<td>审核状态:</td>
					<c:choose>
						<c:when test="${doctorRecord.status==0}">
							<td><input name="status" type="text" disabled="false"
								class="easyui-validatebox" value="待审核"></td>
						</c:when>
						<c:when test="${doctorRecord.status==1}">
							<td><input name="status" type="text" disabled="false"
								class="easyui-validatebox" value="已通过"></td>
						</c:when>
						<c:otherwise>
							<td><input name="doctortype" type="text" disabled="false"
								class="easyui-validatebox" value="已拒绝"></td>
						</c:otherwise>
					</c:choose>

					<td>帐号类型:</td>
					<c:choose>
						<c:when test="${doctorRecord.testAccount==0}">
							<td><input name="testAccount" type="text" disabled="false"
								class="easyui-validatebox" value="测试医生"></td>
						</c:when>
						<c:otherwise>
							<td><input name="testAccount" type="text" disabled="false"
								class="easyui-validatebox" value="正式医生"></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td>证件:</td>
					<td>
						<c:forEach var="certificateUrl" items="${certificateUrls}" varStatus="cf">
							<a href="${certificateUrl}"  target="_blank">证件${cf.index+1}</a>
						</c:forEach>
					</td>
				</tr>
				<tr height="10px"></tr>
				<tr>
					<td>简介:</td>
					<td>
						<%--  <input type="textarea" name="status"  value="${doctorRecord.introduction}" validtype="length[0,255]"/> --%>
						<textarea name="introduction" disabled="false" cols="28" rows="6">${doctorRecord.introduction}</textarea>
					</td>
				</tr>


			</table>
		</form>
	</div>
</div>