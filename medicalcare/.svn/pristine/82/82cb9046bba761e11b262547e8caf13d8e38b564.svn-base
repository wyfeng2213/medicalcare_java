function invokeService(url, postData, callback) {
	$.ajax({
		url : url,
		type : 'post',
		timeout : 300000,
		contentType: "application/json",
		data : JSON.stringify(postData),
		success : function(e) {
			var theResult = JSON.parse(e);
			callback(theResult);
		},
		error : function(xhr, errorInfo, ex) {
			//alert("网络连接错误!");
			//alert('error invoke!errorInfo:' + errorInfo);
			var error = '{"retcode":-1,"retmsg":"系统繁忙，请稍后再试！"}';
			if(errorInfo=='timeout')
			{
				error = '{"retcode":-1,"retmsg":"系统繁忙，请稍后再试！"}';				
			}
			
			var theResult = JSON.parse(error);
			callback(theResult);
		}
	});
}
