<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医生APK管理</title>
    <script type="text/javascript">

    var dataGrid;
    var organizationTree;

    $(function() {

        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/sys/pav/doctorApk/findPage.action',
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
                width : '80',
                title : '应用版本号',
                field : 'hPackageversion',
                sortable : true
            },{
                width : '60',
                title : 'apk版本号',
                field : 'hPackagecode'
            }, {
                width : '320',
                title : '应用包位置',
                field : 'hPackageurl',
                sortable : true
            },{
                width : '70',
                title : '文件大小',
                field : 'hSize',
                formatter : function(value, row, index) {
                	value = value/1024/1024;
                	value = value.toFixed(2);
                    return value+" MB";
                }
            },{
                width : '330',
                title : '下载链接',
                field : 'hServerurl'
            },{
                width : '130s',
                title : '创建时间',
                field : 'hCreatetime',
                sortable : true,
                formatter : function(value, row, index) {
                	return datetimeFormat_1(value);
                }
            },  {
                width : '60',
                title : '状态',
                field : 'hStatus',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                        return '已禁用';
                    case 1:
                        return '已启用';
                    }
                }
            }] ],
            toolbar : '#toolbar'
        });
    });

	
    function addFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 400,
            height : 180,
            href : '${path }/sys/pav/doctorApk/addPage.action',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#doctorApkAddForm');
                    f.submit();
                }
            } ]
        });
    }
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:true,title:'医生APK列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div id="toolbar" style="display: none;">
        <shiro:hasPermission name="/sys/pav/doctorApk/addPage">
            <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
        </shiro:hasPermission>
    </div>
</body>
</html>