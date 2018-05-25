<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
document.getElementById("m2").style.visibility="visible";
document.getElementById("mediaGroup_id").style.visibility="hidden";
var flag = document.getElementById("mchtId").value;
    $(function() {
        $('#mediaStoreAddForm').form({
            url : '${path }/crm/mediaStore/add.do',
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
                    parent.$.messager.alert('提示', result.msg, 'warning');
                }
            }
        });    
        
    });
 
    
	function queryMedia(){
		var v = document.getElementById("mediaPlayId").value;
		if(v==''){
			alert("请输入视频id查询");
		}
		var val = flag;
		$.ajax({ 
    		url: '${path }/crm/mediaStore/addmedia.do', 
    		dataType: "json",   //返回格式为json
    	    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
    	    data: { "mediaId": v ,"mchtId":val},    //参数值
    	    type: "POST",   //请求方式
    		success: function(date){
   			if (flag=='youku'){
	            	if(date.player){
	            		$("#mediaPlayUrl").val(date.player);
	            	}else{
	            		$("#mediaPlayUrl").val("");
	            	}
	            	if(date.title){
	            		$("#name").val(date.title);
	            	}else{
	            		$("#name").val("");
	            	}
	            	if(date.duration){
	            		$("#duration").val(parseInt(date.duration));
	            	}else{
	            		$("#duration").val("");
	            	}
	            	if(date.description){
	            		$("#detail").val(date.description);
	            	}else{
	            		$("#detail").val("");
	            	}
	            	if(date.published){
	            		$("#issueDate").val(date.published);
	            	}else{
	            		$("#issueDate").val("");
	            	}
	            	if(date.bigThumbnail){
	            		$("#thumbnail").val(date.bigThumbnail);
	            	}else{
	            		$("#thumbnail").val("");
	            	}
    			}else if (flag=='bestv'){
    				if(date.playurl){
	            		$("#mediaPlayUrl").val(date.playurl);
	            	}else{
	            		$("#mediaPlayUrl").val("");
	            	}
	            	if(date.title){
	            		$("#name").val(date.title);
	            	}else{
	            		$("#name").val("");
	            	}
	            	if(date.duration){
	            		$("#duration").val(parseInt(date.duration));
	            	}else{
	            		$("#duration").val("");
	            	}
	            	if(date.descinfo){
	            		$("#detail").val(date.descinfo);
	            	}else{
	            		$("#detail").val("");
	            	}
	            	if(date.cTime){
	            		$("#issueDate").val(formatterdate(date.cTime*1000));
	            	}else{
	            		$("#issueDate").val("");
	            	}
	            	if(date.small_image1){
	            		$("#thumbnail").val(date.small_image1);
	            	}else{
	            		$("#thumbnail").val("");
	            	}
    			}
          	}
    	});

	}
	
	function querythumbnail(){
		var url = document.getElementById("thumbnail").value;
		if (url != ''){
			window.open (url, "newwindow", "height=200, width=400,top=250, left=450, toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no"); //写成一行
		}
	}
	
	function watchVedio(){
		var url = document.getElementById("mediaPlayUrl").value;
		if (url != ''){
			window.open (url); //写成一行
		}
	}
	
	
	$('#mchtId').combobox({
        onSelect:function(record){
        	var peo = $('#mchtId').combobox('getValue');
   		 	if (peo=='bestv'){  		 		
   		 		document.getElementById("m2").style.visibility="hidden";
   		 	}
   		 	if (peo=='youku'){
   		 		document.getElementById("m2").style.visibility="visible";
   		 	}
		 	flag = peo;
        }
});       
	
	$('#mediaType').combobox({
        onSelect:function(record){
                var peo = $('#mediaType').combobox('getValue');
                if(peo=="0"){						
                	document.getElementById("mediaGroup_id").style.visibility="hidden";
                }
                else{
                	document.getElementById("mediaGroup_id").style.visibility="visible";
                }
        }
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
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="mediaStoreAddForm" method="post" enctype="multipart/form-data">
            <table class="grid">
            	<tr>
            	    <td>商家名称</td>
                    <td>
                     <select id="mchtId" name="mchtCode" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" >
                         <c:forEach var="mcht" items="${mediaMcht}">
                         		<option value="${mcht.mchtCode}">${mcht.mchtName}</option>
                         </c:forEach>
                      </select>                   
                    </td> 
                    <td>频道名称</td>
                    <td>
                    	<select id="categoryId" name="categoryId" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" >
	                         <c:forEach var="mediaCategory" items="${mediaCategory}">
	                         		<option value="${mediaCategory.id}">${mediaCategory.categoryName}</option>
	                         </c:forEach>
                      	</select>
                    </td>
            	</tr>
                <tr>
                    <td>视频ID</td>
                    <td colspan="3"><input type="text" id="mediaPlayId" name="mediaPlayId" class="easyui-validatebox" data-options="required:true" validtype="length[0,22]" style="width:70%;" value=""/>
                    <a href="javascript:void(0);" onclick="queryMedia()"  class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a></td>
                </tr>
                <tr>               
                    <td>缩略图</td>
                    <td colspan="3"><input type="text" id="thumbnail" name="thumbnail" class="easyui-validatebox" data-options="required:true" validtype="url" style="width:70%;" value=""/>               
                    <a id="m1" href="javascript:void(0);" onclick="querythumbnail()"  class="easyui-linkbutton" plain="true" iconCls="icon-search">预览缩略图</a></td>
                </tr>
                <tr>
                	<td>视频URL</td>
                    <td colspan="3"><input type="text" id="mediaPlayUrl" name="mediaPlayUrl" class="easyui-validatebox" data-options="required:true" validtype="url" style="width:70%;"/>
                    <a id="m2" href="javascript:void(0);" onclick="watchVedio()"  class="easyui-linkbutton" plain="true" iconCls="icon-search">观看电影</a></td>
                </tr>
                <tr>               	
                    <td>时长</td>
                    <td><input type="text" id="duration" name="duration"  class="easyui-validatebox span2" data-options="required:true"  validtype="length[0,11]"  value="" ></td>
               		<td>发布日期</td>
                    <td><input id="issueDate" id="issueDate" name="issueDate" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" /></td>
                </tr>
                <tr>   
                    <td>视频说明</td>
                    <td colspan="3"><textarea id="detail" name="detail" style="width:100%;" rows="4" cols="4" validtype="length[0,512]"></textarea></td>
                </tr>
                <tr>
                	<td>视频名称</td>
                    <td><input id="name" name="name"  placeholder="请输入视频名称" class="easyui-validatebox" data-options="required:true" validtype="length[0,255]" value=""></td>
  					<td>优先级</td>
                    <td><input name="rank" value="0" class="easyui-numberspinner" data-options="editable:false" value=""/></td>                  
                </tr>
                <tr>
                    <td>付费类型</td>
                    <td >
                        <select id="chargeType" name="chargeType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
				                <option value="0" >免费</option> 
				                <option value="1" >付费</option>
                        </select>
                    </td>
                    <td>描述</td>
                    <td><textarea id="remark" name="remark" rows="" cols="" validtype="length[0,255]"></textarea></td>       
                </tr>
                <tr>
                    <td>搜索关键字</td>
                    <td colspan="3"><textarea id="searchLabel" name="searchLabel" rows="" cols="" validtype="length[0,1024]" style="width:70%;"></textarea>     
                    <a style="color:red;" >(用英文逗号分割)</a></td>       
                </tr>
                <tr>
                    <td>视频类型</td>
                    <td >
                        <select id="mediaType" name="mediaType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
				                <option value="0" >单个视频</option>
				                <option value="2" >组视频</option>
				                <option value="3" >连续剧</option>
                        </select>
                    </td>
                       <td>视频索引</td>
                    <td >
                       <input type="text" id="mediaIndex" name="mediaIndex"  class="easyui-numberspinner"  data-options="required:true"  validType="length[1,22]"   value="0"  placeholder="默认为0" />    
                    </td>
                </tr>
                <tr id="mediaGroup_id">
                    <td>组标识</td>
                    <td >
                       <input type="text" id="mediaGroup" name="mediaGroup"   data-options="required:false"    placeholder="同组视频标识相同"  />    
                    </td>
                </tr>
            </table>
            <div id="youkuplayer" style="width:100%;height:420px"></div>
        </form>
    </div>
</div>