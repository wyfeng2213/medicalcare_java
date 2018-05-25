
function Messager() {

}
Messager.Msg = function (msg) {
    //layer.alert(msg);
    //alert(msg);
    layer.open({
        content: msg
        , skin: 'msg'
        , time: 2 //2秒后自动关闭
    });
}
Messager.Alert = function (msg) {
    layer.open({
        content: msg
        , btn: '确  定'
    });
    //alert(msg);
}
Messager.ShowLoading = function () {
    return layer.open({ type: 2, shadeClose: false });
}
Messager.Close = function (index) {
    layer.close(index);
}
Messager.CloseDialog = function () {
    $('.dialog-panel-bg:last()').remove();
}
Messager.CloseAll = function (index) {
    layer.closeAll(index);
}
Messager.Show = function (ele, style) {
    var theHtml = $(ele).html();
    var panel_bg = $("<div class='dialog-panel-bg'/>")
    var panel_wrap = $("<div class='dialog-panel-wrap'/>")
    panel_wrap.wrapInner($(theHtml));
    panel_bg.wrapInner(panel_wrap);

    $(panel_bg).appendTo(document.body);
    $(panel_bg).show();
}