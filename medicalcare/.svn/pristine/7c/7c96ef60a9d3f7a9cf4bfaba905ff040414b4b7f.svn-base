<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>视频子集管理</title>
    <script type="text/javascript">
    var dataGrid;
    var p;
    $(function() {
    	var r = window.location.search.substr(1);
    	p = r.split("=");
    	var url='${path }/crm/subset/findMediaSubsetPage.do';
    	if(p.length>0){
    		url ='${path }/crm/subset/findMediaSubsetPage.do?parentMediaId='+p[1]; 
    	}
        dataGrid = $('#dataGrid').datagrid({
            url : url,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '80',
                title : '父视频',
                field : 'as_name'
            } , {
                width : '80',
                title : '视频名',
                field : 'name'
            }, {
                width : '100',
                title : '视频说明',
                field : 'detail'
            } , {
                width : '100',
                title : '视频URL',
                field : 'media_play_url'
            }, {
                width : '100',
                title : '缩略图',
                field : 'thumbnail'
            },{
                width : '100',
                title : '时长',
                field : 'duration',
                sortable : true
            },{
                width : '130',
                title : '发布日期',
                field : 'issue_date',
                formatter : formatterdate,
                sortable : true
            }, {
                width : '100',
                title : '优先级',
                field : 'media_index',
                sortable : true
            }, {
                width : '100',
                title : '状态',
                field : 'status',
                sortable : true
            }, {
                width : '60',
                title : '付费类型',
                field : 'charge_type',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                        return '免费';
                    case 1:
                        return '付费';
                    }
                }
            },{
                width : '130',
                title : '更新时间',
                field : 'update_time',
                formatter : formatterdate,
                sortable : true
            }, {
                field : 'action',
                title : '操作',
                width : 200,
                formatter : function(value, row, index) {
                    var str = '';
                            str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
                        
                            str += '|';
                            str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-del\'" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.role-easyui-linkbutton-edit').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
                $('.role-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-del'});
            },
            toolbar : '#toolbar'
        });
    });
    
    function addFun(id) {
    	if (id == undefined) {
    		var r = window.location.search.substr(1);
        	p = r.split("=");
        	id=p[1];
        } else {
            id=p[1];
        }
        parent.$.modalDialog({
            title : '添加',
            width : 500,
            height : 400,
            href : '${path }/crm/subset/addPage.do?id='+id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#mediaSubsettAddForm');
                    f.submit();
                }
            } ]
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
            title : '编辑',
            width : 500,
            height : 400,
            href : '${path }/crm/subset/editPage.do?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#mediaSubsetEditForm');
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
        parent.$.messager.confirm('询问', '您是否要删除当前频道？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path }/crm/subset/delete.do', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        dataGrid.datagrid('reload');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }
    function formatterdate(val, row) {
	   	if (val != null) {
		   	var date = new Date(val);
		   	var hours = '';
		   	var minutes = '';
		   	var seconds = '';
		   	if(date.getHours()<10){
		   		hours="0"+date.getHours();
		   	}else{
		   		hours=date.getHours();
		   	}
		   	if(date.getMinutes()<10){
		   		minutes="0"+date.getMinutes();
		   	}else{
		   		minutes=date.getMinutes();
		   	}
		   	if(date.getSeconds()<10){
		   		seconds="0"+date.getSeconds();
		   	}else{
		   		seconds=date.getSeconds();
		   	}
		   	return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
		   	+ date.getDate()+" "+hours+":"+minutes+":"+seconds;
	   	}
   	}
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div id="toolbar" style="display: none;">
            <a onclick="addFun('{0}');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
    </div>
</body>
</html>