<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>视频管理</title>
    <script type="text/javascript">
	var map = new Map();
	//为map添加值；key:对应filed="userName"中的字段名；value:对应数据库的字段
	
	map['mchtCode']='mcht_code';
	map['searchLabel']='search_label';
	map['chargeType']='charge_type';
	map['mediaType']='media_type';
	map['issueDate']='issue_date';
	map['mediaPlayUrl']='media_play_url';
	map['mediaPlayId']='media_play_id';
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

        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/crm/mediaStore/findMediaStorePage.do',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : false,
            fitColumns:false,
            idField : 'id',
            sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                field : 'ck',
                checkbox : true
            },{	
                width : '60',
                title : '视频ID',
                field : 'mediaPlayId'
            }, {	
                width : '60',
                title : '商家',
                field : 'mchtCode',
				sortable : true
            }, {
                width : '100',
                title : '视频名称',
                field : 'name',
				sortable : true
            }, {
                width : '100',
                title : '视频说明',
                field : 'detail',
				sortable : true
            } ,{
                width : '80',
                title : '频道名称',
                field : 'mediaCategory',
				//sortable : true,
				formatter : function formatSubData(value, row){
					return value.categoryName;
				}
            },{
                width : '120',
                title : '视频URL',
                field : 'mediaPlayUrl',
				sortable : true
            },{
                width : '120',
                title : '缩略图',
                field : 'thumbnail',
				sortable : true
            },{
                width : '60',
                title : '时长',
                field : 'duration',
				sortable : true
            },{
                width : '120',
                title : '发布日期',
                field : 'issueDate',
                formatter : formatterdate,
                sortable : true
            },{
                width : '60',
                title : '优先级',
                field : 'rank',
                sortable : true
            },{
                width : '60',
                title : '状态',
                field : 'status',
				sortable : true,
				formatter : function(value, row, index) {
                    if(value==0)
					{
						return '有效';
					}
					else
					{
						return '失效';
					}
                }
            },{
                width : '60',
                title : '视频类型',
                field : 'mediaType',
				sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                    	changev();
                        return '单个视频';                                           
                    case 2:
                    	changev2();
                        return '组视频';
                    case 3:
                    	changev2();
                        return '连续剧';
                    }
                }
            },{
				width : '60',
	            title : '组标识',
	            field : 'mediaGroup',
				sortable : true,

            },{
                width : '60',
                title : '付费类型',
                field : 'chargeType',
				sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                        return '免费';                                           
                    case 1:
                        return '付费';
                    }
                }
            },{
                width : '120',
                title : '搜索关健字',
                field : 'searchLabel',
				sortable : true
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
                width : 265,
                formatter : function(value, row, index) {
                    var str = '';
                    if (document.getElementById("t1").value=='NO'){
                    		str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
                            str += '|';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-del\'" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
	                        str += '|';
	                        str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-search" data-options="plain:true,iconCls:\'icon-search\'" onclick="query(\'{0}\')" >缩略图</a>', row.id);
                    }else {	                    		                		
	                		str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
	                        str += '|';
	                        str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-del\'" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
	                        str += '|';
	                        str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-search" data-options="plain:true,iconCls:\'icon-search\'" onclick="query(\'{0}\')" >缩略图</a>', row.id);
	                        str += '|';
	                        str += $.formatString('<a href="javascript:void(0)" class="vedio-easyui-linkbutton-search" data-options="plain:true,iconCls:\'icon-back\'" onclick="searchFun2(\'{0}\')" >系列</a>', row.mediaGroup);	              
                    }
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){           	
                $('.user-easyui-linkbutton-edit').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
                $('.user-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-del'});
                $('.user-easyui-linkbutton-search').linkbutton({text:'缩略图',plain:true,iconCls:'icon-search'});
                $('.vedio-easyui-linkbutton-search').linkbutton({text:'系列',plain:true,iconCls:'icon-back'});
            },
			onBeforeLoad:function(param){  
				onSortColumn(param,map);  
			},
            toolbar : '#toolbar'
        });

    });

    function jxvedio(){
	    var rows = $('#dataGrid').datagrid('getSelections');
		var ids = '';
	    for(var i=0; i<rows.length; i++){
	    	ids = ids+rows[i].id +",";
	    }
			$.ajax({ 
	    		url: '${path }/crm/mediaStore/addhotmedia.do', 
	    		dataType: "json",   //返回格式为json
	    	    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
	    	    data: { "ids":ids,"mark":'0'},    //参数值
	    	    type: "POST",   //请求方式
	    		success: function(date){
						alert("设置成功!");
	          	}
	    	});

    }
    
    function tjvedio(){
	    var rows = $('#dataGrid').datagrid('getSelections');
		var ids = '';
	    for(var i=0; i<rows.length; i++){
	    	ids = ids+rows[i].id +",";
	    }
			$.ajax({ 
	    		url: '${path }/crm/mediaStore/addhotmedia.do', 
	    		dataType: "json",   //返回格式为json
	    	    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
	    	    data: { "ids":ids,"mark":'1'},    //参数值
	    	    type: "POST",   //请求方式
	    		success: function(date){
						alert("设置成功!");
	          	}
	    	});
    }
    
    function addFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 500,
            height : 475,
            href : '${path }/crm/mediaStore/addPage.do',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#mediaStoreAddForm');
                    f.submit();
                }
            } ]
        });
    }
    
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
                    $.post('${path }/crm/mediaStore/delete.do', {
                        id : id
                    }, function(result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            dataGrid.datagrid('reload');
                        }
                        progressClose();
                    }, 'JSON');
                } else {
                    parent.$.messager.show({
                        title : '提示',
                        msg : '不可以删除自己！'
                    });
                }
            }
        });
    }
    
    function editFun(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 500,
            height : 480,
            href : '${path }/crm/mediaStore/editPage.do?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#mediaStoreEditForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function query(id){
    	parent.$.modalDialog({
            title : '查看缩略图',
            width : 500,
            height : 300,
            href : '${path }/crm/mediaStore/showThumbnail.do?id=' + id
        });
    }
    
    function searchFun() {
        dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    }
        
    function searchFun2(id) {
    	dataGrid.datagrid({  
    	  url:'${path }/crm/mediaStore/findMediaStoreGroup.do?mediaGroup='+ id
    	});
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
	
    
    function addTab(title, href, icon,id) {
    	var tt = parent.$('#index_tabs');
        icon = icon || 'menu_icon_service';
        if (tt.tabs('exists', title)) {
            tt.tabs('select', title);
            var currTab = tt.tabs('getTab', title);
            tt.tabs('update', {tab: currTab, options: {content: content, closable: true}});
        } else {
            if (href) {
                var content = '<iframe name="mediaSubset" frameborder="0" src="' + href + '" style="border:0;width:100%;height:99.5%;"></iframe>';
            } else {
                var content = '未实现';
            }
            tt.tabs('add', {
                title : title,
                content : content,
                closable : true,
                iconCls: icon
            });          
        }
        window.open(href+"?id="+id,"mediaSubset");
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
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff" id="index_tabs">
        <form id="searchForm">
            <table>
                <tr>
                    <th>视频名:</th>
                    <td><input name="name" placeholder="请输入视频名"/></td>
                    <th>视频说明:</th>
                    <td><input name="detail" placeholder="请输入视频说明"/></td>
                    <th>发布时间:</th>
                    <td>
                    <input name="queryBeginDate" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />至<input  name="queryEndDate" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'视频列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div> 
    <div id="tabsMenu" class="easyui-menu" style="width:120px;">  
		<div name="close">关闭</div>  
		<div name="Other">关闭其他</div>  
		<div name="All">关闭所有</div>
	</div>    
	<div><input type="hidden" id="t1"></div>
	<div id="toolbar" data-options="region:'center',border:true,title:'频道列表'">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>&nbsp&nbsp
        <a onclick="jxvedio();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">设为精选<a style="color:red;">(请勾选)</a></a>
        <a onclick="tjvedio();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">设为推荐<a style="color:red;">(请勾选)</a></a>
    </div>
</body>
</html>