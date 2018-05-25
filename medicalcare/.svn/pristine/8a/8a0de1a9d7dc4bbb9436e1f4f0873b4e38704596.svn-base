//2017-7-16
function HuanXin() {
    var innerMessageHander = null;//接收消息处理
    var conn = null;
    var me = this;

    var handleEmoji = function (str) {
        str = str.replace(/\[.*?\]/g, function (key) {
            if (ChatFaces.map[key]) {
                return '<img src="images/face/' + ChatFaces.map[key] + '"border="0" />';
            }
            else {
                return key;
            }
        });
        return str;
    }

    var initIM = function () {
        conn = new WebIM.connection({
            https: WebIM.config.https,
            url: WebIM.config.xmppURL,
            isAutoLogin: WebIM.config.isAutoLogin,
            isMultiLoginSessions: WebIM.config.isMultiLoginSessions,

        });
        setInterval(function () {

            conn.setPresence();
        }, 1800000)
        conn.listen({
            onOpened: function (message) {

                //连接成功回调
                // 如果isAutoLogin设置为false，那么必须手动设置上线，否则无法收消息
                // 手动上线指的是调用conn.setPresence(); 如果conn初始化时已将isAutoLogin设置为true
                // 则无需调用conn.setPresence(); 

            },
            onClosed: function (message) {


            },  //连接关闭回调
            onTextMessage: function (message) {
                var messageItem = new MessageItem();
                messageItem.messageSource = message;
                messageItem.messageType = "Text";
                messageItem.content = handleEmoji(message.data);
                messageItem.from = message.from;
                messageItem.to = message.to;
                if (message.ext) {
                    messageItem.logourl = message.ext.headUrl;
                    messageItem.fromName = message.ext.nickName;
                }
                messageItem.messageId = message.id;
                me.innerMessageHander && me.innerMessageHander.onMessage(messageItem);
            },    //收到文本消息
            onEmojiMessage: function (message) {
                // 当为WebIM添加了Emoji属性后，若发送的消息含WebIM.Emoji里特定的字符串，connection就会自动将
                // 这些字符串和其它文字按顺序组合成一个数组，每一个数组元素的结构为{type: 'emoji(或者txt)', data:''}
                // 当type='emoji'时，data表示表情图像的路径，当type='txt'时，data表示文本消息
                //debugger
                console.log(messages);
                var data = message.data;
                var theText = "";
                for (var i = 0, l = data.length ; i < l ; i++) {
                    var theData = data[i];
                    console.log(theData);
                    if (theData.type = "emoji") {
                        theText += "<img src='" + theData.data + "'/>";
                    }
                    if (theData.type = "txt") {
                        theText += theData.data;
                    }
                }
                var messageItem = new MessageItem();
                messageItem.messageSource = message;
                messageItem.messageType = "Text";
                messageItem.content = theText;
                messageItem.from = message.from;
                messageItem.to = message.to;
                if (message.ext) {
                    messageItem.logourl = message.ext.headUrl;
                    messageItem.fromName = message.ext.nickName;
                }
                messageItem.messageId = message.id;
                me.innerMessageHander && me.innerMessageHander.onMessage(messageItem);

            },   //收到表情消息
            onPictureMessage: function (message) {
                var messageItem = new MessageItem();
                messageItem.messageSource = message;
                messageItem.messageType = "Text";
                messageItem.content = "<img class='chat-image' src=" + message.url + "/ >";
                messageItem.from = message.from;
                messageItem.to = message.to;
                if (message.ext) {
                    messageItem.logourl = message.ext.headUrl;
                    messageItem.fromName = message.ext.nickName;
                }
                messageItem.messageId = message.id;
                me.innerMessageHander && me.innerMessageHander.onMessage(messageItem);

                console.log("Location of Picture is ", message.url);
            }, //收到图片消息
            onCmdMessage: function (message) {
            },     //收到命令消息
            onAudioMessage: function (message) {
                var messageItem = new MessageItem();
                messageItem.messageSource = message;
                messageItem.messageType = "Text";
                if (message.ext) {
                    messageItem.logourl = message.ext.headUrl;
                    messageItem.fromName = message.ext.nickName;
                }
                messageItem.messageId = message.id;
                messageItem.content = "<span  class='voice' onclick='audioPlay(this)' ><audio class='voice-item' src='" + message.url + "' >当前浏览器不支持播放此音频</audio><i class='voice-icon voice-png'></i><i class='time' ></i></span>"
                messageItem.from = message.from;
                messageItem.to = message.to;
                if (message.ext) {
                    messageItem.logourl = message.ext.headUrl;
                    messageItem.fromName = message.ext.nickName;
                }

                console.log("Location of audio is ", message.url);
                var options = { url: message.url };;
                var url = ""
                options.onFileDownloadComplete = function (response, xhr) {

                    var reader = new FileReader();
                    reader.addEventListener("loadend", function () {
                        // reader.result contains the contents of blob as a typed array
                        var theUrl = this.result;
                        messageItem.content = "<span  class='voice' onclick='audioPlay(this)' ><audio preload='auto' onloadedmetadata='allT(this)' class='voice-item' src='" + theUrl + "'>当前浏览器不支持播放此音频</audio><i class='voice-icon voice-png'></i><i class='time'></i><i  class='litter' ></i></span>";
                        me.innerMessageHander && me.innerMessageHander.onMessage(messageItem);
                        
                    });
                    reader.readAsDataURL(response);

                };
                options.onFileDownloadError = function () {
                    //音频下载失败 
                    messageItem.content = "<span  class='voice' onclick='audioPlay(this)'><i class='voice-icon voice-png'></i><i class='time'></i></span>"
                };
                options.headers = {
                    "Accept": "audio/mp3"
                };
                WebIM.utils.download.call(conn, options);
                // Easemob.im.Helper.download(options);

            },   //收到音频消息
            onLocationMessage: function (message) { },//收到位置消息
            onFileMessage: function (message) { },    //收到文件消息
            onVideoMessage: function (message) {
                var node = document.getElementById('privateVideo');
                var option = {
                    url: message.url,
                    headers: {
                        'Accept': 'audio/mp4'
                    },
                    onFileDownloadComplete: function (response) {
                        var objectURL = WebIM.utils.parseDownloadResponse.call(conn, response);
                        node.src = objectURL;
                    },
                    onFileDownloadError: function () {
                        console.log('File down load error.')
                    }
                };
                WebIM.utils.download.call(conn, option);
            },   //收到视频消息
            onPresence: function (message) { },       //收到联系人订阅请求、处理群组、聊天室被踢解散等消息
            onRoster: function (message) { },         //处理好友申请
            onInviteMessage: function (message) { },  //处理群组邀请
            onOnline: function () {

                layer.msg("连接成功！")
                //alert("连接成功！");
            },                  //本机网络连接成功
            onOffline: function () {
                layer.alert("账号在另一个地方登录，请重新登录", function () {
                    APP.Go(APP.URL.LOGIN);
                })
                //conn.setPresence();
            },                 //本机网络掉线
            onError: function (message) {
                layer.alert("登录失败!", function () {
                    APP.Go(APP.URL.LOGIN);
                })
                /*layer.alert("账号在另一个地方登录，请重新登录", function () {
                    APP.Go(APP.URL.LOGIN);
                })*/
               
            },          //失败回调
            onBlacklistUpdate: function (list) {       //黑名单变动
                // 查询黑名单，将好友拉黑，将好友从黑名单移除都会回调这个函数，list则是黑名单现有的所有好友信息
                console.log(list);
            }
        });
    }

    HuanXin.prototype.send = function (messageItem) {
        var id = conn.getUniqueId();                 // 生成本地消息id
        var msg = new WebIM.message('txt', id);      // 创建文本消息
        msg.set({
            msg: messageItem.content,                  // 消息内容
            to: messageItem.to,                          // 接收消息对象（用户id）
            roomType: false,
            success: function (id, serverMsgId) {
                console.log('send private text Success');
            }
        });
        msg.body.chatType = 'singleChat';
        conn.send(msg.body);
    }
    HuanXin.prototype.listen = function (messageHandlerObj) {
        this.innerMessageHander = messageHandlerObj;
    }
    HuanXin.prototype.register = function (name, passwd, nickname) {
        var options = {
            username: name,
            password: passwd,
            nickname: nickname,
            appKey: WebIM.config.appkey,
            success: function () {
                alert('注册成功!');
            },
            error: function () {
                alert('注册失败!');
            },
            apiUrl: WebIM.config.apiURL
        };
        conn.registerUser(options);
    }

    HuanXin.prototype.login = function (user, pwd) {
        var options = {
            apiUrl: WebIM.config.apiURL,
            user: user,// 'sjian2017',
            pwd: Base64.decode(pwd),//'sjian2017',
            appKey: WebIM.config.appkey
        };
        conn.open(options);
    }




    initIM();
}
