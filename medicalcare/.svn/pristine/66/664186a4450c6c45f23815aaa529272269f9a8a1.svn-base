var baseUrl = "";// "http://120.197.89.249:8080";
/*调试信息记录函数*/
function Debug() {

}

Debug.prototype.log = function (msg) {
    console && console.log(msg);
}
/***********************************************************************************************/
String.prototype.isMobilePhone = function () {
    var pattern = /^\d{11}$/ig;
    return pattern.test(this.toString());
}
String.prototype.isNumber = function () {
    var pattern = /^\d+$/ig;
    return pattern.test(this.toString());
}
String.prototype.isCMCCMobilePhone = function () {
    var isChinaMobile = /^1347[0-8]\d{7}$|^(?:13[5-9]|147|15[0-27-9]|178|18[2-478])\d{8}$/; //移动方面最新答复
    //var isChinaUnion = /^(?:13[0-2]|145|15[56]|176|18[56])\\d{8}$/; //向联通微博确认并未回复
    //var isChinaTelcom = /^(?:133|153|177|18[019])\\d{8}$/; //1349号段 电信方面没给出答复，视作不存在
    //var isOtherTelphone = /^170([059])\\d{7}$/;//其他运营商
   
    return isChinaMobile.test(this.toString());
}
/***********************************************************************************************/

function CallPostService(appUrl, postData, callback, headers) {
    //var loadingIndex = Messager.ShowLoading();

    var url = baseUrl + appUrl;
    $.ajax({
        url: url,
        type: 'post',
//        timeout: 300000,
        timeout: 10000,
        // contentType:"application/json",
        //headers: headers || {},
        data: postData,
        success: function (e) {
            // Messager.Close(loadingIndex);
            var theResult = e;
            callback && callback(theResult);

        },
        error: function (e) {
            //Messager.Close(loadingIndex);
            callback && callback({ retcode: 2, retmsg: '网络连接错误!' })


        }
    });
}

function CallGetService(appUrl, postData, callback, headers){
    //var loadingIndex = Messager.ShowLoading();
    var url = baseUrl + appUrl;
    $.ajax({
        url: url,
        type: 'post',
//        timeout: 300000,
        timeout: 10000,
        //contentType: "application/json",
        // headers: headers || {},
        data: postData,
        success: function (e) {
            // Messager.Close(loadingIndex);
            var theResult = e;
            callback && callback(theResult);

        },
        error: function (e) {
            //Messager.Close(loadingIndex);
            callback && callback({ retcode: 2, retmsg: '网络连接错误!' })
            console.log(theResult)


        }
    });
}
/*异步上传文件*/
function CallFileService(appUrl, postData, file, callback) {
    //var loadingIndex = Messager.ShowLoading();
    if (!file) {
        return CallPostService(appUrl, postData, callback);
    }
    submitByIframe(appUrl, file, postData, callback);
}


function updateField(ele, data) {
    
    $(ele).find('.field').each(function () {
        var field = $(this).data('field') || '';
        if (!field) {
            return;
        }
        var value = eval("data." + field);
       
        if (typeof (value) == "undefined") {
            value = '';
        }
        if ($(this).is('input')) {
           
            $(this).val(value);
          
        }
        else if($(this).is('img')) {
            $(this).attr('src', value);
          
        }
      
        else {
            $(this).html(value);
            
        }
        
    });
}



function gotoUrl(url) {
    if ($('base').length > 0) {
        var baseUrl = $('base').attr('href') || "";
        location.href = baseUrl + url;
    }
    else {
        location.href = url;
    }
}
function gotoBack() {
    history.go(-1);
}
function gotoLocation(hash) {
    location.hash = hash;
}
function loadContent(ele, paramStr) {
    var url = $(ele).data('url') + (paramStr || "");
    $(ele).load(url);
}
$(function () {
    $('#loading-div').each(function (e) {
     
        loadContent(this);

    });

});


$(function () {
    bodyFontSize = parseInt($("html").css("font-size").replace("px", ""));
    $(window).resize(function () {
        initSize();
    });
    initSize();
    function initSize() {
        var currentWidth = $(window).width();
        fontSize = bodyFontSize * (currentWidth / 1080);
        $("html").css("font-size", fontSize + "px");
    }
});


function using(url) {
    window.urlMap = {};
    if (urlMap[url]) {
        return;
    }
    else {
        urlMap[url] = true;
    }
    $('<script src=' + url + '></script>').appendTo($(document.body));
}



function submitByIframe(url,fileid,postdata,callback)
{
    var theForm = $('#' + fileid).closest('form');

    var frameId = 'easyui_frame_' + (new Date().getTime());
    var frame = $('<iframe id=' + frameId + ' name=' + frameId + '></iframe>').appendTo('body')
    frame.attr('src', window.ActiveXObject ? 'javascript:false' : 'about:blank');
    frame.css({
        position: 'absolute',
        top: -1000,
        left: -1000
    });
    frame.bind('load', cb);

    submit(postdata);

    function submit(param) {
        var form = $(theForm);
        form.attr('action', url);
        form.attr('method', 'post');
        form.attr('enctype', "multipart/form-data");
        var t = form.attr('target'), a = form.attr('action');
        form.attr('target', frameId);
        var paramFields = $();
        try {
            for (var n in param) {
                var field = $('<input type="hidden" name="' + n + '">').val(param[n]).appendTo(form);
                paramFields = paramFields.add(field);
            }
            checkState();
            form[0].submit();
        } finally {
            form.attr('action', a);
            t ? form.attr('target', t) : form.removeAttr('target');
            paramFields.remove();
        }
    }

    function checkState() {
        var f = $('#' + frameId);
        if (!f.length) { return }
        try {
            var s = f.contents()[0].readyState;
            if (s && s.toLowerCase() == 'uninitialized') {
                setTimeout(checkState, 100);
            }
        } catch (e) {
            cb();
        }
    }

    var checkCount = 10;
    function cb() {
        var f = $('#' + frameId);
        if (!f.length) { return }
        f.unbind();
        var data = '';
        try {
            var body = f.contents().find('body');
            data = body.html();
            if (data == '') {
                if (--checkCount) {
                    setTimeout(cb, 100);
                    return;
                }
            }
            var ta = body.find('>textarea');
            if (ta.length) {
                data = ta.val();
            } else {
                var pre = body.find('>pre');
                if (pre.length) {
                    data = pre.html();
                }
            }
        } catch (e) {
        }
        callback && callback(JSON.parse(data));
        setTimeout(function () {
            f.unbind();
            f.remove();
        }, 100);
    }

}

  



function getTime(time) {
   
   
    date = new Date(time),
    nowDate = new Date();
    nowDate1 = nowDate.getTime();
    nowH = nowDate.getHours();
    var today = (1000 * 60 * 60 * nowH);
    var yorday = (1000 * 60 * 60 * nowH) + (1000 * 60 * 60 * 24)

    if (nowDate1 - time <today) {
        Y = date.getFullYear(),
        M = date.getMonth() + 1,
        D = date.getDate(),
        H = date.getHours(),
        m = date.getMinutes();
        //小于10的在前面补0
        if (H < 12) {
            H = '上午 ' + H;
        }
        else {
            H = '下午 ' + (H - 12);
        }
        if (m < 10) {
            m = '0' + m;
        }
        return H + ":" + m;
    } else if (today<(nowDate1-time) && (nowDate1 - time)<yorday) {
        console.log(nowDate1-time - yorday)
        Y = date.getFullYear(),
        M = date.getMonth() + 1,
        D = date.getDate(),
        H = date.getHours(),
        m = date.getMinutes();
        //小于10的在前面补0       
        if (H < 12) {
            H = '上午 ' + H;
        }
        else {
            H = '下午 ' + (H - 12);
        }
        if (m < 10) {
            m = '0' + m;
        }
        return "昨天" + H + ":" + m;
    } else {
        Y = date.getFullYear(),
        M = date.getMonth() + 1,
        D = date.getDate(),
        H = date.getHours(),
        m = date.getMinutes();

        //小于10的在前面补0
        if (M < 10){
            M = '0' + M;
        }
        if (D < 10) {
            D = '0' + D;
        }
        if (H < 12) {
            H = '上午 ' + H;
        }
        else {
            H = '下午 ' + (H - 12);
        }
        if (m < 10) {
            m = '0' + m;
        }
        
        return Y+"年"+M + '月' + D + '日' + H + ":" + m;

    }

}


