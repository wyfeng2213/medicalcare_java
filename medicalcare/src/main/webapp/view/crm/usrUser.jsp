<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
    <script type="text/javascript">

	var map = new Map();
	//为map添加值；key:对应filed="userName"中的字段名；value:对应数据库的字段
	map['countryCode']='country_code';
	map['appType']='app_type';
	map['updateTime']='update_time';
	onSortColumn=function(param,map){
		//取出map中字段的映射关系值  
		var fieldSort=map[param.sort];
		if(fieldSort!='' && fieldSort!=undefined){
			//设置新的排序字段名，设置完之后，发送请求时一并会发送到服务端
			param.sort=fieldSort;
		}
	}

    var dataGrid;
    $(function() {
        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/crm/user/findCrmUserPage.do',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '100',
                title : ' 帐户ID',
                field : 'accountid',
				sortable : true
            }, {
                width : '80',
                title : '用户名',
                field : 'username',
				sortable : true
            }, {
                width : '80',
                title : '昵称',
                field : 'nickname',
				sortable : true
            } ,{
                width : '80',
                title : '国家区号',
                field : 'countryCode',
				sortable : true
            },{
                width : '80',
                title : '电话',
                field : 'mobile',
				sortable : true
            },{
                width : '60',
                title : '终端类型',
                field : 'appType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                        return 'android';
                    case 1:
                        return 'iphone';
                    }
                }
            },{
                width : '80',
                title : '邮箱',
                field : 'email',
				sortable : true
            },{
                width : '40',
                title : '等级',
                field : 'level',
                sortable : true
            },{
                width : '60',
                title : '分数',
                field : 'score',
                sortable : true
            },{
                width : '130',
                title : '更新时间',
                field : 'updateTime',
                formatter : formatterdate,
                sortable : true
            },{
                field : 'action',
                title : '操作',
                width : 400,
                formatter : function(value, row, index) {
                    var str = '';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
                            str += '|';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-del\'" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
                            str += '|';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-edit1" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editUserPwd(\'{0}\');" >密码重置</a>', row.id);
                            if(row.icon){
	                            str += '|';
	                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-search" data-options="plain:true,iconCls:\'icon-search\'" onclick="show(\'{0}\');" >查看头像</a>', row.id);	
                            }
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){
                $('.user-easyui-linkbutton-edit').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
                $('.user-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-del'});
                $('.user-easyui-linkbutton-edit1').linkbutton({text:'密码重置',plain:true,iconCls:'icon-edit'});
                $('.user-easyui-linkbutton-search').linkbutton({text:'查看头像',plain:true,iconCls:'icon-search'});
            },
			onBeforeLoad:function(param){  
				onSortColumn(param,map);  
			},
            toolbar : '#toolbar'
        });
    });
	
    function addFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 300,
            height : 200,
            href : '${path }/crm/user/addPage.do',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#userAddForm');
                    f.submit();
                }
            } ]
        });
    }
    function show(id){
    	parent.$.modalDialog({
            title : '查看头像',
            width : 450,
            height : 300,
            href : '${path }/crm/user/showImg.do?id=' + id
        });
    }
    
    function editUserPwd(id) {
    	parent.$.messager.confirm('询问', '您是否要为当前用户重置密码？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path }/crm/user/editUserPwd.do', {
                    id : id
                }, function(result) {
                	console.log(result.success);
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        dataGrid.datagrid('reload');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }
    function deleteFun(id) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
            if (b) {
                var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
                if (currentUserId != id) {
                    progressLoad();
                    $.post('${path }/crm/user/delete.do', {
                        id : id
                    }, function(result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            dataGrid.datagrid('reload');
                        }
                        progressClose();
                    }, 'JSON');
                } else {
                    parent.$.messager.show({
                        title : '提示',
                        msg : '不可以删除自己！'
                    });
                }
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
            title : '编辑',
            width : 300,
            height : 250,
            href : '${path }/crm/user/editPage.do?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#userEditForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function searchFun() {
    	console.log($.serializeObject($('#searchForm')))
        dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    }
    function cleanFun() {
        $('#searchForm input').val('');
        dataGrid.datagrid('load', {});
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
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="searchForm">
            <table>
                <tr>
                    <th>手机号码:</th>
                    <td><input name="mobile" style="width:120px" placeholder="请输入手机号码"/></td>
                    <th>用户名:</th>
                    <td><input name="username" style="width:120px" placeholder="请输入用户名"/></td>
                    <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'用户列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
     <!--
    <div data-options="region:'west',border:true,split:false,title:'组织机构'"  style="width:150px;overflow: hidden; ">
        <ul id="organizationTree"  style="width:160px;margin: 10px 10px 10px 10px">
        </ul>
    </div>
    -->
    <div id="toolbar">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
    </div>
     
</body>
</html>