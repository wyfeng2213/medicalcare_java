<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>敏感词管理</title>
    <script type="text/javascript">

    var dataGrid;
    $(function() {

        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/sys/sysSensitiveWord/findPage.action',
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
                width : '130',
                title : '词语',
                field : 'text',
                sortable : true
            },{
                width : '130',
                title : '等级',
                field : 'level',
                sortable : true
            },{
                width : '130',
                title : '类型',
                field : 'type',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 1:
                        return '内容过滤';
                    case 2:
                        return '通信诈骗';
                    }
                }
            },{
                width : '160',
                title : '创建时间',
                field : 'createtime',
                formatter : function(value, row, index) {
                	if(value){
                		return datetimeFormat_1(value);
                	}else {
                		return "";
                	}
                }
            },  {
                width : '160',
                title : '更新时间',
                field : 'updatetime',
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
                    str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-dispose" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >修改</a>', row.id);
                    str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-delete\'" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){
                $('.user-easyui-linkbutton-dispose').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
                $('.user-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-del'});
            },
            toolbar : '#toolbar'
        });
    });

	
    function addFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 650,
            height : 500,
            href : '${path }/sys/sysSensitiveWord/addPage.action',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#sensitiveWordAddForm');
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
                $.post('${path }/sys/sysSensitiveWord/delete.action', {
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
            href : '${path }/sys/sysSensitiveWord/editPage.action?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#sensitiveWordEditForm');
                    f.submit();
                }
            } ]
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
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="searchForm">
            <table>
                <tr>
                    <td>
                    <input name="keyword" type="text" id="keyword" class="easyui-validatebox" value="">
                    </td>
                    <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'敏感词列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div id="toolbar" style="display: none;">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
    </div>
</body>
</html>