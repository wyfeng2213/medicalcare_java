<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医生服务记录</title>
    <script type="text/javascript">

    var dataGrid;
    $(function() {

        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/sys/doctorsOrders/findOrdersPage.action',
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
                width : '130',
                title : '服务类型',
                field : 'ordersType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 1:
                        return '报告解读服务';
                    case 2:
                        return '快速问诊服务';
                    case 3:
                        return '图文咨询服务';
                    case 4:
                        return '语音咨询服务';
                    case 5:
                        return '视频问诊服务';
                    }
                }
            }, {
                width : '130',
                title : '患者姓名',
                field : 'patientName',
                sortable : true
            },{
                width : '130',
                title : '患者电话',
                field : 'patientPhone',
                sortable : true
            },{
                width : '130',
                title : '医生姓名',
                field : 'doctorName'
            },{
                width : '130',
                title : '医生电话',
                field : 'doctorPhone',
                sortable : true
            },  {
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
            }/*  , {
                field : 'action',
                title : '操作',
                width : 130,
                formatter : function(value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-search" data-options="plain:true,iconCls:\'icon-search\'" onclick="details(\'{0}\')" >详情</a>', row.id);
                    return str;
                }
            } */] ],
            onLoadSuccess:function(data){
            	$('.user-easyui-linkbutton-search').linkbutton({text:'详情',plain:true,iconCls:'icon-search'});
            },
            toolbar : '#toolbar'
        });
    });
    
    function details(id){
    	parent.$.modalDialog({
            title : '查看医生服务详情',
            width : 500,
            height : 450,
            href : '${path }/sys/doctorsOrders/queryOrdersDetails.action?id=' + id,
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
        	hospital : encodeURI(document.getElementById("hospital").value),
        	doctorName : encodeURI(document.getElementById("doctorName").value),
		});
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
                    <td>所属医疗机构：</td>
                    <td><input name="hospital" type="text" id="hospital" class="easyui-validatebox" value=""></td>
                    <td>医生姓名：</td>
                    <td><input name="doctorName" type="text" id="doctorName" class="easyui-validatebox" value=""></td>
                    <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'医生服务记录'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
</body>
</html>