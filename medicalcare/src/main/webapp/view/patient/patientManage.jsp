<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>冻结患者管理</title>
    <script type="text/javascript">

    var dataGrid;
    $(function() {
        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/sys/patientManage/findUserPage.action',
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
            columns : [[{
                width : '150',
                title : '患者ID',
                field : 'patientLoginId',
                sortable : true
            },{
                width : '100',
                title : '姓名',
                field : 'patientName',
                sortable : true
            },{
                width : '100',
                title : '手机号',
                field : 'patientPhone'
            },{
                width : '150',
                title : '登录时间',
                field : 'loginTime',
                formatter : function(value, row, index) {
                	if(value){
                		return datetimeFormat_1(value);
                	}else {
                		return "";
                	}
                }
            },{
                width : '150',
                title : '上次登录时间',
                field : 'lastLoginTime',
                formatter : function(value, row, index) {
                	if(value){
                		return datetimeFormat_1(value);
                	}else {
                		return "";
                	}
                }
            },{
                width : '130',
                title : '登录IP',
                field : 'ip'
            },{
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
                width : 80,
                formatter : function(value, row, index) {
                	var str = '';
                    if(row.isEnable==1){
                    	str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-cancel" data-options="plain:true,iconCls:\'icon-del\'" onclick="forbiddenOrEnableFun(\'{0}\',0);" >禁用</a>', row.id);
                    } else {
                    	str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-cancel" data-options="plain:true,iconCls:\'icon-del\'" onclick="forbiddenOrEnableFun(\'{0}\',1);" >启用</a>', row.id);
                    }
                	return str;
               }
            }]],
            onLoadSuccess:function(data){
            	$('.user-easyui-linkbutton-cancel').linkbutton({plain:true,iconCls:'icon-cancel'});
            },
            toolbar : '#toolbar'
        });
    });

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
                    $.post('${path }/sys/patientManage/forbiddenOrEnable.action', {
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
                    <td><input name="patientName" placeholder="请输入姓名"/></td>
                    <th>电话:</th>
                    <td><input name="patientPhone" placeholder="请输入电话"/></td>
                     <th></th>
                    <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
                	</td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'患者列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
</body>
</html>