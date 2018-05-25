<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function() {
    $('#mediaStoreEditForm').form({
        url : '${path }/crm/mediaStore/edit.do',
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
    
    var val = document.getElementById("mchtId").value;
    if (val == 'youku'){
    	document.getElementById("m2").style.visibility="visible";
    }
    if (val == 'bestv'){
    	document.getElementById("m2").style.visibility="hidden";
    }
    
});


function test(obj){
	var searchLabel = document.getElementById("searchLabel").value;
	var text;
	if ( obj.checked == true){
		if(searchLabel==''){
			text = obj.value;
		}else{
			text = searchLabel+","+obj.value;
		}
	}else{
		var arr;
		arr = searchLabel.split(",");
		$.each(arr,function(index,item){  
            if(item==obj.value){
            	arr.splice(index,1);
       	    }
     	});
		text = arr.join(",");
	}
	document.getElementById("searchLabel").innerHTML=text;
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
    }
});       
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="mediaStoreEditForm" method="post" enctype="multipart/form-data">
            <table class="grid">
                <tr>
                    <td>商家ID</td>
                    <td>
                     <select id="mchtId" name="mchtCode" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" >
                           <c:forEach var="id" items="${mediaMcht}">
                           		<c:choose>
						           <c:when test="${id.mchtCode==mediaStore.mchtCode }">
						                <option value="${id.mchtCode}"  selected>${id.mchtName}</option>
						           </c:when>
						           <c:otherwise>
						                <option value="${id.mchtCode}">${id.mchtName}</option>
						           </c:otherwise>
						        </c:choose> 
                           </c:forEach>
                      </select>                   
                    </td>
                    <td>频道ID</td>
                    <td >
                        <select id="categoryId" name="categoryId" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" >
                          <c:forEach var="id" items="${mediaCategory}">
                           		<c:choose>
						           <c:when test="${id.id==mediaStore.categoryId }">
						                <option value="${id.id}"  selected>${id.categoryName}</option>
						           </c:when>
						           <c:otherwise>
						                <option value="${id.id}">${id.categoryName}</option>
						           </c:otherwise>
						        </c:choose> 
                           </c:forEach>
                        </select>
                    </td>
                                     
                </tr>
                <tr>               
                    <td>缩略图</td>
                    <td colspan="3"><input type="text" id="thumbnail" name="thumbnail" class="easyui-validatebox" data-options="required:true" value="${mediaStore.thumbnail}" validtype="url" style="width:70%;"/>               
                    <a id="m1" href="javascript:void(0);" onclick="querythumbnail()"  class="easyui-linkbutton" plain="true" iconCls="icon-search" >预览缩略图</a></td>
                </tr>
                <tr>
                	<td>视频URL</td>
                    <td colspan="3"><input type="text" id="mediaPlayUrl" name="mediaPlayUrl" class="easyui-validatebox" data-options="required:true" value="${mediaStore.mediaPlayUrl}" validtype="url" style="width:70%;"/>
                    <a id="m2" href="javascript:void(0);" onclick="watchVedio()"  class="easyui-linkbutton" plain="true" iconCls="icon-search">观看视频</a></td>
                </tr>
                <tr>
                    <td>视频ID</td>
                    <td><input type="text" name="mediaPlayId" class="easyui-validatebox" data-options="required:true" value="${mediaStore.mediaPlayId}" validtype="length[0,22]"/></td>
                    <td>时长</td>
                    <td><input type="text" id="duration" name="duration" class="easyui-numberbox" data-options="required:true" value="${mediaStore.duration}" validtype="length[0,11]"/></td>
                </tr>
                <tr>
                 	<td>视频名称</td>
                    <td><input name="name" type="text" placeholder="请输入视频名称" class="easyui-validatebox" data-options="required:true" value="${mediaStore.name}" validtype="length[0,255]"></td>
               		<td>状态</td>
                    <td><input type="text" name="status" class="easyui-validatebox" data-options="required:true" value="${mediaStore.status}" validtype="length[0,4]"/></td>
                </tr>
                <tr>
                    <td>视频说明</td>           
					<td colspan="3"><textarea id="detail" name="detail" rows="4" cols="4" style="width:100%;" validtype="length[0,1024]">${mediaStore.detail}</textarea></td>
                </tr>
                <tr>
                    <td>发布日期</td>
                    <td><input id="issueDate" name="issueDate" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" value="<fmt:formatDate  value="${mediaStore.issueDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
                    <td>优先级</td>
                    <td><input name="rank" class="easyui-numberspinner" data-options="editable:false" value="${mediaStore.rank}"/></td>
                </tr>
                <tr>
                    <td>付费类型</td>
                    <td >
                        <select id="chargeType" name="chargeType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
				                <option value="0" <c:if test="${mediaStore.chargeType=='0'}">selected='selected'</c:if>>免费</option>
				                <option value="1" <c:if test="${mediaStore.chargeType=='1'}">selected='selected'</c:if>>付费</option>
                        </select>
                    </td>
                    <td>描述</td>
                    <td><textarea id="remark" name="remark" rows="" cols="" validtype="length[0,255]">${mediaStore.remark}</textarea></td>
                </tr>
                <tr>
                    <td>选择搜索关键字</td>
                    <td >
						<c:forEach var="val" items="${labels}" >
						<input type="checkbox" id="ch1" name="na1" value="${val}" onclick="test(this)" checked="checked"/>${val}
						</c:forEach>
                    </td>
                  	<td>视频类型</td>
                    <td >
                        <select id="mediaType" name="mediaType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
				                <option value="0" <c:if test="${mediaStore.mediaType=='0'}">selected='selected'</c:if>>单个视频</option>
				                <option value="2" <c:if test="${mediaStore.mediaType=='2'}">selected='selected'</c:if>>组视频</option>
				                <option value="3" <c:if test="${mediaStore.mediaType=='3'}">selected='selected'</c:if>>连续剧</option>
                        </select>
                    </td>                
                </tr>
                <tr>
                    <td>搜索关键字合并</td>
                    <td colspan="3"><textarea id="searchLabel" name="searchLabel" rows="" cols="" validtype="length[0,1024]" style="width:70%;">${mediaStore.searchLabel} </textarea>
                    <a style="color:red;">(用英文逗号分割)</a></td>
                <tr>
                    <td><input name="id" type="hidden"  value="${mediaStore.id}">
                </tr>

            </table>
        </form>
    </div>
</div>