<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#mediaSubsettAddForm').form({
            url : '${path }/crm/subset/add.do',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
        
        $("#mediaPlayId").blur(function(){
        	var mediaPlayId = $("#mediaPlayId").val();
        	var mchtCode = $("#mchtCode").val();
        	//var mchtCode = ${mediaStore.mchtCode};
        	$.ajax({ 
        		url: '${path }/crm/subset/findSubsetDetailPage.do', 
        		dataType: "json",   //返回格式为json
        	    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
        	    data: { "mediaPlayId": mediaPlayId ,"mchtCode":mchtCode},    //参数值
        	    type: "POST",   //请求方式
        		success: function(date){
        			mchtCode = date.mchtCode;
               		if(mchtCode=="youku"){
                		if(date.player){
	                		$("#mediaPlayUrl").val(date.player);
                		}else{
	                		$("#mediaPlayUrl").val("");
	                	}
               		}else if(mchtCode=="bestv"){
               			if(date.playurl){
	                		$("#mediaPlayUrl").val(date.playurl);
                		}else{
	                		$("#mediaPlayUrl").val("");
	                	}
            		}
                	if(date.title){
                		$("#name").val(date.title);
                	}else{
                		$("#name").val("");
                	}
               		if(mchtCode=="youku"){
	                	if(date.duration){
	                		$("#duration").val(parseInt(date.duration));
	                	}else{
	                		$("#duration").val("");
	                	}
               		}else if(mchtCode=="bestv"){
               			if(date.length){
               				$("#duration").val(date.length);
	                	}else{
	                		$("#duration").val("");
	                	}
            		}
               		if(mchtCode=="youku"){
               			if(date.description){
	                		$("#detail").html(date.description);
	                	}else{
	                		$("#detail").html("");
	                	}
               		}else if(mchtCode=="bestv"){
               			if(date.descinfo){
               				$("#detail").html(date.descinfo);
	                	}else{
	                		$("#detail").html("");
	                	}
            		}
               		if(mchtCode=="youku"){
                		if(date.published){
	                		$("#issueDate").val(date.published);
	                	}else{
	                		$("#issueDate").val("");
	                	}
                	}else if(mchtCode=="bestv"){
                		if(date.cTime){
                			$("#issueDate").val(format(date.cTime*1000));
	                	}else{
	                		$("#issueDate").val("");
	                	}
            		}
               		if(mchtCode=="youku"){
                		if(date.thumbnail){
	                		$("#thumbnail").val(date.thumbnail);
	                	}else{
	                		$("#thumbnail").val("");
	                	}
               		}else if(mchtCode=="bestv"){
               			if(date.small_image1){
               				$("#thumbnail").val(date.small_image1);
	                	}else{
	                		$("#thumbnail").val("");
	                	}
            		}
              	}
        	});
       	});
        
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
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
        <form id="mediaSubsettAddForm" method="post">
            <table class="grid">
            	<tr>
            		<td>视频ID</td>
                    <td  colspan="3"><input id="mediaPlayId" name="mediaPlayId" type="text" placeholder="请输入视频ID" validType="length[1,128]" style="width:100%;" class="easyui-validatebox span2" data-options="required:true" value=""></td>
            	</tr>
            	<tr>
            		<td>视频名</td>
                    <td colspan="3"><input id="name" name="name" type="text" placeholder="请输入视频名" validType="length[1,255]" class="easyui-validatebox span2" style="width:100%;" data-options="required:true" value=""></td>
            	</tr>
            	<tr>
                    <td>视频URL</td>
                    <td colspan="3"><input id="mediaPlayUrl" name="mediaPlayUrl" type="text" placeholder="请输入视频URL" validType="length[1,1024]" style="width:100%;" class="easyui-validatebox span2" data-options="required:false" value=""></td>
            	</tr>
            	<tr>
                	<td>缩略图</td>
                	<td  colspan="3">
                		<input type="text" id="thumbnail" name="thumbnail" placeholder="请输入缩略图地址" style="width:100%;">
                	</td>
            	</tr>
            	
            	<tr>
            		<td>视频说明</td>
            		<td colspan="4"><textarea id="detail" name="detail" maxlength="1024" rows="4" cols="" style="width: 100%;"  placeholder="请输入视频说明"></textarea></td>
            	</tr>
                <tr>
                	<td>发布日期</td>
                    <td><input id="issueDate" name="issueDate" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" /></td>
                    <td>时长</td>
                    <td><input id="duration" name="duration" type="text" placeholder="请输入视频时长" validType="length[1,11]" class="easyui-validatebox span2" data-options="required:true" value=""></td>
                </tr>
                <tr>
                    <td>优先级</td>
                    <td><input name="mediaIndex" value="0" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false"></td>
                    <td>付费类型</td>
                    <td >
                        <select id="chargeType" name="chargeType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
				                <option value="0" >免费</option>
				                <option value="1" >付费</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3"><textarea name="remark" rows="4"  style="width: 100%;" cols="" maxlength="255"></textarea></td>
                    <td><input name="parentMediaId" type="hidden"  value="${mediaStore.id}">
                    <td><input id="mchtCode" type="hidden"  value="${mediaStore.mchtCode}">
                </tr>
            </table>
        </form>
    </div>
</div>