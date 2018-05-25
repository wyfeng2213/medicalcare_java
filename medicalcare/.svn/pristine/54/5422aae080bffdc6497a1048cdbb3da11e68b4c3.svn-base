<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>敏感词记录日志管理</title>
    <script type="text/javascript">

    var dataGrid;
    $(function() {

        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/sys/sysSensitiveRecordLog/findPage.action',
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
                width : '85',
                title : '发送者电话',
                field : 'userPhone',
                sortable : true
            },{
                width : '100',
                title : '发送者名字',
                field : 'userName',
                sortable : true
            },{
                width : '135',
                title : '发送者ID',
                field : 'userLoginid',
                sortable : true
            },{
                width : '80',
                title : '敏感词语',
                field : 'text',
                sortable : true
            },{
                width : '50',
                title : '等级',
                field : 'level',
                sortable : true
            },{
                width : '60',
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
                width : '85',
                title : '接收者电话',
                field : 'toUserPhone',
                sortable : true
            },{
                width : '100',
                title : '接收者名字',
                field : 'toUserName',
                sortable : true
            },{
                width : '135',
                title : '接收者ID',
                field : 'toUserLoginid',
                sortable : true
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
            },  {
                width : '130',
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
                width : "80",
                formatter : function(value, row, index) {
                    var str = '';
                  /*   str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-dispose" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >修改</a>', row.id);
                    str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-delete\'" onclick="deleteFun(\'{0}\');" >删除</a>', row.id); */
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){
               /*  $('.user-easyui-linkbutton-dispose').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
                $('.user-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-del'}); */
            },
            toolbar : '#toolbar'
        });
    });
    
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
                	<td>发送者电话:</td>
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
    <div data-options="region:'center',border:true,title:'敏感词记录日志列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>

</body>
</html>