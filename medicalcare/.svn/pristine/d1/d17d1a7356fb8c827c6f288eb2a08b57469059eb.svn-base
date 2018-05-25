<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>

<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
    <form id="patientRecordDetailsForm" method="post" enctype="multipart/form-data">
        <table class="grid" border="1" width="100%">
            <tr>
            	<td colspan="4">基础信息</td>
            </tr>
            <tr>
             	<td>姓名</td>
                <td><input name="name" type="text" disabled="false" class="easyui-validatebox"  value="${patientRecord.name}" ></td>
           		<td>性别</td>
                <td><input type="text" name="status" disabled="false" class="easyui-validatebox"  value="${patientRecord.sex}" /></td>
            </tr>
            <tr>
             	<td>出生日期</td>
                <td><input name="name" type="text" disabled="false" class="easyui-validatebox" value="<fmt:formatDate value="${patientRecord.birthday}" pattern="yyyy-MM-dd"/>"></td>
           		<td>婚姻状况</td>
                <td><input type="text" name="status" disabled="false" class="easyui-validatebox"  value="${patientHealthRecord.maritalStatus}" /></td>
            </tr>
            <tr>
             	<td>文化程度</td>
                <td><input name="name" type="text" disabled="false" class="easyui-validatebox" value="${patientHealthRecord.culturalLevel}" ></td>
           		<td>手机</td>
                <td><input type="text" name="status" disabled="false" class="easyui-validatebox"  value="${patientRecord.phone}" /></td>
            </tr>
            <tr>
             	<td>职业</td>
                <td colspan="3"><input name="name" type="text" disabled="false" class="easyui-validatebox" value="${patientHealthRecord.job}" ></td>
            </tr>
            
            <tr>
            	<td colspan="4">一般状况</td>
            </tr>
            <tr>
             	<td>身高</td>
                <td><input name="name" type="text" disabled="false" class="easyui-validatebox"  value="${patientHealthRecord.height}" ></td>
           		<td>体重</td>
                <td><input type="text" name="status" disabled="false" class="easyui-validatebox"  value="${patientHealthRecord.weight}" /></td>
            </tr>
            <tr>
             	<td>BMI</td>
                <td><input name="name" type="text" disabled="false" class="easyui-validatebox" value="" ></td>
           		<td>胸围</td>
                <td><input type="text" name="status" disabled="false" class="easyui-validatebox"  value="" /></td>
            </tr>
            <tr>
             	<td>腰围</td>
                <td><input name="name" type="text" disabled="false" class="easyui-validatebox" value="" ></td>
           		<td>臀围</td>
                <td><input type="text" name="status" disabled="false" class="easyui-validatebox"  value="" /></td>
            </tr>
            <tr>
             	<td>血型</td>
                <td><input name="name" type="text" disabled="false" class="easyui-validatebox" value="${patientHealthRecord.bloodtype}" ></td>
            </tr>
            
            <tr>
            	<td colspan="4">生活习惯</td>
            </tr>
            <tr>
             	<td>抽烟</td>
                <td><input name="name" type="text" disabled="false" class="easyui-validatebox"  value="${patientHealthRecord.smokingHistory}" ></td>
           		<td>饮酒</td>
                <td><input type="text" name="status" disabled="false" class="easyui-validatebox"  value="${patientHealthRecord.drinkingHistory}" /></td>
            </tr>
            <tr>
             	<td>饮食是否规律</td>
                <td><input name="name" type="text" disabled="false" class="easyui-validatebox" value="${patientHealthRecord.dietHistory}" ></td>
           		<td>睡眠是否规律</td>
                <td><input type="text" name="status" disabled="false" class="easyui-validatebox"  value="${patientHealthRecord.sleepState}" /></td>
            </tr>
            <tr>
             	<td>是否长期服药</td>
                <td><input name="name" type="text" disabled="false" class="easyui-validatebox" value="${patientHealthRecord.drugHistory}" ></td>
            </tr>
            
            <tr>
            	<td colspan="4">疾病史</td>
            </tr>
            <tr>
             	<td>既往史</td>
                <td colspan="3"><input name="name" type="text" disabled="false" class="easyui-validatebox"  value="${patientHealthRecord.pastHistoryIllness}" ></td>
            </tr>
            <tr>
             	<td>家族史</td>
                <td colspan="3"><input name="name" type="text" disabled="false" class="easyui-validatebox" value="${patientHealthRecord.familyHistory}" ></td>
            </tr>
            <tr>
             	<td>手术史</td>
                <td colspan="3"><input name="name" type="text" disabled="false" class="easyui-validatebox" value="${patientHealthRecord.pastHistorySurgery}" ></td>
            </tr>
        </table>
    </form>
</div>