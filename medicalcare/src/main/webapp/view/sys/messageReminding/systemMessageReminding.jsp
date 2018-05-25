<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>患者端消息通知管理</title>
    <script type="text/javascript">

    var dataGrid;
    var organizationTree;

    $(function() {

        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/sys/systemMessageReminding/findPage.action',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            userName : 'createTime',
            sortOrder : 'asc',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '60',
                title : '消息状态',
                field : 'messageStatus',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                        return '未读';
                    case 1:
                        return '已读';
                    }
                }
            }, {
                width : '100',
                title : '手机号',
                field : 'patientPhone',
                sortable : true
            },{
                width : '130',
                title : '患者id',
                field : 'patientLoginId'
            },{
                width : '80',
                title : '患者名字',
                field : 'patientName'
            },{
                width : '80',
                title : '消息名称',
                field : 'messageName'
            },{
                width : '80',
                title : '消息内容',
                field : 'messageContent'
            },{
                width : '80',
                title : '消息链接',
                field : 'messageLink'
            },{
                width : '70',
                title : '按钮名称',
                field : 'messageButton'
            },{
                width : '80',
                title : '图标链接',
                field : 'messageIcon'
            },{
                width : '70',
                title : '消息类型',
                field : 'messageType',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 1:
                        return '视频咨询';
                    case 2:
                        return '健康走';
                    case 3:
                        return '链接';    
                    }
                }
            },{
                width : '130',
                title : '创建时间',
                field : 'createtime',
                formatter : function(value, row, index) {
                	if(value){
                		return datetimeFormat_1(value);
                	}else {
                		return "";
                	}
                }
            }, {
                field : 'action',
                title : '操作',
                width : "120",
                formatter : function(value, row, index) {
                    var str = '';
                   // str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-dispose" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >修改</a>', row.id);
                    str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-delete\'" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){
               // $('.user-easyui-linkbutton-dispose').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
                $('.user-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-del'});
            },
            toolbar : '#toolbar'
        });
    });

	
    function addFun() {
        parent.$.modalDialog({
            title : '发送消息',
            width : 650,
            height : 500,
            href : '${path }/sys/systemMessageReminding/addPage.action',
            buttons : [ {
                text : '发送',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#systemMessageRemindingAddForm');
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
                $.post('${path }/sys/systemMessageReminding/delete.action', {
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
            width : 650,
            height : 500,
            href : '${path }/sys/systemHealthyProducts/editPage.action?id=' + id,
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
    <div data-options="region:'center',border:true,title:'用户列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div id="toolbar" style="display: none;">
        <shiro:hasPermission name="/sys/systemMessageReminding/addPage">
            <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">发送消息</a>
        </shiro:hasPermission>
    </div>
</body>
</html>