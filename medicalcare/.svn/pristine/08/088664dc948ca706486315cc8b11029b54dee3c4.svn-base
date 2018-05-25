<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="easyui-layout" data-options="fit:true,border:false">
	<div>
		<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
	        <form id="patientRecordDetailsForm" method="post" enctype="multipart/form-data">
	            <table class="grid">
	                <tr>
	                 	<td width=50 height=50><img src="${patientRecord.headUrl}" width=50 height=50 alt="" /></td>
	                 	<td width=50 height=50>${patientRecord.name}</td>
	                 	<td width=50 height=50>${patientRecord.phone}</td>
	                </tr>
	            </table>
	        </form>
	    </div>
	</div>
	
	<div>
		<div class="easyui-tabs" id="tabsMain" data-options="tabWidth:112">  
	    	<div title="健康档案" style="padding:10px" data-options="href:'${path}/sys/patientRecord/getDetailsHealthRecord.action?patientLoginId=${patientRecord.loginId}'">
	    	</div> 
	    	<div title="检验检查记录" style="padding:10px">
				<iframe src="${path}/sys/patientRecord/gotoMedicalRecordDetailsPage.action?patientLoginId=${patientRecord.loginId}" style="width:99%;height:440px">
	        	</iframe>
	        </div> 
	        <div title="监测数据" style="padding:10px">即将上线，敬请期待
	        </div>  
	        <div title="问诊记录" style="padding:10px">即将上线，敬请期待
	        </div>  
		</div>
	</div>
</div>