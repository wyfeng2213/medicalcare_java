<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp"%>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机构管理</title>
<script type="text/javascript">
	var dataGrid;

	$(function() {

		dataGrid = $('#dataGrid')
				.datagrid(
						{
							url : '${path }/sys/hospital/findUserPage.action',
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
										title : '机构id',
										field : 'id',
										sortable : true
									},
									{
										width : '180',
										title : '机构名称',
										field : 'name',
										sortable : true
									},
									{
										width : '130',
										title : '创建时间',
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
										field : 'action',
										title : '操作',
										width : 205,
										formatter : function(value, row, index) {
											var str = '';
											str += $
													.formatString(
															'<a href="javascript:void(0)" class="user-easyui-linkbutton-search" data-options="plain:true,iconCls:\'icon-search\'" onclick="query(\'{0}\')" >查看</a>',
															row.id);

											str += '&nbsp;&nbsp;|&nbsp;&nbsp;';

											str += $
													.formatString(
															'<a href="javascript:void(0)" class="user-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >编辑</a>',
															row.id);
											str += '&nbsp;&nbsp;|&nbsp;&nbsp;';

											str += $
													.formatString(
															'<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-del\'" onclick="deleteFun(\'{0}\',\'{1}\');" >删除</a>',
															row.id, row.name);
											
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
								$('.user-easyui-linkbutton-edit').linkbutton({
									text : '编辑',
									plain : true,
									iconCls : 'icon-edit'
								});
								$('.user-easyui-linkbutton-del').linkbutton({
									text : '删除',
									plain : true,
									iconCls : 'icon-del'
								});

							},
							toolbar : '#toolbar'
						});
	});

	function query(id) {
		parent.$.modalDialog({
			title : '查看机构详情',
			width : 330,
			height : 520,
			href : '${path }/sys/hospital/queryPage.action?id=' + id,
			buttons : [ {
				text : '关闭',
				handler : function() {
					parent.$.modalDialog.handler.dialog('destroy');
					parent.$.modalDialog.handler = undefined;
				}
			} ]
		});
	}


	function editFun(id) {
        parent.$.modalDialog({
            title : '修改机构信息',
            width : 330,
            height : 520,
            href : '${path }/sys/hospital/editPage.action?id=' + id,
            buttons : [ {
                text : '提交',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#hospitalEditForm');
                    f.submit();
                }
            },{
                text : '关闭',
                handler : function() {
                	parent.$.modalDialog.handler.dialog('destroy');
					parent.$.modalDialog.handler = undefined;
                }
            }  ]
        });
    }
	
	function addFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 330,
            height : 520,
            href : '${path }/sys/hospital/addPage.action',
            buttons : [ {
                text : '提交',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#hospitalAddForm');
                    f.submit();
                }
            },{
                text : '关闭',
                handler : function() {
                	parent.$.modalDialog.handler.dialog('destroy');
					parent.$.modalDialog.handler = undefined;
                }
            }  ]
        });
    }
	
	function deleteFun(id,name) {
        parent.$.messager.confirm('询问', '您是否要删除<font color=\'red\'> ' + name
				+ '</font>机构？', function(b) {
            if (b) {
                    progressLoad();
                    $.post('${path }/sys/hospital/delete.action?id='+id, {  
						}, function(result) {
                        if (result.code === 200) {
                        	parent.$.messager.alert('提示', result.message, 'info');
                            dataGrid.datagrid('load');
                        }
                        progressClose();
                    }, 'JSON');
            }
        });
    }
	
	function searchFun() {
		dataGrid.datagrid('load', {
			keyword : encodeURI(document.getElementById("keyword").value)
		});
	}

	function cleanFun() {
		$('#searchForm input').val('');
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
								<!-- <td><input name="type" type="hidden " value="1"></td> -->
								<td><input name="keyword" type="text" id="keyword"
									class="easyui-validatebox" value=""></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'icon-search',plain:true"
									onclick="searchFun();">搜索</a><a href="javascript:void(0);"
									class="easyui-linkbutton"
									data-options="iconCls:'icon-cancel',plain:true"
									onclick="cleanFun();">清空</a></td>
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

	<div id="toolbar" style="display: none;">
		<a onclick="addFun();" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'icon-add'">新增</a>
	<!-- 	<a
			onclick="importFun();" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'icon-add'">导入</a> -->
	</div>
</body>
</html>