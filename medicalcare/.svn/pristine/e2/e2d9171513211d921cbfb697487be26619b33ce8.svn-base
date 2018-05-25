mui.extend({
    // $.delHtmlTag();
    delHtmlTag: function (str) {
        //去掉所有的html标记
        return str.replace(/<[^>]+>/g, "");
    },
    getCookie: function (name) {
        //获取cookie
        var arr,
	    	reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg))
            return unescape(arr[2]);
        else
            return null;
    },
    setCookie: function (c_name, value, expiredays) {
        //设置cookie
        var exdate = new Date()
        exdate.setDate(exdate.getDate() + expiredays)
        document.cookie = c_name + "=" + escape(value) +
	    ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
    },
    clearCookie: function () {
        var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
        if (keys) {
            for (var i = keys.length; i--;)
                document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString()
        }
    },
    getQueryString: function (name) {
        //获取url参数
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    },
    toast: function (config) {
        var msgEntity,
			context = config.context == null ? mui('body')[0] : config.context,//上下文
			message = config.message,//显示内容
			time = config.time == null ? 3000 : config.time;//持续时间
        var init = function () {
            var _toastMessage = mui("#toastMessage")[0];
            if (_toastMessage) {
                _toastMessage.parentNode.removeChild(_toastMessage);
            }

            //设置消息体
            var span = document.createElement('span');
            span.innerHTML = message;
            var toastMessage = document.createElement('div');
            toastMessage.id = 'toastMessage';
            toastMessage.appendChild(span);
            toastMessage.style.display = 'none';
            document.querySelector("#denglu").appendChild(toastMessage);
        }

        init();
        //显示动画
        return function show(time) {
            var toastMessage = document.querySelector("#toastMessage");
            toastMessage.style.display = 'block';
            setTimeout(function () {
                toastMessage.style.display = 'none';
            }, 2000)
        }

    }
});


/**
 * 模仿android里面的Toast效果，主要是用于在不打断程序正常执行的情况下显示提示数据
 * @param config
 * @return
 */
var Toast = function (config) {
    this.context = config.context == null ? $('body') : config.context;//上下文
    this.message = config.message;//显示内容
    this.time = config.time == null ? 3000 : config.time;//持续时间
    this.init();


}

var msgEntity;

Toast.prototype = {
    //初始化显示的位置内容等
    init: function () {
        $("#toastMessage").remove();
        //设置消息体
        var msgDIV = new Array();
        msgDIV.push('<div id="toastMessage">');
        msgDIV.push('<span>' + this.message + '</span>');
        msgDIV.push('</div>');
        msgEntity = $(msgDIV.join('')).appendTo(this.context);
        //设置消息样式
        var left = this.left == null ? this.context.width() / 2 - msgEntity.find('span').width() / 2 : this.left;
        var top = this.top == null ? '20px' : this.top;
        /*msgEntity.css({'position':'fixed','top':'0','z-index':'9999','left':'0','right':'0','bottom':'0','margin':'auto','background':'rgba(0,0,0,0.5)',color:'white','font-size':'0.18rem',padding:'0.1rem',height:'0.4rem',width:'2.5rem','line-height':'0.4rem'});*/
        msgEntity.hide();
    },
    //显示动画
    show: function () {
        msgEntity.fadeIn(this.time / 3);
        msgEntity.fadeOut(this.time / 1);
    }
}

function updateField(ele, data) {
    //var obj = {};
    //for (var key in data) {
    //    obj[(key + "").toUpperCase()] = data[key];
    //}

    $(ele).find('.field').each(function () {

        var field = $(this).data('field') || '';
        //field = field.toUpperCase();
        var value = eval("data." + field);
        if (typeof (value) == "undefined") {
            value = '';
        }
        if ($(this).is('input')) {

            $(this).val(value);
            $(this).attr(value);
        } else {
            $(this).html(value);
        }
    });
}
/**
*跳转页面到指定的地址
*/
function Go(url) {
    location.href = url;
}
/*获取页面传递的参数*/
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}
/**
保存数据
*/
function setItem(key, value) {
    sessionStorage.setItem(key, value);
}
/*获取数据**/
function getItem(key) {
    return sessionStorage.getItem(key);
}

/*浏览器判断代码*/
var Browser = {};
/*是否是微信打开*/
Browser.isWeiXin = function () {
    var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
    if (ua.match(/MicroMessenger/i) == "micromessenger") {
        return true;
    }
    return false;
};
/*是否是qq打开*/
Browser.isQQ = function () {
    var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
    if (ua.match(/QQ/i) == "qq") {
        return true;
    }
    return false;
};
