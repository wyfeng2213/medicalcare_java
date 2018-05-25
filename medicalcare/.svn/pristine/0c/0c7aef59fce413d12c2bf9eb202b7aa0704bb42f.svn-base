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
<title>统计活跃用户数及活跃总次数</title>
    <script type="text/javascript">
    var dataGrid;
    $(function() {
        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/sys/systemUserCount/getUserCount.action',
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
                title : '活跃用户数',
                field : 'userCount',
                sortable : true
            },{
                width : '180',
                title : '使用总次数',
                field : 'totalCount',
                sortable : true
            },{
                field : 'action',
                title : '操作',
                width : "120",
                formatter : function(value, row, index) {
                	/* var str = '';
                	str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-search" data-options="plain:true,iconCls:\'icon-search\'" onclick="details(\'{0}\')" >详情</a>', row.hospitalId);
                	return str; */ 
                	var title = '统计医疗机构每个用户使用次数';
                	var url = '';
                	url += '${path }/sys/systemUserCount/gotoUserCountDetails.action?hospitalId=' + row.hospitalId 
							+ '&startTime=' + document.getElementById("startTime").value 
							+ '&endTime=' + document.getElementById("endTime").value;
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
    
    /* function details(hospitalId) {
    	parent.$.modalDialog({
			title : '查看详情',
			width : 960,
			height : 520,
			href : '${path }/sys/systemUserCount/gotoUserCountDetails.action?hospitalId=' + hospitalId 
					+ '&startTime=' + document.getElementById("startTime").value 
					+ '&endTime=' + document.getElementById("endTime").value,
			buttons : [ {
				text : '关闭',
				handler : function() {
					parent.$.modalDialog.handler.dialog('destroy');
					parent.$.modalDialog.handler = undefined;
				}
			} ]
		});
	} */
    
    function exportToExcel() {
    	var hospitalId = $('#hospital').combobox('getValue');
    	var startTime = document.getElementById("startTime").value;
    	var endTime = document.getElementById("endTime").value;
    	document.location.href= '${path }/sys/systemUserCount/exportUserCount.action?hospitalId=' + hospitalId + '&startTime=' + startTime + '&endTime=' + endTime;
    }
    
    function searchFun() {
    	dataGrid.datagrid('load', {
    		hospitalId : $('#hospital').combobox('getValue'),
    		startTime : document.getElementById("startTime").value,
    		endTime : document.getElementById("endTime").value,
		});
    }
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 55px; overflow: hidden;background-color: #fff">
        <form id="searchForm">
            <table>
                <tr>
                	<th>所属医疗机构:</th>
                    <td>
	                    <select id="hospital" name="hospital" class="easyui-combobox" data-options="width:150,height:29,editable:false" data-options="required:true">
	                        <option value="-1">全部</option>
	                        <c:forEach var="doctorsTeam" items="${doctorsTeamList}" varStatus="status">
								<option value="${doctorsTeam.id}" >${doctorsTeam.name}</option>
							</c:forEach>
	                    </select>
                    </td>
                    <th></th>
                    
                    <th>开始日期:</th>
                    <td><input type="text" id="startTime" name="startTime" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="readonly" value="<%=date%>" /></td>
                    <th></th>
                    
                    <th>结束日期:</th>
                    <td><input type="text" id="endTime" name="endTime" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="readonly" value="<%=date%>" /></td>
                    <th></th>
                    
                    <td>
                    	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询统计</a>
                	</td>
                </tr>
            </table>
        </form>
    </div>
    
    <div data-options="region:'center',border:true,title:'用户列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
    
    <div id="toolbar" style="display: none;">
    	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-redo'" onclick="exportToExcel();">导出Excel</a>
    </div>
</body>
</html>