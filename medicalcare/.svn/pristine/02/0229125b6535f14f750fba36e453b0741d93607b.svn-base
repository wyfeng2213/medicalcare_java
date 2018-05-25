<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>	
<%@ page import="com.cmcc.medicalcare.model.SystemUser" %>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医生档案管理</title>
 <%
 SystemUser systemUser = (SystemUser)session.getAttribute("systemUser");
 String hospitalName = systemUser.getHospitalName();
 Integer hospitalId = systemUser.getHospitalId();
 System.out.println(hospitalName);
 boolean flag = (hospitalName!=null&&!hospitalName.equals("")&&hospitalId!=null)?true:false;
 %>
    <script type="text/javascript">

    var dataGrid;
    
    var hospitalId=null;
    if(<%=flag%>){
    	hospitalId = <%=hospitalId%>;
    }

    $(function() {
        //var hospitalId =<%=hospitalId%>;  
        //alert(hospitalId);

        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/sys/doctorRecord/findUserPage.action?hospitalId2='+hospitalId,
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
            columns : [ [ {
                width : '130',
                title : '医生ID',
                field : 'loginId',
                sortable : true
            }, {
                width : '130',
                title : '姓名',
                field : 'name',
                sortable : true
            },{
                width : '130',
                title : '手机号',
                field : 'phone',
                sortable : true
            },{
                width : '130',
                title : '就职机构',
                field : 'hospital'
            },{
                width : '130',
                title : '所在科室',
                field : 'department',
                sortable : true
            },  {
                width : '130',
                title : '职称',
                field : 'title',
                sortable : true
            } , {
                field : 'action',
                title : '操作',
                width : 130,
                formatter : function(value, row, index) {
                    var str = '';
                       
                    		str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-search" data-options="plain:true,iconCls:\'icon-search\'" onclick="query(\'{0}\')" >详情</a>', row.id);
                    
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >修改</a>', row.id);
                   
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){
            	$('.user-easyui-linkbutton-search').linkbutton({text:'详情',plain:true,iconCls:'icon-search'});
                $('.user-easyui-linkbutton-edit').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
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
    function addFun() {
        parent.$.modalDialog({
            title : '添加医生',
            width : 800,
            height : 500,
            href : '${path }/sys/doctorRecord/addPage.action',
            buttons : [ {
                text : '提交',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#doctorRecordAddForm');
                    f.submit();
                }
            },{
                text : '关闭',
                handler : function() {
                	parent.$.modalDialog.handler.dialog('destroy');
					parent.$.modalDialog.handler = undefined;
                }
            } ]
        });
    }
    
    
    function downLoad() {
    	window.open("${path }/sys/doctorRecord/downLoadFile.action");
    }
    
    function query(id){
    	parent.$.modalDialog({
            title : '查看医生详情',
            width : 800,
            height : 550,
            href : '${path }/sys/doctorRecord/queryPage.action?id=' + id,
            buttons : [ {
                text : '关闭',
                handler : function() {
                	parent.$.modalDialog.handler.dialog('destroy');
            		parent.$.modalDialog.handler = undefined;
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
        parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
            if (b) {
                var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
                if (currentUserId != id) {
                    progressLoad();
                    $.post('${path }/sys/user/delete.do', {
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
            title : '修改医生档案',
            width : 800,
            height : 550,
            href : '${path }/sys/doctorRecord/editPage.action?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#doctorRecordEditForm');
                    f.submit();
                }
            },{
                text : '关闭',
                handler : function() {
                	parent.$.modalDialog.handler.dialog('destroy');
					parent.$.modalDialog.handler = undefined;
                }
            }]
        });
    }
    
    function batchImport(){
    	 parent.$.modalDialog({
             title : '导入',
             width : 300,
             height : 200,
             href : '${path }/sys/doctorRecord/batchImportPage.action',
             buttons : [ {
                 text : '确定',
                 handler : function() {
                     parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                     var f = parent.$.modalDialog.handler.find('#doctorRecordBatchImportForm');
                     f.submit();
                 }
             },{
                 text : '关闭',
                 handler : function() {
                 	parent.$.modalDialog.handler.dialog('destroy');
 					parent.$.modalDialog.handler = undefined;
                 }
             }]
         });
    }
    
    function searchFun() {
        dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    }
    function cleanFun() {
        $('#searchForm input').val('');
        dataGrid.datagrid('load', {});
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
                    <th>职称:</th>
                    <td><input name="title" placeholder="请输入职称"/></td>
                    
                </tr>
                <tr>
                	<th>就职机构:</th>
                    <td><input name="hospital" placeholder="请输入就职机构"/></td>
                    <th>科室:</th>
                    <td><input name="department" placeholder="请输入科室"/></td>
                     <th></th>
                    <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
                	</td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'医生列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>

    <div id="toolbar" style="display: none;">     
            <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
            <a onclick="downLoad();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-redo'">下载医生模版</a>
            <a id="batchImport" onclick="batchImport();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-mini-add'">批量导入医生</a>
    </div>
    <script type="text/javascript">
    if(<%=flag%>){
    	var currentBtn = document.getElementById('batchImport');
    	currentBtn.style.visibility="hidden"; 
    }
    </script>
</body>
</html>