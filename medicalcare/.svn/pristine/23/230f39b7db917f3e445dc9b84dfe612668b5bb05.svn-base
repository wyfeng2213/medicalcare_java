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
            url : '${path }/sys/doctorServiceSetting/findUserPage.action?hospitalId2='+hospitalId,
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
                title : '姓名',
                field : 'name',
                sortable : true
            },{
                width : '130',
                title : '手机号',
                field : 'phone',
                sortable : true
            },
			{
				width : '100',
				title : '是否快速咨询',
				field : 'isQuickConsulting',
				sortable : true,
				formatter : function(value, row, index) {
					if (value == "0") {
						return "<font color='black'>关闭</font>";
					} else if (value == "1") {
						return "<font color='green'>开启</font>";
					} 
				}
			},
			{
				width : '100',
				title : '是否视频咨询',
				field : 'isVideoConsultation',
				sortable : true,
				formatter : function(value, row, index) {
					if (value == "0") {
						return "<font color='black'>关闭</font>";
					} else if (value == "1") {
						return "<font color='green'>开启</font>";
					}
				}
			},
			{
				width : '100',
				title : '是否图文咨询',
				field : 'graphicConsulting',
				sortable : true,
				formatter : function(value, row, index) {
					if (value == "0") {
						return "<font color='black'>关闭</font>";
					} else if (value == "1") {
						return "<font color='green'>开启</font>";
					}
				}
			}, {
                field : 'action',
                title : '操作',
                width : 130,
                formatter : function(value, row, index) {
                    var str = '';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >修改</a>', row.id);
                   
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){
                $('.user-easyui-linkbutton-edit').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
            },
            toolbar : '#toolbar'
        });

    });


    function query(id){
    	parent.$.modalDialog({
            title : '查看医生服务设置详情',
            width : 800,
            height : 500,
            href : '${path }/sys/doctorServiceSetting/queryPage.action?id=' + id,
            buttons : [ {
                text : '关闭',
                handler : function() {
                	parent.$.modalDialog.handler.dialog('destroy');
            		parent.$.modalDialog.handler = undefined;
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
            title : '修改医生服务设置',
            width : 400,
            height :300,
            href : '${path }/sys/doctorServiceSetting/editPage.action?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#doctorServiceSettingEditForm');
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
                    <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
                	</td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'医生服务设置列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>

    <div id="toolbar" style="display: none;">     
    </div>
</body>
</html>