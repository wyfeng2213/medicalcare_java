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

		dataGrid = $('#dataGrid')
				.datagrid(
						{
							url : '${path }/sys/mediaLog/findUserPage.action',
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
										width : '60',
										title : '姓名',
										field : 'userName',
										sortable : true
									},
									{
										width : '100',
										title : '电话',
										field : 'userPhone',
										sortable : true
									},
									{
										width : '50',
										title : '后缀',
										field : 'suffix',
										sortable : true
									},
									{
										width : '80',
										title : '大小(字节B)',
										field : 'size',
										sortable : true
									},
									{
										width : '230',
										title : '链接',
										field : 'outerUrl',
										sortable : true,
										formatter : function(value, row, index) {
											var str = '';
											if(value !=''&&value!=null){
												
												str +="<a href=\""+value+"\" target=\"_blank\">链接</a>";
											}
											
											return str;
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
							},
							toolbar : '#toolbar'
						});
	});

	
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
			title : '查看详情',
			width : 500,
			height : 520,
			href : '${path }/sys/mediaLog/queryPage.action?id=' + id,
			buttons : [ {
				text : '关闭',
				handler : function() {
					parent.$.modalDialog.handler.dialog('destroy');
					parent.$.modalDialog.handler = undefined;
				}
			} ]
		});
	}


	
	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
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
<!-- 				<td width="600"></td>
 -->				<td>
					<form id="searchForm">
						<table>
							<tr>
								<td>电话：</td>
								<td><input name="phone" type="text" id="phone"
									class="easyui-validatebox" value=""></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'icon-search',plain:true"
									onclick="searchFun();">查询</a><a href="javascript:void(0);"
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

	<div data-options="region:'center',border:true,title:'列表'">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>

</body>
</html>