<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>	
<%@ page import="com.cmcc.medicalcare.model.SystemUser" %>	
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp"%>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医生注册审核</title>
<%
 SystemUser systemUser = (SystemUser)session.getAttribute("systemUser");
 String hospitalName = systemUser.getHospitalName();
 Integer hospitalId = systemUser.getHospitalId();
 System.out.println(hospitalName);
 boolean flag = (hospitalName!=null&&!hospitalName.equals("")&&hospitalId!=null)?true:false;
 %>
<script type="text/javascript">
	var dataGrid;
	var hospitalId=null;
    if(<%=flag%>){
    	hospitalId = <%=hospitalId%>;
    	//alert(hospitalId);
    }

	$(function() {
		$(document).ready(function() {
			$("#status").combobox({
				onChange : function(n, o) {
					searchFun();
				}
			});
		});
		
		dataGrid = $('#dataGrid')
				.datagrid(
						{
							url : '${path }/sys/doctorRegister/findUserPage.action?hospitalId2='+hospitalId,
							fit : true,
							striped : true,
							rownumbers : true,
							pagination : true,
							singleSelect : true,
							idField : 'id',
							sortName : 'id',
							sortOrder : 'asc',
							pageSize : 20,
							pageList : [ 10, 20, 30, 40, 50, 100, 200, 300,
									400, 500 ],
							columns : [ [
									{
										width : '130',
										title : '申请时间',
										field : 'createtime',
										sortable : true,
										formatter : function(val, row, index) {
											if (val != null) {
												var date = new Date(
														parseInt(
																val
																		.toString()
																		.replace(
																				"/Date(",
																				"")
																		.replace(
																				")/",
																				""),
																10));
												//月份为0-11，所以+1，月份小于10时补个0  
												var month = date.getMonth() + 1 < 10 ? "0"
														+ (date.getMonth() + 1)
														: date.getMonth() + 1;
												var currentDate = date
														.getDate() < 10 ? "0"
														+ date.getDate() : date
														.getDate();
												var hours = date.getHours() < 10 ? "0"
														+ date.getHours()
														: date.getHours();
												var min = date.getMinutes() < 10 ? "0"
														+ date.getMinutes()
														: date.getMinutes();
												var sec = date.getSeconds() < 10 ? "0"
														+ date.getSeconds()
														: date.getSeconds();
												return date.getFullYear() + "-"
														+ month + "-"
														+ currentDate + " "
														+ hours + ":" + min
														+ ":" + sec;
											}
											return "";
										}
									},
									{
										width : '130',
										title : '姓名',
										field : 'name',
										sortable : true
									},
									{
										width : '130',
										title : '手机号',
										field : 'phone',
										sortable : true
									},
									{
										width : '130',
										title : '就职机构',
										field : 'hospital'
									},
									{
										width : '130',
										title : '所在科室',
										field : 'department',
										sortable : true
									},
									{
										width : '130',
										title : '职称',
										field : 'title',
										sortable : true
									},
									{
										width : '100',
										title : '审核状态',
										field : 'status',
										sortable : true,
										formatter : function(value, row, index) {
											if (value == "0") {
												return "<font color='black'>待审核</font>";
											} else if (value == "1") {
												return "<font color='green'>已通过</font>";
											} else if (value == "2") {
												return "<font color='red'>已拒绝</font>";
											}
										}
									},
									{
										field : 'action',
										title : '操作',
										width : 205,
										formatter : function(value, row, index) {
											var str = '';
											str += $
													.formatString(
															'<a href="javascript:void(0)" class="user-easyui-linkbutton-search" data-options="plain:true,iconCls:\'icon-search\'" onclick="query(\'{0}\')" >详情</a>',
															row.id);

											str += '&nbsp;&nbsp;|&nbsp;&nbsp;';

											if (row.status == 1) {

											} else if (row.status == 2) {
												str += $
														.formatString(
																'<a href="javascript:void(0)" class="user-easyui-linkbutton-refresh" data-options="plain:true,iconCls:\'icon-refresh\'" onclick="rePassFun(\'{0}\',\'{1}\');" >重新通过</a>',
																row.id,
																row.name);
											} else if (row.status == 0) {
												str += $
														.formatString(
																'<a href="javascript:void(0)" class="user-easyui-linkbutton-ok" data-options="plain:true,iconCls:\'icon-ok\'" onclick="passFun(\'{0}\',\'{1}\');" >通过</a>',
																row.id,
																row.name);
												str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
												str += $
														.formatString(
																'<a href="javascript:void(0)" class="user-easyui-linkbutton-no" data-options="plain:true,iconCls:\'icon-no\'" onclick="rejectFun(\'{0}\',\'{1}\');" >拒绝</a>',
																row.id,
																row.name);
											}
											return str;
										}
									} ] ],
							onLoadSuccess : function(data) {
								$('.user-easyui-linkbutton-search').linkbutton(
										{
											text : '详情',
											plain : true,
											iconCls : 'icon-search'
										});
								$('.user-easyui-linkbutton-ok').linkbutton({
									text : '通过',
									plain : true,
									iconCls : 'icon-ok'
								});
								$('.user-easyui-linkbutton-no').linkbutton({
									text : '拒绝',
									plain : true,
									iconCls : 'icon-no'
								});
								$('.user-easyui-linkbutton-refresh')
										.linkbutton({
											text : '重新通过',
											plain : true,
											iconCls : 'icon-refresh'
										});
							},
							toolbar : '#toolbar'
						});
	});

	function query(id) {
		parent.$.modalDialog({
			title : '查看医生详情',
			width : 800,
			height : 550,
			href : '${path }/sys/doctorRegister/queryPage.action?id=' + id,
			buttons : [ {
				text : '关闭',
				handler : function() {
					parent.$.modalDialog.handler.dialog('destroy');
					parent.$.modalDialog.handler = undefined;
				}
			} ]
		});
	}

	function rejectFun(id, name) {
		parent.$.messager.confirm('询问', '您是否要拒绝<font color=\'red\'> ' + name
				+ '</font> 申请？', function(b) {
			if (b) {
				progressLoad();
				$.post('${path }/sys/doctorRegister/reject.action?id=' + id, {
				}, function(result) {
					if (result.code === 200) {
						parent.$.messager.alert('提示', result.message, 'info');
						dataGrid.datagrid('load',  $.serializeObject($('#searchForm')));
					}
					progressClose();
				}, 'JSON');
			}
		});
	}

	function passFun(id, name) {
		parent.$.messager.confirm('询问', '您是否要同意<font color=\'red\'> ' + name
				+ '</font> 申请？', function(b) {
			if (b) {
				progressLoad();
				$.post('${path }/sys/doctorRegister/pass.action?id=' + id,{
				}, function(result) {
					if (result.code === 200) {
						parent.$.messager.alert('提示', result.message, 'info');
						dataGrid.datagrid('load',  $.serializeObject($('#searchForm')));
					}
					progressClose();
				}, 'JSON');
			}
		});
	}

	function rePassFun(id, name) {
		parent.$.messager.confirm('询问', '您是否要重新同意<font color=\'red\'> ' + name
				+ '</font> 申请？', function(b) {
			if (b) {
				progressLoad();
				$.post('${path }/sys/doctorRegister/rePass.action', {
					id : id
				}, function(result) {
					if (result.code === 200) {
						parent.$.messager.alert('提示', result.message, 'info');
						dataGrid.datagrid('load',  $.serializeObject($('#searchForm')));
					}
					progressClose();
				}, 'JSON');
			}
		});
	}

	function searchFun() {
		document.getElementById("keyword").value='';
		dataGrid.datagrid('load', {status:$("#status").combobox("getValue")});
	}
	
	function searchFun_() {
		dataGrid.datagrid('load', {keyword:encodeURI(document.getElementById("keyword").value)});
	}
	
	function cleanFun() {
		$('#searchForm_ input').val('');
		dataGrid.datagrid('load', {});
	}

	function test() {
		alert("123");
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false"
		style="height: 55px; overflow: hidden; background-color: #fff">
		<table>
			<tr>
				<td>
				<form id="searchForm">
					<table>
						<tr>
							<!-- <td><input name="type" type="hidden" value="0"></td> -->
							<th>审核状态:</th>
							<td><select id="status" name="status"
								class="easyui-combobox"
								data-options="width:140,height:29,editable:false,panelHeight:'auto'">
									<option value="0">待审核</option>
									<option value="1">已通过</option>
									<option value="2">已拒绝</option>
									<option value="3" selected='selected'>全部</option>
							</select></td>
						</tr>
					</table>
				</form>
			</td>
			<td width="600"></td>
			<td>
				<form id="searchForm_">
					<table>
						<tr>
							<!-- <td><input name="type" type="hidden " value="1"></td> -->
							<td><input name="keyword" type="text" id="keyword"
								class="easyui-validatebox" value=""></td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton"
								data-options="iconCls:'icon-search',plain:true"
								onclick="searchFun_();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
						</tr>
					</table>
				</form>
				</td>
			</tr>
		</table>
	</div>

	<div data-options="region:'center',border:true,title:'医生列表'">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>

	<div id="toolbar" style="display: none;"></div>
</body>
</html>