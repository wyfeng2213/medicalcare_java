/**
 * 分页JS
 * @autor mikel
 */
$(document).ready(function() {
	var pagerForm = $("#pagerForm");
	//初始化分页Form信息
	pagerForm[0].action = document.forms[0].action;
	appendFormParams();
	
	
	
});



/**
 * 跳转到指定页数
 * @param pageNo
 */
function pageGoto(pageNo) {
	var form = $("#pagerForm");
	if(pageNo <= 0){
		pageNo = $("#pageIndex").val();
	}
	var total = new Number($("#total").text());
	if(pageNo > total){
		pageNo = total;
	}
	submitPagerForm(form, pageNo);
}

/**
 * 将查询form表单的元素copy到分页form
 */
function appendFormParams(){
	var form = document.forms[0];
	var itemValue = "";
	for(var i=0;i<form.length;i++ ){
		var item = form[i];
		if( item.name!='' ){
			if( item.type == 'select-one' ){
				itemValue = item.options[item.selectedIndex].value;
			}else if( item.type=='checkbox' || item.type=='radio') {
				if ( item.checked == false ){
					continue; 
				}
				itemValue = item.value;
			}else if( item.type == 'button' || item.type == 'submit' || item.type == 'reset' || item.type == 'image'){// ignore this type
				continue;
			}else{
				itemValue = item.value;
			}
			if(itemValue != '' && itemValue != $(item).attr("tip")){
				$("<input type='hidden' name = '"+item.name+"' value='"+
						itemValue+"' />").appendTo($("#pagerForm"));
			}
		}
	}
}

/**
 * 提交分页表单
 * @param pagerForm
 * @param pageNum
 */
function submitPagerForm(pagerForm, pageNum) {
	pagerForm.find("[name='pageIndex']").val(pageNum);
	pagerForm.submit();
}

/*只能输入数字，可按退格键删除数字*/
function vaildNumber(evnt){
	evnt=evnt||window.event;
	var keyCode=window.event?evnt.keyCode:evnt.which;
	return keyCode>=48&&keyCode<=57||keyCode==8||keyCode==13;
}
