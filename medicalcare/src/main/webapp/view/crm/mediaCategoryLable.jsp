<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>频道检索标签管理</title>
    <script type="text/javascript">

	var map = new Map();
	//为map添加值；key:对应filed="userName"中的字段名；value:对应数据库的字段	
	map['mediaCategory']='category_id';
	map['labelType']='label_type';
	map['labelKey']='label_key';
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
    var cId;
    var flag = 0;
    $(function() {
    	var r = window.location.search.substr(1);
    	p = r.split("=");
    	var url='${path }/crm/mediaCategory/lable/findLablePage.do';
    	if(typeof(p[1]) != "undefined"){
    		cId = p[1];
    		url ='${path }/crm/mediaCategory/lable/findLablePage.do?categoryId='+cId; 
    		flag = '1';
    	}
        dataGrid = $('#dataGrid').datagrid({
            url : url,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                width : '80',
                title : 'ID',
                field : 'id',
                sortable : true
            } ,{
                width : '80',
                title : '频道',
                field : 'mediaCategory',
				formatter : function formatSubData(value, row){
					return value.categoryName;
				},
				sortable : true
            } , {
                width : '80',
                title : '标签类别名',
                field : 'labelType',
                sortable : true
            }, {
                width : '80',
                title : '标签KEY',
                field : 'labelKey',
                sortable : true
            }, {
                width : '80',
                title : '显示顺序',
                field : 'rank',
                sortable : true
            }, {
                width : '130',
                title : '更新时间',
                field : 'updateTime',
                formatter : formatterdate,
                sortable : true
            }, {
                field : 'action',
                title : '操作',
                width : 150,
                formatter : function(value, row, index) {
                    var str = '';
                            str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
                            str += '&nbsp;|&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-del\'" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.role-easyui-linkbutton-edit').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
                $('.role-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-del'});
            },
			onBeforeLoad:function(param){  
				onSortColumn(param,map);  
			},
            toolbar : '#toolbar'
        });
        
      //根据频道筛选
        $(document).ready(function () {
        	$("#categoryId").combobox({
        		onChange: function (n,o) {
        			$('#dataGrid').datagrid(
       					{
       						url:'${path }/crm/mediaCategory/lable/findLablePage.do',    
       						queryParams:{categoryId:n}
       					}
        			);
        		}
        	});

        });
        
    });

    function addFun() {
    	var url = '${path }/crm/mediaCategory/lable/addPage.do';
    	if(typeof(cId) != "undefined"){
    		url = '${path }/crm/mediaCategory/lable/addPage.do?id='+cId;
    	}
        parent.$.modalDialog({
            title : '添加',
            width : 300,
            height : 200,
            href : url,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#lableAddForm');
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
            width : 300,
            height : 200,
            href : '${path }/crm/mediaCategory/lable/editPage.do?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#lableEditForm');
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
        parent.$.messager.confirm('询问', '您是否要删除当前标签？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path}/crm/mediaCategory/lable/delete.do', {
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

    function formatterdate(val, row) {
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
    <div data-options="region:'center',fit:true,border:false">
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div id="toolbar" style="display: none;">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
    	<td>频道筛选</td>
        <td >
            <select id="categoryId" name="categoryId" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" value="${mediaCategory.parentId }">
	            <option value="" selected>全部</option>
	            <c:forEach var="id" items="${ids}">
		            <c:choose>
						<c:when test="${id.id==lable.categoryId }">
							<option value="${id.id}"  selected>${id.categoryName}</option>
						</c:when>
						<c:otherwise>
							<option value="${id.id}">${id.categoryName}</option>
						</c:otherwise>
					</c:choose> 
	            </c:forEach>
            </select>
        </td>
    </div>
</body>
</html>