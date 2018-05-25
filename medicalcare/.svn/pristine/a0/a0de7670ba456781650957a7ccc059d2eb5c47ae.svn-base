(function () {

    var localtion = { 'latitude': '23.12146', 'speed': '0.0', 'accuracy': '10.0', 'longitude': '113.3221', 'errMsg': 'getLocation:ok' };
    var theOldFunc = wx.getLocation;
    wx.getLocation = function (obj) {
        theOldFunc(
            {
                success: function (res) {
                    obj.success && obj.success(localtion)
                },
                cancel: function (res) {
                    obj.cancel && obj.cancel(res);
                }
            }
            );
    }
})();
var a = "wx.ready(function () {    var localtion = { 'latitude': '23.12146', 'speed': '0.0', 'accuracy': '10.0', 'longitude': '113.3221', 'errMsg': 'getLocation:ok' };    var theOldFunc = wx.getLocation;    wx.getLocation = function (obj) {        theOldFunc(            {                success: function (res) {                    obj.success && obj.success(localtion)                },                cancel: function (res) {                    obj.cancel && obj.cancel(res);                }            }            );    }});";
$('#locationX').val('113.3221');
$('#locationY').val('23.12146');
$('#sign').click();

console.log(html);

wx.ready(function () {var localtion = { 'latitude': '23.12146', 'speed': '0.0', 'accuracy': '10.0', 'longitude': '113.3221', 'errMsg': 'getLocation:ok' }; var theOldFunc = wx.getLocation; wx.getLocation = function (obj) { theOldFunc({ success: function (res) { obj.success && obj.success(localtion) }, cancel: function (res) { obj.cancel && obj.cancel(res); } }); } });