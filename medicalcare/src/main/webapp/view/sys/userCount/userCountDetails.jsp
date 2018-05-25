<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat,java.util.Date" %>
<%@ include file="/commons/global.jsp" %>
<%
SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
String date= format.format(new Date());
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>统计医疗机构每个用户使用次数</title>
    <script type="text/javascript">
    var hospitalId = '${hospitalId}';
    var startTime = '${startTime}';
    var endTime = '${endTime}';
    var dataGrid;
    $(function() {
        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/sys/systemUserCount/getUserCountDetails.action?hospitalId='+hospitalId + '&startTime='+startTime + '&endTime='+endTime,
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : false, //关闭分页
            singleSelect : true,
            idField : 'id',
            sortOrder : 'asc',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ], 
            columns : [ [ {
                width : '180',
                title : '所属机构',
                field : 'hospital',
                sortable : true
            },{
                width : '180',
                title : '用户手机号码',
                field : 'fromUserPhone',
                sortable : true
            },{
                width : '180',
                title : '用户姓名',
                field : 'fromUserName',
                sortable : true
            },{
                width : '180',
                title : '使用次数',
                field : 'eachUserCount',
                sortable : true
            },{
                field : 'action',
                title : '操作',
                width : "120",
                formatter : function(value, row, index) {
                	/* var str = '';
                	str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-search" data-options="plain:true,iconCls:\'icon-search\'" onclick="details(\'{0}\')" >详情</a>', row.fromUserPhone);
                	return str; */
                	var title = '用户使用详情';
                	var url = '';
                	url += '${path }/sys/systemUserCount/gotoUseDetails.action?patientPhone=' + row.fromUserPhone
    				+ '&hospitalId=' + hospitalId + '&startTime=' + startTime + '&endTime=' + endTime;
                	var icon = 'menu_icon_datadeal';
                	return '<a href="#" onclick="self.parent.addTab(\'' + title + '\',\'' + url + '\',\'' + icon + '\')">详情</a>';
                }
            }] ],
            onLoadSuccess:function(data){
            	$('.user-easyui-linkbutton-search').linkbutton({text:'详情',plain:true,iconCls:'icon-search'});
            },
            toolbar : '#toolbar'
        });
    });
    
    /* function details(fromUserPhone) {
    	$.modalDialog({
			title : '查看详情',
			width : 960,
			height : 520,
			href : '${path }/sys/systemUserCount/gotoUseDetails.action?patientPhone=' + fromUserPhone
				+ '&hospitalId=' + hospitalId + '&startTime=' + startTime + '&endTime=' + endTime,
			buttons : [ {
				text : '关闭',
				handler : function() {
					parent.$.modalDialog.handler.dialog('destroy');
					parent.$.modalDialog.handler = undefined;
				}
			} ]
		});
	} */
    
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 55px; overflow: hidden;background-color: #fff">
        <form id="searchForm">
            <table>
                <tr>
                	<th>所属医疗机构:</th>
                    <td><input type="text" id="hospital" name="hospital" readonly="readonly" value="${hospital}" /></td>
                    <th></th>
                    
                    <th>开始日期:</th>
                    <td><input type="text" id="startTime" name="startTime" readonly="readonly" value="${startTime}" /></td>
                    <th></th>
                    
                    <th>结束日期:</th>
                    <td><input type="text" id="endTime" name="endTime" readonly="readonly" value="${endTime}" /></td>
                    <th></th>
                </tr>
            </table>
        </form>
    </div>
    
    <div data-options="region:'center',border:true,title:'用户列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
    
    <div id="toolbar" style="display: none;">
    	<a href="${path}/sys/systemUserCount/exportUserCountDetails.action?hospitalId=${hospitalId}&startTime=${startTime}&endTime=${endTime}" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-redo'">导出Excel</a>
    </div>
</body>
</html>