<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>	
<%@ page import="com.cmcc.medicalcare.model.SystemUser" %>	
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>粤健康-管理平台</title>
<style>
</style>
 <%
 SystemUser systemUser = (SystemUser)session.getAttribute("systemUser");
 String hospitalName = systemUser.getHospitalName();
 Integer hospitalId = systemUser.getHospitalId();
 System.out.println(hospitalName);
 boolean flag = (hospitalName!=null&&!hospitalName.equals("")&&hospitalId!=null)?true:false;
 %>
<script type="text/javascript">
	var index_layout;
	var index_tabs;
	var layout_west_tree;

	$(function() {
		
		if(<%=flag%>){
			$("#accordionMenu").accordion('remove','系统管理');
			$("#accordionMenu").accordion('remove','患者管理');
			//$("#accordionMenu").accordion('remove','助理管理');
			$("#accordionMenu").accordion('remove','日志管理');
			$("#accordionMenu").accordion('remove','通知');
			$("#accordionMenu").accordion('remove','运营');
		}
		
		
		index_layout = $('#index_layout').layout({
			fit : true
		});
		index_tabs = $('#index_tabs').tabs(
				{
					fit : true,
					border : false,
					tools : [
							{
								iconCls : 'icon-home',
								handler : function() {
									index_tabs.tabs('select', 0);
								}
							},
							{
								iconCls : 'icon-refresh',
								handler : function() {
									var index = index_tabs.tabs('getTabIndex',
											index_tabs.tabs('getSelected'));
									index_tabs.tabs('getTab', index).panel(
											'open').panel('refresh');
								}
							},
							{
								iconCls : 'icon-del',
								handler : function() {
									var index = index_tabs.tabs('getTabIndex',
											index_tabs.tabs('getSelected'));
									var tab = index_tabs.tabs('getTab', index);
									if (tab.panel('options').closable) {
										index_tabs.tabs('close', index);
									}
								}
							} ]
				});

		//绑定tabs的右键菜单 begin
		$("#index_tabs").tabs({
			onContextMenu : function(e, title) {
				e.preventDefault();
				$('#tabsMenu').menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data("tabTitle", title);
			}
		});

		//实例化menu的onClick事件
		$("#tabsMenu").menu({
			onClick : function(item) {
				CloseTab(this, item.name);
			}
		});

		//几个关闭事件的实现
		function CloseTab(menu, type) {
			var curTabTitle = $(menu).data("tabTitle");
			var tabs = $("#index_tabs");

			if (type === "close") {
				tabs.tabs("close", curTabTitle);
				return;
			}

			var allTabs = tabs.tabs("tabs");
			var closeTabsTitle = [];

			$.each(allTabs, function() {
				var opt = $(this).panel("options");
				if (opt.closable && opt.title != curTabTitle
						&& type === "Other") {
					closeTabsTitle.push(opt.title);
				} else if (opt.closable && type === "All") {
					closeTabsTitle.push(opt.title);
				}
			});

			for (var i = 0; i < closeTabsTitle.length; i++) {
				tabs.tabs("close", closeTabsTitle[i]);
			}
		}
		//绑定tabs的右键菜单 endxx
	});

	function addTab(title, href, icon) {
		var tt = $('#index_tabs');
		icon = icon || 'menu_icon_service';
		if (tt.tabs('exists', title)) {
			tt.tabs('select', title);
			var currTab = tt.tabs('getTab', title);
			tt.tabs('update', {
				tab : currTab,
				options : {
					content : content,
					closable : true
				}
			});
		} else {
			if (href) {
				var content = '<iframe frameborder="0" src="'
						+ href
						+ '" style="border:0;width:100%;height:99.5%;"></iframe>';
			} else {
				var content = '未实现';
			}
			tt.tabs('add', {
				title : title,
				content : content,
				closable : true,
				iconCls : icon
			});
		}
	}

	function logout() {
		$.messager.confirm('提示', '确定要退出?', function(r) {
			if (r) {
				progressLoad();
				$.post('${path }/sys/logout.action', function(result) {
					if (result.code == 200) {
						progressClose();
						window.location.href = '${path }/sys/login.action';
					}
				}, 'json');
			}
		});
	}

	function editUserPwd() {
		parent.$.modalDialog({
			title : '修改密码',
			width : 300,
			height : 250,
			href : '${path }/sys/systemUser/editPwdPage.action',
			buttons : [ {
				text : '确定',
				handler : function() {
					var f = parent.$.modalDialog.handler
							.find('#editUserPwdForm');
					f.submit();
				}
			} ]
		});
	}
</script>
</head>
<body>

	<div id="loading"
		style="position: fixed; top: -50%; left: -50%; width: 200%; height: 200%; background: #fff; z-index: 100; overflow: hidden;">
		<img src="${staticPath }/style/images/ajax-loader.gif"
			style="position: absolute; top: 0; left: 0; right: 0; bottom: 0; margin: auto;" />
	</div>
	<div id="index_layout">
		<div data-options="region:'north',border:false" class="header">
			<div>
				<span
					style="float: right; padding-right: 20px; margin-top: 15px; color: #333">欢迎
					<b><shiro:principal></shiro:principal></b>&nbsp;&nbsp; <shiro:hasPermission
						name="/sys/systemUser/editPwdPage.action">
						<a href="javascript:void(0)" onclick="editUserPwd()"
							class="easyui-linkbutton" plain="true" icon="icon-edit">修改密码</a>
					</shiro:hasPermission>&nbsp;&nbsp;<a href="javascript:void(0)" onclick="logout()"
					class="easyui-linkbutton" plain="true" icon="icon-clear">安全退出</a>
				</span>
				<!--<span class="header"></span>-->
			</div>
		</div>
		<div data-options="region:'west',split:true" title="菜单"
			style="width: 160px; overflow: hidden; overflow-y: auto; padding: 0px">
			<div class="easyui-accordion  i_accordion_menu" fit="true" id="accordionMenu"
				border="false">

				<div title="系统管理" id="systemManagement" selected="true" style="overflow: auto;">
					<shiro:hasPermission name="/sys/hospital/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('机构管理','${path}/sys/hospital/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>机构管理</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/systemUser/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('管理员管理','${path}/sys/systemUser/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>管理员管理</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/systemRole/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('角色管理','${path}/sys/systemRole/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>角色管理</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/systemResource/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('资源管理','${path}/sys/systemResource/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>资源管理</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/pav/doctorApk/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('医生APK管理','${path}/sys/pav/doctorApk/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>医生APK管理</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/pav/patientApk/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('患者APK管理','${path}/sys/pav/patientApk/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>患者APK管理</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/systemBanner/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('banner管理','${path}/sys/systemBanner/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>banner管理</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/systemHealthyProducts/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('健康产品管理','${path}/sys/systemHealthyProducts/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>健康产品展示</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/scienceColumn/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('健康科普标题栏管理','${path}/sys/scienceColumn/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>健康科普标题栏管理</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/scienceArticle/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('健康科普文章管理','${path}/sys/scienceArticle/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>健康科普文章管理</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/sysSensitiveWord/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('敏感词管理','${path}/sys/sysSensitiveWord/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>敏感词管理</span>
						</a>
					</div>
					</shiro:hasPermission>
				</div>

				<div title="医生管理" id="doctorManagement" selected="true" style="overflow: auto;">
					<shiro:hasPermission name="/sys/doctorRecord/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('医生档案','${path}/sys/doctorRecord/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>医生档案</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/doctorRegister/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('医生注册审核','${path}/sys/doctorRegister/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>医生注册审核</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/doctorServiceSetting/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('医生服务设置管理','${path}/sys/doctorServiceSetting/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>医生服务设置</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/doctorsOrders/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('医生服务记录','${path}/sys/doctorsOrders/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>医生服务记录</span>
						</a>
					</div>
					</shiro:hasPermission>
				</div>


				<div title="患者管理" id="patientManagement" selected="true" style="overflow: auto;">
					<shiro:hasPermission name="/sys/patientRecord/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('患者档案','${path}/sys/patientRecord/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>患者档案</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/patientManage/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('冻结患者管理','${path}/sys/patientManage/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>冻结患者管理</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/healthStep/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('健康走管理','${path}/sys/healthStep/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>健康走管理</span>
						</a>
					</div>
					</shiro:hasPermission>
					<%-- <shiro:hasPermission name="/crm/favorMedia/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('用户收藏管理','${path}/crm/favorMedia/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>患者消息管理</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/crm/suitMediaCategory/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('用户订制管理','${path}/crm/suitMediaCategory/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>签约管理</span>
						</a>
					</div>
					</shiro:hasPermission> --%>
				</div>


				 <%-- <div title="助理管理" id="secretaryManagement" selected="true" style="overflow: auto;">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('APP用户信息','${path}/crm/user/manager.do','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>助理申请</span>
						</a>
					</div>
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('用户收藏管理','${path}/crm/favorMedia/manager.do','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>助理档案</span>
						</a>
					</div>
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('用户订制管理','${path}/crm/suitMediaCategory/manager.do','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>助理服务记录</span>
						</a>
					</div>
				</div> --%>

				<div title="日志管理" id="logManagement" selected="true" style="overflow: auto;">
					<shiro:hasPermission name="/sys/systemRequestLog/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('请求日志','${path}/sys/systemRequestLog/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>请求日志</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/mediaLog/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('媒体日志','${path}/sys/mediaLog/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>媒体日志</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/inquiryLog/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('问诊日志','${path}/sys/inquiryLog/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>问诊日志</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/sysSensitiveRecordLog/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('敏感词记录日志管理','${path}/sys/sysSensitiveRecordLog/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>敏感词记录日志管理</span>
						</a>
					</div>
					</shiro:hasPermission>
				</div>

				<div title="通知" id="notifyManagement" selected="true" style="overflow: auto;">
					<shiro:hasPermission name="/sys/systemMessageNotice/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('通知列表','${path}/sys/systemMessageNotice/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>通知列表</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/systemMessageReminding/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('患者端消息通知列表','${path}/sys/systemMessageReminding/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>患者端消息通知列表</span>
						</a>
					</div>
					</shiro:hasPermission>
				</div>

				<div title="运营" id="operationManagement" selected="true" style="overflow: auto;">
					<shiro:hasPermission name="/sysChart/sysChartsStatistics.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('运营统计','${path}/sysChart/sysChartsStatistics.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>运营统计</span>
						</a>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/sys/systemUserCount/manager.action">
					<div class="nav-item"
						style="margin-top: 2px; margin-bottom: 2px; border-bottom: 1px solid #d6d6d6;">
						<a
							href="javascript:addTab('用户使用情况统计','${path}/sys/systemUserCount/manager.action','menu_icon_datadeal')">
							<span class="menu_icon_datadeal"></span> <span>用户使用情况统计</span>
						</a>
					</div>
					</shiro:hasPermission>
				</div>

			</div>

		</div>
		<div data-options="region:'center'" style="overflow: auto;">
			<div id="index_tabs" style="overflow: hidden;">
				<div title="首页" data-options="border:false"
					style="overflow: hidden; text-align: top; padding: 0em;">
					<img src="${base}/static/images/main.jpg" width="100%" />
				</div>
			</div>
		</div>
		<div data-options="region:'south',border:false"
			style="height: 30px; line-height: 30px; overflow: hidden; text-align: center; background-color: #eee">Copyright
			© 2017 power by gmcc</div>
	</div>

	<div id="tabsMenu" class="easyui-menu" style="width: 120px;">
		<div name="close">关闭</div>
		<div name="Other">关闭其他</div>
		<div name="All">关闭所有</div>
	</div>
	<!--[if lte IE 7]>
    <div id="ie6-warning"><p>您正在使用 低版本浏览器，在本页面可能会导致部分功能无法使用。建议您升级到 <a href="http://www.microsoft.com/china/windows/internet-explorer/" target="_blank">Internet Explorer 8</a> 或以下浏览器：
    <a href="http://www.mozillaonline.com/" target="_blank">Firefox</a> / <a href="http://www.google.com/chrome/?hl=zh-CN" target="_blank">Chrome</a> / <a href="http://www.apple.com.cn/safari/" target="_blank">Safari</a> / <a href="http://www.operachina.com/" target="_blank">Opera</a></p></div>
    <![endif]-->

	<style>
/*ie6提示*/
#ie6-warning {
	width: 100%;
	position: absolute;
	top: 0;
	left: 0;
	background: #fae692;
	padding: 5px 0;
	font-size: 12px
}

#ie6-warning p {
	width: 960px;
	margin: 0 auto;
}
</style>
</body>
</html>