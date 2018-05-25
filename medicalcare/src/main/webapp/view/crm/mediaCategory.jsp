<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>频道管理</title>
    <script type="text/javascript">

	var map = new Map();
	//为map添加值；key:对应filed="userName"中的字段名；value:对应数据库的字段	
	map['categoryName']='category_name';
	map['imageUrl']='image_url';
	map['iconUrl']='icon_url';
	map['styleType']='style_type';
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
            url : '${path }/crm/mediaCategory/findMediaCategoryPage.do',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [  {
                width : '80',
                title : '频道ID',
                field : 'id',
                sortable : true
            } , {
                width : '80',
                title : '频道名称',
                field : 'categoryName',
                sortable : true
            } , {
                width : '150',
                title : '频道图片',
                field : 'imageUrl',
                sortable : true
            } , {
                width : '150',
                title : '频道图标',
                field : 'iconUrl',
                sortable : true
            } , {
                width : '80',
                title : '视频排列样式',
                field : 'styleType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                        return '横排';
                    case 1:
                        return '竖排';
                    }
                }
            } , {
                width : '80',
                title : '优先级',
                field : 'rank',
                sortable : true
            }, {
                width : '200',
                title : '描述',
                field : 'remark',
                sortable : true
            } , {
                field : 'action',
                title : '操作',
                width : 400,
                formatter : function(value, row, index) {
                    var str = '';
                            str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
                            str += '&nbsp;|&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-del\'" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
                            str += '&nbsp;|&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-add" data-options="plain:true,iconCls:\'icon-add\'" onclick="addLable(\'{0}\');" >添加标签</a>', row.id);
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.role-easyui-linkbutton-edit').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
                $('.role-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-del'});
                $('.role-easyui-linkbutton-add').linkbutton({text:'添加标签',plain:true,iconCls:'icon-add'});
            },
			onBeforeLoad:function(param){  
				onSortColumn(param,map);  
			},
            toolbar : '#toolbar'
        });
    });
    
    function addLable(id){
    	addTab('频道检索标签','${path}/crm/mediaCategory/lable/manager.do','menu_icon_datadeal',id);
    }
    function addTab(title, href, icon,id) {
    	var tt = parent.$('#index_tabs');
        icon = icon || 'menu_icon_service';
        if (tt.tabs('exists', title)) {
            tt.tabs('select', title);
            var currTab = tt.tabs('getTab', title);
            var content = '<iframe name="mediaCategoryLable" frameborder="0" src="' + href + '" style="border:0;width:100%;height:99.5%;"></iframe>';
            tt.tabs('update', {tab: currTab, options: {content: content, closable: true}});
            window.open(href+"?id="+id,"mediaCategoryLable");
        } else {
            if (href) {
                var content = '<iframe name="mediaCategoryLable" frameborder="0" src="' + href + '" style="border:0;width:100%;height:99.5%;"></iframe>';
            } else {
                var content = '未实现';
            }
            tt.tabs('add', {
                title : title,
                content : content,
                closable : true,
                iconCls: icon,
            });          
            window.open(href+"?id="+id,"mediaCategoryLable");
        }
       	
    }
    function addFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 300,
            height : 300,
            href : '${path }/crm/mediaCategory/addPage.do',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#mediaCategoryAddForm');
                    f.submit();
                }
            } ]
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
            height : 300,
            href : '${path }/crm/mediaCategory/editPage.do?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#mediaCategoryEditForm');
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
        parent.$.messager.confirm('询问', '您是否要删除当前频道？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path }/crm/mediaCategory/delete.do', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        dataGrid.datagrid('reload');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }
    
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div id="toolbar" style="display: none;">
            <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
    </div>
</body>
</html>