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
		<form id="hospitalQueryForm" method="post"
			enctype="multipart/form-data">
			<table class="grid">
				<tr>
					<td>机构名称:</td>
					<td><input type="text" name="name" disabled="false"
						class="easyui-validatebox" value="${doctorsTeam.name}" /></td>
				</tr>
				<tr>
					<td>机构电话:</td>
					<td><input name="hospitalPhone" type="text" disabled="false"
						class="easyui-validatebox" value="${doctorsTeam.hospitalPhone}"></td>

				</tr>
				<tr>
					<td>机构地址:</td>
					<td><input type="text" cols="28" name="hospitalAddr"
						disabled="false" class="easyui-validatebox"
						value="${doctorsTeam.hospitalAddr}" /></td>
				</tr>

				<tr>
					<td>创建时间:</td>
					<td><input type="datetime" name="createtime" disabled="false"
						class="easyui-validatebox"
						value="<fmt:formatDate  value="${doctorsTeam.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
				</tr>
				<tr>
					<td>省份:</td>
					<td><input name="province" type="text" disabled="false"
						class="easyui-validatebox" value="${doctorsTeam.province}"></td>
				</tr>
				<tr>
					<td>市区:</td>
					<td><input type="text" name="cityName" disabled="false"
						class="easyui-validatebox" value="${doctorsTeam.cityName}" /></td>
				</tr>

				<tr>
					<td>区县:</td>
					<td><input name="area" type="text" disabled="false"
						class="easyui-validatebox" value="${doctorsTeam.area}"></td>
				</tr>
				<tr>
					<td>医院图片:</td>
					<td><c:choose>
							<c:when test="${isHospitalIMG=='1'}">
								<a href="${doctorsTeam.hospitalIMG}" target="_blank">图片</a>
							</c:when>
							<c:otherwise>
								无
							</c:otherwise>
						</c:choose></td>
				</tr>
				<tr>
					<td>简介:</td>
					<td>
						<%--  <input type="textarea" name="status"  value="${doctorRecord.introduction}" validtype="length[0,255]"/> --%>
						<textarea name="introduction" disabled="false" cols="28" rows="6">${doctorsTeam.introduction}</textarea>
					</td>
				</tr>


			</table>
		</form>
	</div>
</div>