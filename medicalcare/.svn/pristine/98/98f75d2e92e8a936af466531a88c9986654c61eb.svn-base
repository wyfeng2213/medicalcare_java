<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>	
<%@ page import="com.cmcc.medicalcare.model.SystemUser" %>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医生档案管理</title>

    <script type="text/javascript">

    var dataGrid;

    $(function() {
        //alert(hospitalId);

        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/sys/healthStep/findUserPage.action',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            sortName : 'id',
            sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
				width : '40',
				title : 'id',
				field : 'id',
				sortable : true
			},{
                width : '100',
                title : '姓名',
                field : 'userName',
                sortable : true
            },{
                width : '100',
                title : '手机号',
                field : 'userPhone',
                sortable : true
            },
            {
                width : '80',
                title : '上一次健康币',
                field : 'lcoins',
                sortable : true
            },
            {
                width : '70',
                title : '本次健康币',
                field : 'mcoins',
                sortable : true
            },
            {
                width : '40',
                title : '差价',
                field : 'gcoins',
                sortable : true
            },
            {
                width : '100',
                title : '收支明细',
                field : 'detail',
                sortable : true
            },{
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
			},{
				width : '130',
				title : '更新时间',
				field : 'updatetime',
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
			}, {
                field : 'action',
                title : '操作',
                width : 130,
                formatter : function(value, row, index) {
                    var str = '';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >修改</a>', row.id);
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-del\'" onclick="deleteFun(\'{0}\',\'{1}\');" >删除</a>', row.id, row.userName);
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){
                $('.user-easyui-linkbutton-edit').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
                $('.user-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-del'});
            },
            toolbar : '#toolbar'
        });

    });

/* 
    function query(id){
    	parent.$.modalDialog({
            title : '查看医生服务设置详情',
            width : 800,
            height : 500,
            href : '${path }/sys/healthStep/queryPage.action?id=' + id,
            buttons : [ {
                text : '关闭',
                handler : function() {
                	parent.$.modalDialog.handler.dialog('destroy');
            		parent.$.modalDialog.handler = undefined;
                }
            } ]		
        });
    }
     */
    
    function deleteFun(id,name) {
        parent.$.messager.confirm('询问', '您是否要删除<font color=\'red\'> ' + name
				+ '</font>？', function(b) {
            if (b) {
                    progressLoad();
                    $.post('${path }/sys/healthStep/delete.action?id='+id, {  
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
    
    function editFun(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '修改健康走信息',
            width : 400,
            height :300,
            href : '${path }/sys/healthStep/editPage.action?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#healthStepEditForm');
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
        dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    }
    function cleanFun() {
        $('#searchForm input').val('');
        dataGrid.datagrid('load', {});
    }
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 55px; overflow: hidden;background-color: #fff">
        <form id="searchForm">
            <table>
                <tr>
                    <th>姓名:</th>
                    <td><input name="userName" placeholder="请输入姓名"/></td>
                    <th>电话:</th>
                    <td><input name="userPhone" placeholder="请输入电话"/></td>
                    <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
                	</td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'患者健康走列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>

    <div id="toolbar" style="display: none;">     
    </div>
</body>
</html>