<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>问诊日志</title>
    <script type="text/javascript">

    var dataGrid;
    $(function() {

        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/sys/inquiryLog/findPage.action',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            userName : 'id',
            sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '100',
                title : '发送用户名字',
                field : 'fromUserName',
                sortable : true
            },{
                width : '100',
                title : '发送用户手机号',
                field : 'fromUserPhone',
                sortable : true
            },{
                width : '80',
                title : '发送用户类型',
                field : 'fromUserType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 1:
                        return '医生';
                    case 2:
                        return '患者';
                    case 3:
                        return '秘书';
                    }
                }
            },{
                width : '100',
                title : '接收用户名字',
                field : 'toUserName',
                sortable : true
            },{
                width : '100',
                title : '接收用户手机号',
                field : 'toUserPhone',
                sortable : true
            },{
                width : '80',
                title : '接收用户类型',
                field : 'toUserType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 1:
                        return '医生';
                    case 2:
                        return '患者';
                    case 3:
                        return '秘书';
                    case 4:
                        return '聊天室';
                    }
                }
            },{
                width : '200',
                title : '消息',
                field : 'messagecontent',
                sortable : true
            },{
                width : '80',
                title : '消息类型',
                field : 'messageType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 1:
                        return '文字';
                    case 2:
                        return '图片';
                    case 3:
                        return '语音';
                    case 4:
                        return '视频';
                    case 5:
                        return '问诊表';
                    case 6:
                        return '随访计划';
                    }
                }
            },{
                width : '160',
                title : '发送时间',
                field : 'sendtime',
                formatter : function(value, row, index) {
                	if(value){
                		return datetimeFormat_1(value);
                	}else {
                		return "";
                	}
                }
            },{
                field : 'action',
                title : '操作',
                width : "120",
                formatter : function(value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-search" data-options="plain:true,iconCls:\'icon-search\'" onclick="query(\'{0}\')" >详情</a>', row.id);
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){
                $('.user-easyui-linkbutton-search').linkbutton({text:'详情',plain:true,iconCls:'icon-search'});
            },
            toolbar : '#toolbar'
        });
    });
    
    function query(id) {
		parent.$.modalDialog({
			title : '查看详情',
			width : 500,
			height : 520,
			href : '${path }/sys/inquiryLog/queryPage.action?id=' + id,
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
		dataGrid.datagrid('load', {
			userName : encodeURI(document.getElementById("userName").value),
			userPhone : encodeURI(document.getElementById("userPhone").value),
		});
	}
    
    function cleanFun() {
        $('#searchForm input').val('');
        dataGrid.datagrid('load', {});
    }
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="searchForm">
            <table>
                <tr>
                    <td>用户名：</td>
                    <td><input name="userName" type="text" id="userName" class="easyui-validatebox" value=""></td>
                    <td>电话：</td>
                    <td><input name="userPhone" type="text" id="userPhone" class="easyui-validatebox" value=""></td>
                    <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'问诊日志列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
</body>
</html>