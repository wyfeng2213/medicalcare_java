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

    var dataGrid;
    var organizationTree;

    $(function() {

        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/sys/systemUser/findUserPage.action',
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
                width : '120',
                title : '用户名',
                field : 'userName',
                sortable : true
            }, {
                width : '120',
                title : '姓名',
                field : 'userRealname',
                sortable : true
            },{
                width : '120',
                title : '角色',
                field : 'userRole',
                formatter : function(value, row, index) {
               	    var htmlObj = $.ajax({  
                       url : '${path }/sys/systemUser/queryRoleById.action',  
                       type : "post",  
                       async : false,  
                       data : {id:value}  
                    });  
                	var text = htmlObj.responseText;  
                    var json = JSON.parse(text);  
                    return json.name;
                    
                    /* switch (value) {
                    case 1:
                        return '一级管理员';
                    case 2:
                        return '二级管理员';
                    case 3:
                        return '三级管理员';
                    case 4:
                        return '医疗机构管理员';    
                    } */
              }
            }, {
                width : '120',
                title : '医疗机构组织',
                field : 'hospitalName',
                sortable : true
            },{
                width : '120',
                title : '联系方式',
                field : 'userTel'
            },{
                width : '130s',
                title : '创建者',
                field : 'createUser',
                sortable : true
            },  {
                width : '60',
                title : '状态',
                field : 'isEnable',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                        return '已禁用';
                    case 1:
                        return '已启用';
                    }
                }
            },{
                field : 'action',
                title : '操作',
                width : 400,
                formatter : function(value, row, index) {
                    var str = '';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-dispose" data-options="plain:true,iconCls:\'icon-edit\'" onclick="assignRoleFun(\'{0}\');" >角色分配</a>', row.id);
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-reset" data-options="plain:true,iconCls:\'icon-edit\'" onclick="resetPwdFun(\'{0}\');" >重置密码</a>', row.id);
	                        str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
	                        if(row.isEnable==1){
	                        	str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-cancel" data-options="plain:true,iconCls:\'icon-del\'" onclick="forbiddenOrEnableFun(\'{0}\',0);" >禁用</a>', row.id);
	                        } else {
	                        	str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-cancel" data-options="plain:true,iconCls:\'icon-del\'" onclick="forbiddenOrEnableFun(\'{0}\',1);" >启用</a>', row.id);
	                        };
	                        str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
	                        str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-del\'" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){
                $('.user-easyui-linkbutton-dispose').linkbutton({text:'角色分配',plain:true,iconCls:'icon-man'});
                $('.user-easyui-linkbutton-reset').linkbutton({text:'重置密码',plain:true,iconCls:'icon-reload'});
                $('.user-easyui-linkbutton-cancel').linkbutton({plain:true,iconCls:'icon-cancel'});
                $('.user-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-del'});
            },
            toolbar : '#toolbar'
        });
    });

	
    function addFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 560,
            height : 280,
            href : '${path }/sys/systemUser/addPage.action',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#systemuserAddForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function forbiddenOrEnableFun(id,isEnable) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        var isEnablemrg;
        if(isEnable==1){
        	isEnablemrg="启用";
        }else{
        	isEnablemrg="禁止";
        }
        parent.$.messager.confirm('询问', '您是否要'+isEnablemrg+'当前用户？', function(b) {
            if (b) {
                var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
                if (currentUserId != id) {
                    progressLoad();
                    $.post('${path }/sys/systemUser/forbiddenOrEnable.action', {
                        id : id,
                        isEnable : isEnable
                    }, function(result) {
                        if (result.code==200) {
                            parent.$.messager.alert('提示', result.message, 'info');
                            dataGrid.datagrid('reload');
                        }
                        progressClose();
                    }, 'JSON');
                } else {
                    parent.$.messager.show({
                        title : '提示',
                        msg : '不可以操作自己！'
                    });
                }
            }
        });
    }
    
    function resetPwdFun(id) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要为当前用户重置密码？', function(b) {
            if (b) {
                var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
                if (currentUserId != id) {
                    progressLoad();
                    $.post('${path }/sys/systemUser/resetPwdFun.action', {
                        id : id
                    }, function(result) {
                        if (result.code==200) {
                            parent.$.messager.alert('提示', result.message, 'info');
                            dataGrid.datagrid('reload');
                        }
                        progressClose();
                    }, 'JSON');
                } else {
                    parent.$.messager.show({
                        title : '提示',
                        msg : '不可以操作自己！'
                    });
                }
            }
        });
    }
    
    function deleteFun(id) {
        parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
            if (b) {
                var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
                if (currentUserId != id) {
                    progressLoad();
                    $.post('${path }/sys/systemUser/delete.action', {
                        id : id
                    }, function(result) {
                        if (result.code == 200) {
                            parent.$.messager.alert('提示', result.message, 'info');
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
    
    function assignRoleFun(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '角色分配',
            width : 500,
            height : 300,
            href : '${path }/sys/systemUser/assignRolePage.action?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#assignRolePageForm');
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
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
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
    </div>
    <div data-options="region:'center',border:true,title:'用户列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div id="toolbar" style="display: none;">
        <shiro:hasPermission name="/sys/systemUser/addPage">
            <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
        </shiro:hasPermission>
    </div>
</body>
</html>