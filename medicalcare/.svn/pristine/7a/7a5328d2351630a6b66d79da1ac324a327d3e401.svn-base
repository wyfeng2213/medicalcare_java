<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp"%>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<script type="text/javascript">
	var dataGrid;
	var organizationTree;

	$(function() {

		dataGrid = $('#dataGrid')
				.datagrid(
						{
							url : '${path }/sys/systemBanner/findPage.action',
							fit : true,
							striped : true,
							rownumbers : true,
							pagination : true,
							singleSelect : true,
							idField : 'id',
							userName : 'createTime',
							sortOrder : 'asc',
							pageSize : 10,
							pageList : [ 10, 20, 30, 40, 50, 100, 200, 300,
									400, 500 ],
							columns : [ [
									{
										width : '80',
										title : '图片种类',
										field : 'type',
										sortable : true,
										formatter : function(value, row, index) {
											switch (value) {
											case 1:
												return '粤健康首页';
											case 2:
												return '健康优品';
											}
										}
									},
									{
										width : '180',
										title : '标题',
										field : 'title',
										sortable : true
									},
									{
										width : '180',
										title : '图片',
										field : 'imgSrc'
									},
									{
										width : '180',
										title : '内容地址',
										field : 'pageUrl'
									},
									{
										width : '130',
										title : '创建时间',
										field : 'createtime',
										formatter : function(value, row, index) {
											if (value) {
												return datetimeFormat_1(value);
											} else {
												return "";
											}
										}
									},
									{
										width : '100',
										title : '更新时间',
										field : 'updatetime',
										formatter : function(value, row, index) {
											if (value) {
												return datetimeFormat_1(value);
											} else {
												return "";
											}
										}
									},
									{
										field : 'action',
										title : '操作',
										width : "190",
										formatter : function(value, row, index) {
											var str = '';
											str += $
													.formatString(
															'<a href="javascript:void(0)" class="user-easyui-linkbutton-dispose" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >修改</a>',
															row.id);
											str += $
													.formatString(
															'<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-delete\'" onclick="deleteFun(\'{0}\');" >删除</a>',
															row.id);
											if (row.flag == 1) {
												str += $
														.formatString(
																'<a href="javascript:void(0)" class="user-easyui-linkbutton-no" data-options="plain:true,iconCls:\'icon-no\'" onclick="rejectFun(\'{0}\',\'{1}\');" >拒绝审核</a>',
																row.id,row.title);
											} else if (row.flag == 0) {
												str += $
														.formatString(
																'<a href="javascript:void(0)" class="user-easyui-linkbutton-ok" data-options="plain:true,iconCls:\'icon-ok\'" onclick="passFun(\'{0}\',\'{1}\');" >通过审核</a>',
																row.id,row.title);
											}
											return str;
										}
									} ] ],
							onLoadSuccess : function(data) {
								$('.user-easyui-linkbutton-dispose')
										.linkbutton({
											text : '修改',
											plain : true,
											iconCls : 'icon-edit'
										});
								$('.user-easyui-linkbutton-del').linkbutton({
									text : '删除',
									plain : true,
									iconCls : 'icon-del'
								});
								$('.user-easyui-linkbutton-ok').linkbutton({
									text : '通过审核',
									plain : true,
									iconCls : 'icon-ok'
								});
								$('.user-easyui-linkbutton-no').linkbutton({
									text : '拒绝审核',
									plain : true,
									iconCls : 'icon-no'
								});
							},
							toolbar : '#toolbar'
						});
	});

	
	function rejectFun(id, name) {
		parent.$.messager.confirm('询问', '您是否要拒绝审核<font color=\'red\'> ' + name
				+ '</font> ？', function(b) {
			if (b) {
				progressLoad();
				$.post('${path }/sys/systemBanner/reject.action?id=' + id, {
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
		parent.$.messager.confirm('询问', '您是否要同意审核<font color=\'red\'> ' + name
				+ '</font> ？', function(b) {
			if (b) {
				progressLoad();
				$.post('${path }/sys/systemBanner/pass.action?id=' + id,{
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
	
	
	function addFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 600,
			height : 400,
			href : '${path }/sys/systemBanner/addPage.action',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler
							.find('#systemBannerAddForm');
					f.submit();
				}
			} ]
		});
	}

	function deleteFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前数据？', function(b) {
			if (b) {
				progressLoad();
				$.post('${path }/sys/systemBanner/delete.action', {
					id : id
				}, function(result) {
					if (result.code == 200) {
						parent.$.messager.alert('提示', result.message, 'info');
						dataGrid.datagrid('reload');
					}
					progressClose();
				}, 'JSON');
			}
		});
	}

	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '修改',
			width : 600,
			height : 400,
			href : '${path }/sys/systemBanner/editPage.action?id=' + id,
			buttons : [ {
				text : '确定',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#editPageForm');
					f.submit();
				}
			} ]
		});
	}

	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<!--  <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="searchForm">
            <table>
                <tr>
                    <th>姓名:</th>
                    <td><input name="userName" placeholder="请输入用户姓名"/></td>
                    <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div> -->
	<div data-options="region:'center',border:true,title:'用户列表'">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<shiro:hasPermission name="/sys/systemBanner/addPage">
			<a onclick="addFun();" href="javascript:void(0);"
				class="easyui-linkbutton"
				data-options="plain:true,iconCls:'icon-add'">添加</a>
		</shiro:hasPermission>
	</div>
</body>
</html>