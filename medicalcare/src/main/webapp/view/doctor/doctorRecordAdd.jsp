<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {

		$('#doctorRecordAddForm').form({
			url : '${path }/sys/doctorRecord/add.action',
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
				if (result.code == 200) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.messager.alert('提示', result.message, 'info');
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('提示', result.message, 'warning');
				}
			}
		});

	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden; padding: 3px;">
		<form id="doctorRecordAddForm" method="post"
			enctype="multipart/form-data">
			<table class="grid">
				<tr>
					<td>头像图片</td>
					<td><input name="file" type="file" placeholder="请输入图片"
						class="easyui-validatebox" value=""></td>
					<td>姓名</td>
					<td><input type="text" name="name" class="easyui-validatebox"
						value="" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>手机号</td>
					<td><input name="phone" type="text" class="easyui-validatebox"
						value="" data-options="required:true"></td>
					<td>就职机构</td>
					<td>
	                    <select id="hospital" name="hospital" class="easyui-combobox" data-options="width:150,height:29,editable:false" data-options="required:true">
	                        <c:forEach var="doctorsTeam" items="${doctorsTeamList}" varStatus="status">
								<option value="${doctorsTeam.name}" >${doctorsTeam.name}</option>
							</c:forEach>
	                    </select>
                    </td>
					<!-- <td><input type="text" name="hospital"
						class="easyui-validatebox" value="" data-options="required:true" /></td> -->
				</tr>
				<tr>
					<td>所在科室</td>
					<td><input name="department" type="text"
						class="easyui-validatebox" value="" data-options="required:true"></td>
					<td>职称</td>
					<td><input type="text" name="title" class="easyui-validatebox"
						value="" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>性别</td>
					<td><select name="sex">
							<option value="男">男</option>
							<option value="女">女</option>
					</select></td>
					<td>开始工作时间</td>
					<td><input name="startWorkingTime" placeholder="点击选择时间"
						readonly="readonly"
						onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
				</tr>
				<tr>
					<td>科室电话</td>
					<td><input name="departmentTelephone" type="text"
						class="easyui-validatebox" value=""></td>
					<td>帐号类型</td>
					<td><select name="testAccount">
							<option value="0">测试帐号</option>
							<option value="1">正式帐号</option>
					</select></td>
				</tr>
				<tr>
					<td>医生类型:</td>
					<td><select name="doctortype">
							<option value="1">专科医生</option>
							<option value="2">签约医生</option>
							<option value="3">健康咨询师</option>
					</select></td>

					<td>推荐人</td>
					<td><input type="text" name="recommended"
						class="easyui-validatebox" value="" /></td>
				</tr>
				<tr>
					<td>省份</td>
					<td><input name="province" type="text"
						class="easyui-validatebox" value="" data-options="required:true"></td>
					<td>市区</td>
					<td><input type="text" name="cityName"
						class="easyui-validatebox" value="" data-options="required:true" /></td>
				</tr>

				<tr>
					<td>区县</td>
					<td><input name="area" type="text" class="easyui-validatebox"
						value="" data-options="required:true"></td>
					<td>图文咨询</td>
					<td><select>
							<option value="1">是</option>
							<option value="0">否</option>
					</select></td>
				</tr>

				<tr>

					<td>证件图片</td>
					<td><input name="certificate" type="file" placeholder="请输入图片"
						class="easyui-validatebox" value=""></td>
				</tr>
				<tr>
					<td>排序序号</td>
					<td><input name="sequence" type="text"
						class="easyui-validatebox" data-options="required:true" value="999"></td>
				</tr>
				<tr>
					<td>简介</td>
					<td>
						<%--  <input type="textarea" name="status"  value="${doctorRecord.introduction}" validtype="length[0,255]"/> --%>
						<textarea name="introduction" cols="28" rows="6"></textarea>
					</td>
				</tr>


			</table>
		</form>
	</div>
</div>