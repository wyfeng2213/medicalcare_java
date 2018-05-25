<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单管理</title>
<script type="text/javascript">

	var map = new Map();
	//为map添加值；key:对应filed="userName"中的字段名；value:对应数据库的字段
	map['transactionId']='transaction_id';
	map['buyTime']='buy_time';
	map['paidTime']='paid_time';
	map['orderStatus']='order_status';
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
            url : '${path }/crm/order/findOrderPage.do',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ 
            {
                width : '30',
                title : ' id',
                field : 'id',
                hidden : true
            }, {
                width : '100',
                title : ' 订单号',
                field : 'transactionId',
				sortable : true
            }, {
                width : '80',
                title : '账户名称',
                field : 'accountid',
				sortable : true
            }, {
                width : '80',
                title : '号码',
                field : 'mobile',
				sortable : true
            },{
                width : '150',
                title : '产品名称',
                field : 'product',
				formatter : function formatSubData(value, row){
					return value.productName;
				}
            },{
                width : '120',
                title : '订购时间',
                formatter : formatterdate,
                field : 'buyTime',
                sortable : true
            },{
                width : '120',
                title : '付费时间',
                formatter : formatterdate,
                field : 'paidTime',
                sortable : true
            },{
                width : '60',
                title : '付费价钱',
                field : 'cost',
				sortable : true
            },{
                width : '60',
                title : '订单状态',
                field : 'orderStatus',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                        return '待支付';         
                    case 1:
                        return '已支付'; 
					case 2:
                        return '订购失败'; 
                    case 3:
                        return '已用完';
                    case 4:
                        return '已过期';
                    case 7:
                        return '已取消';
                    }
                }
            }
			,{
                width : '120',
                title : '更新时间',
                field : 'updateTime',
                formatter : formatterdate,
                sortable : true
            },{
                width : '80',
                title : 'ECOP单号',
                field : 'ecopOrderseq',
                sortable : true
            },{
                width : '60',
                title : 'ECOP码',
                field : 'ecopCode',
                sortable : true
            },{
                width : '200',
                title : 'ECOP消息',
                field : 'ecopMsg',
                sortable : true
            },{
                field : 'action',
                title : '操作',
                width : 120,
                formatter : function(value, row, index) {
                    var str = '';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-del\'" onclick="editFun(\'{0}\');" >取消订单</a>', row.id);                       
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){
                $('.user-easyui-linkbutton-del').linkbutton({text:'取消订单',plain:true,iconCls:'icon-del'});
            },
			onBeforeLoad:function(param){  
				onSortColumn(param,map);  
			},
            toolbar : '#toolbar'
        });

    });

    
    function editFun(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }

		parent.$.modalDialog({
			title : '取消订单',
			width : 500,
			height : 300,
			href : '${path }/crm/order/editPage.do?id=' + id,
			buttons : [ {
				text : '确定',
				handler : function() {
					parent.$.messager.confirm('询问', '您是否要取消订单？', function(b) {
						if (b) {  
							parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
							var f = parent.$.modalDialog.handler.find('#orderEditForm');
							f.submit();
						}
					})
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
    function formatterdate(val) {
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
                    <th>订单号:</th>
						<td><input type="text" name="transactionId" placeholder="请输入订单号"  style="width:120px" value=""/></td>
					<th>手机号:</th>
						<td><input type="text" name="mobile" placeholder="请输入手机号" style="width:100px" value=""/></td>
					<th>订单状态:</th>
						<td>
						<select id="orderStatus" name="orderStatus" class="easyui-combobox" style="width: 80px" panelMaxHeight="160px" data-options="editable:false,panelHeight:'auto'">
							<option value="-1">全部</option>
							<option value="0">待支付</option>
							<option value="1">已支付</option>
							<option value="2">订购失败</option>
							<option value="7">用户取消</option>
                        </select>
						</td>
                    <th>订购时间:</th>
                    <td>
						<input name="buyTime" placeholder="点击选择时间"  onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />至
						<input  name="buyendTime" placeholder="点击选择时间"  onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'订单列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
     <!--
    <div data-options="region:'west',border:true,split:false,title:'组织机构'"  style="width:150px;overflow: hidden; ">
        <ul id="organizationTree"  style="width:160px;margin: 10px 10px 10px 10px">
        </ul>
    </div>
    -->
     
</body>
</html>