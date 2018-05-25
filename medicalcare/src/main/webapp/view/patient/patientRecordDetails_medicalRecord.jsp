<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript">
		var patientLoginId = '${patientLoginId}';
	    var dataGrid;
	    $(function() {
	    	dataGrid = $('#dataGrid').datagrid({
	            url : '${path }/sys/patientRecord/getDetailsMedicalRecord.action?patientLoginId=' + patientLoginId,
	            fit : true,
	            striped : true,
	            rownumbers : true,
	            pagination : true,
	            singleSelect : true,
	            idField : 'id',
	            sortName : 'id',
	            sortOrder : 'asc',
	            pageSize : 10,
	            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
	            columns : [[{
	                width : '90',
	                title : '上传日期',
	                field : 'createtime',
	                formatter : function(value, row, index) {
	                	var str = formatDate(value);
	                    return str;
	                }
	            },{
	                width : '80',
	                title : '上传人',
	                field : 'patinetName',
	                sortable : true
	            },{
	                width : '90',
	                title : '诊断日期',
	                field : 'diagnoseDate',
	                formatter : function(value, row, index) {
	                	var str = formatDate(value);
	                    return str;
	                }
	            },{
	                width : '125',
	                title : '诊断结果',
	                field : 'diagnoseResult'
	            },{
	                width : '125',
	                title : '检查报告名称',
	                field : 'diagnoseContent'
	            },{
	                width : '200',
	                title : '检查报告图片URL',
	                field : 'inspectionReportUrl'
	            }/* ,{
	                field : 'action',
	                title : '操作',
	                width : 100,
	                formatter : function(value, row, index) {
	                	var str = '';
	                	str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-search" data-options="plain:true,iconCls:\'icon-search\'" onclick="details(\'{0}\')" >详情</a>', row.id);
	                    return str;
	               }
	            } */
	            ]],
	            onLoadSuccess:function(data){
	            	//$('.user-easyui-linkbutton-search').linkbutton({text:'详情',plain:true,iconCls:'icon-search'});
	            },
	            toolbar : '#toolbar'
	        });
	    });
	
	     function details(id){
	    	parent.$.modalDialog({
	            title : '详情',
	            width : 800,
	            height : 650,
	            href : '${path }/sys/patientRecord/userPageDetails.action?id=' + id,
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
	        dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	    }
	    function cleanFun() {
	        $('#searchForm input').val('');
	        dataGrid.datagrid('load', {});
	    }
	    
	    /*格式化日期*/  
	    function formatDate(f_date) {
	    	if (f_date == null) {
	    		return '';
	    	}
	    	
	    	var returnDate = new Date(f_date);
	    	
	        return returnDate.getFullYear()+"-"+(returnDate.getMonth()+1)+"-"+returnDate.getDate();
	    }
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 50px; overflow: hidden;background-color: #fff">
	    <form id="searchForm">
	        <table>
	            <tr>
	                <th>诊断日期:</th>
	                <td><input type="text" name="searchStartTime" placeholder="点击选择时间" style="width:83px" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="readonly"/></td>
	                <th>~</th>
	                <td><input type="text" name="searchEndTime" placeholder="点击选择时间" style="width:83px" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="readonly"/></td>
	                <th style="width:75px"></th>
	                <td><input name="diagnoseResult" placeholder="请输入检查结果"/></td>
	                <th></th>
	                <td>
	                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">搜索</a>
	                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
	            	</td>
	            </tr>
	        </table>
	    </form>
	</div>
	<div data-options="region:'center',border:true,title:'检查结果列表'" >
	    <table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>