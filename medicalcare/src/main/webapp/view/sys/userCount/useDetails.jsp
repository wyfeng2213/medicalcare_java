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
<title>用户使用详情</title>
    <script type="text/javascript">
    var hospitalId = '${hospitalId}';
    var patientPhone = '${patientPhone}';
    var startTime = '${startTime}';
    var endTime = '${endTime}';
    var dataGrid;
    $(function() {
        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/sys/systemUserCount/getUseDetails.action?hospitalId='+hospitalId + '&patientPhone='+patientPhone + '&startTime='+startTime + '&endTime='+endTime,
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
                width : '130',
                title : '用户手机号码',
                field : 'fromUserPhone',
                sortable : true
            },{
                width : '130',
                title : '用户姓名',
                field : 'fromUserName',
                sortable : true
            },{
                width : '130',
                title : '医生手机号码',
                field : 'toUserPhone',
                sortable : true
            },{
                width : '130',
                title : '医生姓名',
                field : 'toUserName',
                sortable : true
            },{
                width : '230',
                title : '消息内容',
                field : 'messagecontent',
                sortable : true
            },{
                width : '180',
                title : '发送时间',
                field : 'createtime',
                formatter : function(value, row, index) {
                	if(value){
                		return datetimeFormat_1(value);
                	}else {
                		return "";
                	}
                }
            }] ],
            toolbar : '#toolbar'
        });
    });
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
                    
                    <th>用户手机号码:</th>
                    <td><input type="text" id="patientPhone" name="patientPhone" readonly="readonly" value="${patientPhone}" /></td>
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
    	<a href="${path}/sys/systemUserCount/exportUseDetails.action?hospitalId=${hospitalId}&patientPhone=${patientPhone}&startTime=${startTime}&endTime=${endTime}" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-redo'">导出Excel</a>
    </div>
</body>
</html>