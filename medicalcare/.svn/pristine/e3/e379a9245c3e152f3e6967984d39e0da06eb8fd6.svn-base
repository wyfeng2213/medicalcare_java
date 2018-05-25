/**
 * 初始化输入框提示
 */
$(document).ready(function(){
	$("[tip]").each(function() {
		$(this).val($(this).val()||$(this).attr("tip"));
	});
	$("[tip]").live("focus",function(){//获得焦点
		var value=$.trim($(this).val());
		var tip= $(this).attr("tip");
		if(value==tip){
			$(this).val("");
		}
	});
	$("[tip]").live("blur",function(){//失去焦点
		var value=$.trim($(this).val());
		var tip= $(this).attr("tip");
		if(value==""){
			$(this).val(tip);
		}
	});
	
	//textarea 增加maxLength属性
	$("textarea[maxLength]").keydown(function() {
		var maxLength = $(this).attr("maxLength");
		var curLength=$(this).val().length;
		if (curLength > maxLength) {
			$(this).val($(this).val().substr(0, maxLength));
		}

	});

});

