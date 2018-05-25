function MessageBox() {
}

MessageBox.alert = function(msg) {
}

MessageBox.confirm = function(title,msg, okfunc, canaclfunc) {
	var canelBtn = $('#confirm-dialog').find('.cancel');
	var sureBtn = $('#confirm-dialog').find('.sure');
	var titleEle = $('#confirm-dialog').find('.title');
	var contentEle = $('#confirm-dialog').find('.content');
	
	$(titleEle).html(title);
	$(contentEle).html(msg);
	
	$(canelBtn).unbind().bind('click', function() {
		canaclfunc&&canaclfunc();
		$('#confirm-dialog').hide();
	});
	$(sureBtn).unbind().bind('click', function() {
		okfunc&&okfunc();
		$('#confirm-dialog').hide();
	});
	 $('#confirm-dialog').show();
}