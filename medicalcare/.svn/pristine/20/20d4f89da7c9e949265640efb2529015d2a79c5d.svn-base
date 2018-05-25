<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>热门精选视频管理</title>
    <script type="text/javascript">
	
	var map = new Map();
	//为map添加值；key:对应filed="userName"中的字段名；value:对应数据库的字段
	
	map['mediaName']='media_name';
	map['hotType']='hot_type';	
	map['mchtCode']='mcht_code';
	map['updateTime']='update_time';
	onSortColumn=function(param,map){
		//取出map中字段的映射关系值  
		var fieldSort=map[param.sort];
		if(fieldSort!='' && fieldSort!=undefined){
			//设置新的排序字段名，设置完之后，发送请求时一并会发送到服务端
			param.sort=fieldSort;
		}
	}

    var index_tabs;
	var dataGrid;
	$(function() {
		$("#hotType").combobox('setValue',"");
		dataGrid = $('#dataGrid').datagrid({
			url : '${path }/crm/hotMediaStore/findMediaStorePage.do',
			fit : true,
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'media_id',
			sortOrder : 'asc',
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			columns : [ [ {
				field : 'id',
				hidden:true
			}, {
                width : '100',
                title : '商家',
                field : 'mchtCode',
				sortable : true
            },{
            	width : '80',
            	title : '视频',
                field : 'mediaStore',
				//sortable : true,
				formatter : function formatSubData(value, row){
					return value.name;
				}
            },{
                width : '80',
                title : '优先级',
                field : 'rank',
				sortable : true
            },{
				width : '120',
				title : '热门类型',
				field : 'hotType',
				sortable : true,
				formatter: function(value,row,index){
					switch (value) {
                    case 0:
                        return '精选';
                    case 1:
                        return '推荐';
                    }
				}
			},{
				width : '120',
				title : '描述',
				field : 'remark'
			},{
				width : '120',
				title : '更新时间',
				field : 'updateTime',
				formatter : formatterdate,
				sortable : true
			},{
				field : 'action',
				title : '操作',
				width : 200,
				formatter : function(value, row, index) {
					var str = $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-del\'" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
					return str;
				}
			}] ],
			onLoadSuccess:function(data){           	
				$('.user-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-del'});
			},
			onBeforeLoad:function(param){  
				onSortColumn(param,map);  
			},
			toolbar : '#toolbar'
		});

	});


	function deleteFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前视频？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
				if (currentUserId != id) {
					progressLoad();
					$.post('${path }/crm/hotMediaStore/delete.do', {
						id : id
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							dataGrid.datagrid('reload');
						}
						progressClose();
					}, 'JSON');
				}
			}
		});
	}

	function searchFun() {
		console.log($.serializeObject($('#searchForm')))
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
		
	function cleanFun(){
        dataGrid.datagrid('load', {});
		$("#hotType").combobox('setValue',"");
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



	function CloseTab(menu,type){
		var curTabTitle = $(menu).data("tabTitle");
		var tabs = $("#index_tabs");

		if(type === "close"){
			tabs.tabs("close",curTabTitle);
			return;
		}

		var allTabs = tabs.tabs("tabs");
		var closeTabsTitle = [];

		$.each(allTabs,function(){
			var opt = $(this).panel("options");
			if(opt.closable && opt.title != curTabTitle && type === "Other"){
				closeTabsTitle.push(opt.title);
			}else if(opt.closable && type === "All"){
				closeTabsTitle.push(opt.title);
			}
		});

		for(var i = 0;i<closeTabsTitle.length;i++){
			tabs.tabs("close",closeTabsTitle[i]);
		}
	}
	//绑定tabs的右键菜单 endxx
	function changev(){
		 document.getElementById("t1").value='NO';
	}
	function changev2(){
		 document.getElementById("t1").value='YES';
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="searchForm">
            <table>
                <tr>
                    <th>热门类型:</th>
                    <td>
                    <select  data-options="editable: false" class="easyui-combobox" panelHeight="auto" style="width:100px"  id="hotType" name="hotType">
	               		<option value="0">精选</option> <option value="1">推荐</option></select>
                    </td>
                    <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'热门视频列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div id="toolbar">
    </div>
     
</body>
</html>