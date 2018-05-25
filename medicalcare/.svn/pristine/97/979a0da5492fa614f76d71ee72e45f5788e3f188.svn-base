<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp"%>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医生注册审核</title>
<script type="text/javascript">
	var dataGrid;

	$(function() {
		$(document).ready(function() {
			$("#columnId").combobox({
				onChange : function(n, o) {
					searchFun();
				}
			});
		});

		dataGrid = $('#dataGrid')
				.datagrid(
						{
							url : '${path }/sys/scienceArticle/findUserPage.action',
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
										width : '230',
										title : '标题',
										field : 'title',
										sortable : true
									},
									{
										width : '130',
										title : '来源',
										field : 'source',
										sortable : true
									},
									{
										field : 'action',
										title : '操作',
										width : 355,
										formatter : function(value, row, index) {
											var str = '';
											str += $
													.formatString(
															'<a href="javascript:void(0)" class="user-easyui-linkbutton-search" data-options="plain:true,iconCls:\'icon-search\'" onclick="query(\'{0}\')" >详情</a>',
															row.id);

											str += '&nbsp;&nbsp;|&nbsp;&nbsp;';

											str += $
													.formatString(
															'<a href="javascript:void(0)" class="user-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\')" >修改</a>',
															row.id);

											str += '&nbsp;&nbsp;|&nbsp;&nbsp;';

											str += $
													.formatString(
															'<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-del\'" onclick="delFun(\'{0}\',\'{1}\')" >删除</a>',
															row.id,row.title);

											str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
											
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
								$('.user-easyui-linkbutton-search').linkbutton(
										{
											text : '详情',
											plain : true,
											iconCls : 'icon-search'
										});
								$('.user-easyui-linkbutton-edit').linkbutton({
									text : '修改',
									plain : true,
									iconCls : 'icon-edit'
								});
								$('.user-easyui-linkbutton-del').linkbutton({
									text : '删除',
									plain : true,
									iconCls : 'icon-no'
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
				$.post('${path }/sys/scienceArticle/reject.action?id=' + id, {
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
				$.post('${path }/sys/scienceArticle/pass.action?id=' + id,{
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
            title : '添加文章',
            width : 500,
            height : 320,
            href : '${path }/sys/scienceArticle/addPage.action',
            buttons : [ {
                text : '提交',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#scienceArticleAddForm');
                    f.submit();
                }
            },{
                text : '关闭',
                handler : function() {
                	parent.$.modalDialog.handler.dialog('destroy');
					parent.$.modalDialog.handler = undefined;
                }
            } ]
        });
    }
	
	function query(id) {
		parent.$.modalDialog({
			title : '查看文章详情',
			width : 500,
			height : 320,
			href : '${path }/sys/scienceArticle/queryPage.action?id=' + id,
			buttons : [ {
				text : '关闭',
				handler : function() {
					parent.$.modalDialog.handler.dialog('destroy');
					parent.$.modalDialog.handler = undefined;
				}
			} ]
		});
	}


	function delFun(id, name) {
		parent.$.messager.confirm('询问', '您是否要同意删除<font color=\'red\'> ' + name
				+ '</font> ？', function(b) {
			if (b) {
				progressLoad();
				$.post('${path }/sys/scienceArticle/delete.action?id=' + id, {},
						function(result) {
							if (result.code === 200) {
								parent.$.messager.alert('提示', result.message,
										'info');
								dataGrid.datagrid('load', $
										.serializeObject($('#searchForm')));
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
            title : '修改文章',
            width : 500,
            height : 450,
            href : '${path }/sys/scienceArticle/editPage.action?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#scienceArticleEditForm');
                    f.submit();
                }
            },{
                text : '关闭',
                handler : function() {
                	parent.$.modalDialog.handler.dialog('destroy');
					parent.$.modalDialog.handler = undefined;
                }
            }]
        });
    }
	

	function searchFun() {
		document.getElementById("keyword").value = '';
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}

	function searchFun_() {
		dataGrid.datagrid('load', {
			keyword : encodeURI(document.getElementById("keyword").value)
		});
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
								<th>标题栏:</th>
								<td><select id="columnId" name="columnId"
									class="easyui-combobox"
									data-options="width:140,height:29,editable:false,panelHeight:'auto'">
										<option value="0" selected='selected'>全部</option>
										<c:forEach var="item" items="${scienceColumnList}" varStatus="status">
         								<option value="${item.id}">${item.columnHeading}</option>
										</c:forEach>
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
									onclick="searchFun_();">查询</a><a href="javascript:void(0);"
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

	<div data-options="region:'center',border:true,title:'标题栏列表'">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>

	<div id="toolbar" style="display: none;">     
            <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加文章</a> 
    </div>
</body>
</html>