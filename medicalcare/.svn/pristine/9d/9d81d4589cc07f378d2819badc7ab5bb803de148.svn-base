<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat,java.util.Date" %>
<%@ include file="/commons/global.jsp" %>
<%
SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
String date= format.format(new Date());
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请求日志</title>
	<script type="text/javascript">
	    var dataGrid;
	    $(function () {
	        dataGrid = $('#dataGrid').datagrid({
	            url: '${path }/sys/systemRequestLog/findSysLogPage.action',
	            fit: true,
	            striped: true,
	            pagination: true,
	            singleSelect: true,
	            idField: 'id',
	            pageSize: 20,
	            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
	            columns: [[{
	                width: '80',
	                title: '用户名',
	                field: 'userName',
	                sortable: true
	            }, {
	                width: '100',
	                title: '电话号码',
	                field: 'userPhone'
	            }, {
	                width: '80',
	                title: '用户类型',
	                field: 'userType'
	            }, {
	                width: '150',
	                title: '请求名称',
	                field: 'acctionName',
	                sortable: true
	            }, {
	                width: '150',
	                title: '类名',
	                field: 'className'
	            }, {
	                width: '130',
	                title: '方法',
	                field: 'methodName'
	            }, {
	                width: '100',
	                title: '地址',
	                field: 'url'
	            }, {
	                width: '100',
	                title: '请求输入',
	                field: 'request'
	            }, {
	                width: '100',
	                title: '请求输出',
	                field: 'response'
	            }, {
	                width: '80',
	                title: '客户端ip',
	                field: 'clientIp'
	            }, {
	                width: '130',
	                title: '服务端ip',
	                field: 'serverIp'
	            }, {
	                width: '60',
	                title: '版本',
	                field: 'version'
	            }, {
	                width: '60',
	                title: '系统',
	                field: 'system'
	            }, {
	                width: '60',
	                title: '机型',
	                field: 'model'
	            }, {
	                width: '30',
	                title: '状态',
	                field: 'state',
	                formatter : function(value, row, index) {
	                    switch (value) {
	                    case 0:
	                        return '初始';
	                    case 1:
	                        return '正常';
	                    case 2:
	                        return '异常';
	                    }
	                }
	            }, {
	                width: '160',
	                title: '创建时间',
	                field: 'createtime',
	                formatter : function(value, row, index) {
	                	var crtTime = new Date(value);
	                	var str = formatDate("yyyy-MM-dd hh:mm:ss",crtTime);
	                    return str;
	                }
	            }]]
	        });
	    });
	    
	    function searchFun() {
	        dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	    }
	    function cleanFun() {
	        $('#searchForm input').val('');
	        dataGrid.datagrid('load', {});
	    }
	    
	    /*格式化日期*/  
	    function formatDate(fmt,date) { 
		  var o = {   
		    "M+" : date.getMonth()+1,                 //月份   
		    "d+" : date.getDate(),                    //日   
		    "h+" : date.getHours(),                   //小时   
		    "m+" : date.getMinutes(),                 //分   
		    "s+" : date.getSeconds(),                 //秒   
		    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
		    "S"  : date.getMilliseconds()             //毫秒   
		  };   
		  if(/(y+)/.test(fmt))   
		    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
		  for(var k in o)   
		    if(new RegExp("("+ k +")").test(fmt))   
		  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
		  return fmt;   
		}
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 55px; overflow: hidden;background-color: #fff">
        <form id="searchForm">
            <table>
                <tr>
                    <th>年月:</th>
                    <td><input type="text" id="storage_table" name="storage_table" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM'})" readonly="readonly" value="<%=date%>" /></td>
                    <th></th>
                    <th>用户名:</th>
                    <td><input name="userName" placeholder="请输入用户名"/></td>
                    <th></th>
                    <th>电话号码:</th>
                    <td><input name="userPhone" placeholder="请输入电话号码"/></td>
                    <th></th>
                    <td>
                    	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
                    	<!-- <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a> -->
                	</td>
                </tr>
            </table>
        </form>
    </div>
	<div data-options="region:'center',border:false">
	    <table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>