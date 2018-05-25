<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>患者档案管理</title>
    <script type="text/javascript">

    var dataGrid;
    $(function() {
        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/sys/patientRecord/findUserPage.action',
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
                width : '180',
                title : '患者ID',
                field : 'loginId',
                sortable : true
            },{
                width : '130',
                title : '姓名',
                field : 'name',
                sortable : true
            },{
                width : '80',
                title : '性别',
                field : 'sex',
            },{
                width : '80',
                title : '年龄',
                field : 'birthday',
                formatter : function(value, row, index) {
                	var str = jsGetAge(value);
                    return str;
                }
            },{
                width : '130',
                title : '手机号',
                field : 'phone'
            },{
                field : 'action',
                title : '操作',
                width : 130,
                formatter : function(value, row, index) {
                	var str = '';
                	str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-search" data-options="plain:true,iconCls:\'icon-search\'" onclick="details(\'{0}\')" >详细资料</a>', row.id);
                	return str;
               }
            }]],
            onLoadSuccess:function(data){
            	$('.user-easyui-linkbutton-search').linkbutton({text:'详细资料',plain:true,iconCls:'icon-search'});
            },
            toolbar : '#toolbar'
        });

		//设置分页控件
		/*
		var pager = $("#list_data").datagrid("getPager");
		var url = "findUserPage.do";
		pager.pagination({
			 beforePageText : "第",// 页数文本框前显示的汉字
			 afterPageText : "页 共 {pages} 页",
			 displayMsg : "当前显示从{from}到{to}共{total}记录",
			 layout : [ 'list', 'sep', 'first', 'prev', 'sep', 'manual', 'sep',
			 'next', 'last', 'sep', 'refresh' ],
			// 选择页的处理
			onSelectPage : function(pageNumber, pageSize) {
				// 按分页的设置取数据
				turnPageFunc(pageNumber, pageSize, url, sortfield, orderdir);
			},
			onChangePageSize : function() {

			},
			onBeforeRefresh : function(pageNumber, pageSize) {

			},
			onBeforeLoad : function(param) {// 表格加载数据前事件

			},
			onRefresh:function(pageNumber, pageSize){
				turnPageFunc(pageNumber, pageSize, url, sortfield, orderdir);
			}
		});
		$.get("findUserPage.do",function(data,status){
			//alert("Data: " + data + "\nStatus: " + status);
		});
		*/
    });

	// 点击分页按钮时,需要触发的事件
	/*
	function turnPageFunc(pageNumber, pageSize, url, sortfield, orderdir) {
		$("#dataGrid").datagrid('getPager').pagination({
			pageSize : pageSize,
			pageNumber : pageNumber
		});// 重置
		$("#dataGrid").datagrid("loading"); // 加屏蔽
		$.ajax({
			type : "POST",
			dataType : "json",
			url : url,
			data : {
				'page' : pageNumber,
				'rows' : pageSize,
				sort:sortfield,
				order:orderdir
			},
			success : function(data) {
				try {
					$("#dataGrid").datagrid('loadData', parsePageDataFunc(data.rows, data.total));
				} catch (e) {
					alert("exception occur...");
				}
			},
			error : function(err) {
				$.messager.alert('操作提示', '获取信息失败...请联系管理员!', 'error');
			},
			complete : function() {
				$("#dataGrid").datagrid("loaded"); // 移除屏蔽
			}
		});
	}

	function parsePageDataFunc(list,total){
		var obj=new Object(); 
		obj.total=total; 
		obj.rows=list; 
		return obj; 
	}
    */
    
    function sendFun() {
        parent.$.modalDialog({
            title : '发送通知',
            width : 500,
            height : 300,
            href : '${path }/sys/user/addPage.do',
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
    
    function details(id){
    	parent.$.modalDialog({
            title : '患者档案详情',
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
    
    /*根据出生日期算出年龄*/  
    function jsGetAge(longBirthday) {
    	if (longBirthday == null) {
    		return '';
    	}
    	
        var returnAge;
        
        var d = new Date(longBirthday);
        var birthYear = d.getFullYear();  
        var birthMonth = d.getMonth() + 1;  
        var birthDay = d.getDate();
        
        var newDate = new Date();  
        var nowYear = newDate.getFullYear();  
        var nowMonth = newDate.getMonth() + 1;  
        var nowDay = newDate.getDate();  
          
        if(nowYear == birthYear) {  
            returnAge = 0;//同年 则为0岁  
        } else {  
            var ageDiff = nowYear - birthYear ; //年之差  
            if(ageDiff > 0){  
                if(nowMonth == birthMonth) {  
                    var dayDiff = nowDay - birthDay;//日之差  
                    if(dayDiff < 0) {  
                        returnAge = ageDiff - 1;  
                    }  
                    else {  
                        returnAge = ageDiff ;  
                    }  
                } else {  
                    var monthDiff = nowMonth - birthMonth;//月之差  
                    if(monthDiff < 0) {  
                        returnAge = ageDiff - 1;  
                    } else {  
                        returnAge = ageDiff ;  
                    }  
                }  
            } else {  
                returnAge = -1;//返回-1 表示出生日期输入错误 晚于今天  
            }  
        }
        return returnAge;//返回周岁年龄  
    }
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 55px; overflow: hidden;background-color: #fff">
        <form id="searchForm">
            <table>
                <tr>
                    <th>姓名:</th>
                    <td><input name="name" placeholder="请输入姓名"/></td>
                    <th>电话:</th>
                    <td><input name="phone" placeholder="请输入电话"/></td>
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

    <div id="toolbar" style="display: none;">
<!--         <shiro:hasPermission name="/user/add.do">
            <a onclick="sendFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-large-smartart'">发送通知</a>
        </shiro:hasPermission> -->
    </div>
</body>
</html>