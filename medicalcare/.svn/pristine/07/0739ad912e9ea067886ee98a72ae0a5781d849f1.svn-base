/*
还有就是每个请求都要带上公共参数
equipmentData ={"phone":"","userName":"","userRole":"secretary","systemtype":"","model":"","packageVesion":"","session":""}
*/

/*
小秘书发送文本消息到医生：
http://120.197.89.249:8080/medicalcare/web/secretarySendMessage/sendTxtMessageToSingle.action?parameter={"doctorsLoginId":"","doctorsName":"","secretaryLoginId":"","secretaryName":"","msgContentStr":""}

*/



/*通用的消息体*/
function MessageItem() {
    this.chatId = "";//
    this.messageId = "";//当前的消息ID
    this.messageTime = "";//当前的消息的时间
    this.messageType = "";
    this.logourl = "";//图片发送者的图片
    this.messageSource = "";
    this.from = "";//谁发送的。医生和小秘书的判断 可以根据前缀进行判断
    this.fromName = "";
    this.to = "";//指定人/患者有图形信息
    this.toName = "";
    this.content = "";
    this.callback = "";
    this.read = false;
    this.chatApp = "";
    this.clientId = "";//当前登录的客户端ID
    this.listen = '';//是否已读
};

/*消息类型*/
function MessageTypes() {

}

/*文本消息*/
MessageTypes.TEXT = "TEXT";
MessageTypes.CHAT_TEXT = "CHAT_TEXT";
MessageTypes.CHAT_ROOM_TEXT = "ROOM_TEXT";
/*图片信息*/
MessageTypes.CHAT_IMG = "CHAT_IMG";
MessageTypes.ROOM_IMG = "ROOM_IMG";

/*通用的消息客户端*/
function MessageClient(table) {

    var theInnerTable = table || "test_db";
    this.InnerTable = theInnerTable;
    var client = this;
    var hasLoadHistory = false;
    var instance = new HuanXin();
    var innerMessageHander = null;//接收消息处理
    //发送消息到服务端（通过服务端发送消息）
    var sendMessageToServer = function (messageItem) {
        var theServerUrlMap = {
            "CHAT_TEXT": "/medicalcare/web/secretarySendMessage/sendTxtMessageToSingle.action",
            "ROOM_TEXT": "/medicalcare/web/secretarySendMessage/sendTxtMessageToChatroom.action",
            "CHAT_IMG": "/medicalcare/web/secretarySendMessage/sendImgMessageToSingle.action",
            "ROOM_IMG": "/medicalcare/web/secretarySendMessage/sendImgMessageToChatroom.action",

        };
        // client.addHistoryItem(theInnerTable, messageItem);
        if (messageItem) {
            var theUrl = theServerUrlMap[MessageTypes.TEXT];
            var theUserData = EquipmentData.Get();
            switch (messageItem.messageType) {
                case MessageTypes.CHAT_TEXT:
                    {
                        theUrl = theServerUrlMap[MessageTypes.CHAT_TEXT];
                        var parameter = {
                            doctorsLoginId: messageItem.to,
                            doctorsName: '',
                            secretaryLoginId: theUserData.loginId,
                            secretaryName: theUserData.userName,
                            msgContentStr: messageItem.content
                        };
                        var theClientState = EquipmentData.GetClientState();
                        CallGetService(theUrl, {
                            parameter: JSON.stringify(parameter),
                            equipmentData: JSON.stringify(theClientState)
                        }, function (data) {
                            if (data.code == 400) {
                                APP.Logout();
                                return;
                            }
                            console.log('返回消息:' + JSON.stringify(data));
                            if (messageItem && messageItem.callback) {
                                messageItem.callback(data);
                            }
                        });
                        break;
                    }
                    break;
                case MessageTypes.CHAT_IMG:
                    {
                        theUrl = theServerUrlMap[MessageTypes.CHAT_IMG];
                        var parameter = {
                            doctorsLoginId: messageItem.to,
                            doctorsName: '',
                            secretaryLoginId: theUserData.loginId,
                            secretaryName: theUserData.userName
                        };
                        var theClientState = EquipmentData.GetClientState();
                        CallFileService(theUrl, {
                            parameter: JSON.stringify(parameter),
                            equipmentData: JSON.stringify(theClientState)
                        },
                         messageItem.content,
                        function (data) {

                            console.log('返回消息:' + JSON.stringify(data));
                            if (data.code == 400) {
                                APP.Logout();
                                return;
                            }
                            if (messageItem && messageItem.callback) {
                                messageItem.callback(data);
                            }
                        });
                        break;
                    }
                    break;
                case MessageTypes.CHAT_ROOM_TEXT:
                    theUrl = theServerUrlMap[MessageTypes.CHAT_ROOM_TEXT];
                    var parameter = {
                        secretaryName: theUserData.userName,
                        secretaryLoginId: theUserData.loginId,
                        doctorsLoginId: "",
                        patientName: "",
                        patientLoginId: "",
                        msgContentStr: messageItem.content,
                        chatroomId: messageItem.to
                    };
                    var theClientState = EquipmentData.GetClientState();
                    CallGetService(theUrl, {
                        parameter: JSON.stringify(parameter),
                        equipmentData: JSON.stringify(theClientState)
                    }, function (data) {
                        console.log('返回消息:' + JSON.stringify(data));
                        if (data.code == 400) {
                            APP.Logout();
                            return;
                        }
                        if (messageItem && messageItem.callback) {
                            messageItem.callback(data);
                        }
                    });
                    break;
                case MessageTypes.ROOM_IMG:
                    theUrl = theServerUrlMap[MessageTypes.ROOM_IMG];
                    var parameter = {
                        secretaryName: theUserData.userName,
                        secretaryLoginId: theUserData.loginId,
                        doctorsLoginId: "",
                        patientName: "",
                        patientLoginId: "",
                        chatroomId: messageItem.to
                    };
                    var theClientState = EquipmentData.GetClientState();
                    CallFileService(theUrl, {
                        parameter: JSON.stringify(parameter),
                        equipmentData: JSON.stringify(theClientState)
                    },
                    messageItem.content, function (data) {
                        console.log('返回消息:' + JSON.stringify(data));
                        if (data.code == 400) {
                            APP.Logout();
                            return;
                        }
                        if (messageItem && messageItem.callback) {
                            messageItem.callback(data);
                        }
                    });
                    break;
            }
        }
        // 小秘书发送文本消息到聊天室:
        // http://120.197.89.249:8080/medicalcare/web/secretarySendMessage/sendTxtMessageToChatroom.action?parameter={"secretaryName":"","secretaryLoginId":"","doctorsLoginId":"","patientName":"","doctorName":"","patientLoginId":"","msgContentStr":"","chatroomId":""}

    };
    MessageClient.prototype.enableLocalHistory = function () {
        var theKey = "ENABLE_LOCAL_HISTORY";
        localStorage.setItem(theKey, 1);
    }
    MessageClient.prototype.IsEnableLocalHistory = function () {
        var theKey = "ENABLE_LOCAL_HISTORY";
        localStorage.getItem(theKey) == "1";
    }

    MessageClient.prototype.clearHistory = function (table, callback) {
        var theDb = this.getDataStore(table);
        theDb.open().then(function () {
            theDb["CHAT_MESSAGE"].delete();
        }).then(function () {
            console.log("清除数据完成!");
        }).catch(function (e) {
            console.log("Error: " + (e.stack || e));
        });
    }
    MessageClient.prototype.getHistory = function (table, callback) {
        var theDb = this.getDataStore(table);
        var theLoginData = EquipmentData.Get();
        var clientId = theLoginData.loginId;
        //debugger
        var theList = [];
        theDb.open().then(function () {
            return Promise.resolve();
        }).then(function () {
            return theDb['CHAT_MESSAGE'].where('chatApp').equals(table).and(function (item) {
                return item.clientId = clientId
            }).toArray();
            //callback(theList);

        }).then(function (theMessageItems) {
            for (var i = 0; i < theMessageItems.length; i++) {
                var theItem = theMessageItems[i];
                callback(theItem);
            }

        }).catch(function (e) {
            console.log("Error: " + (e.stack || e));
        });

    }

    MessageClient.prototype.addHistoryItem = function (table, item) {
        var theLoginData = EquipmentData.Get();
        var clientId = theLoginData.loginId;
        var theDb = this.getDataStore(table);
        item.messageTime = item.messageTime || new Date().getTime();
        theDb.open().then(function () {
            return Promise.resolve();
        }).then(function () {
            var newMessage = {
                from: item.from,
                fromName: item.fromName,
                to: item.to,
                toName: item.toName,
                listen:false,
                clientId: item.clientId || clientId,
                logourl: item.logourl,
                messageTime: item.messageTime,
                messageId: item.messageId,
                messageType: item.messageType,
                read: item.read,
                messageSource: JSON.stringify(item.messageSource),
                content: item.content,
                chatApp: table
            };
            theDb['CHAT_MESSAGE'].add(newMessage)
            console.log(theDb['CHAT_MESSAGE'])
          .catch(function (e) {
              console.log("Error: " + (e.stack || e));
          });
        });

    }
    MessageClient.prototype.getDataStore = function (table) {
        var db = new Dexie("ChatDatabase");
        var theObj = {};
        var theMessageItem = new MessageItem();
        var theFields = [];
        theFields.push("++id");
        for (var key in theMessageItem) {
            theFields.push(key);
        }
        theObj['CHAT_MESSAGE'] = theFields.join(",");
        db.version(20).stores(theObj)
        /*.upgrade(function (trans) {
        debugger;
        trans.CHAT_MESSAGE.toCollection().modify(function (message) {
            message.mesageTime = null;
        });
    });;*/
        return db;
    }
    MessageClient.prototype.deleteHistory = function () {
        Dexie.delete('ChatDatabase');
    }
    //更新消息状态
    MessageClient.prototype.updateHistoryItemState = function (table, key, callback) {
        debugger
        var theLoginData = EquipmentData.Get();
        var clientId = theLoginData.loginId;
        var theDb = this.getDataStore(table);

        if (table == "YSFW") {
            theDb.open().then(function () {
                return Promise.resolve();
            }).then(function () {
                    theDb['CHAT_MESSAGE'].where('from').equals(key).or('to').equals(key).and(function (item) {
                        return item.clientId = clientId&&item.chatApp==table;
                    }).modify({ read: true });
                    console.log(theDb['CHAT_MESSAGE'])
                  .catch(function (e) {
                      console.log("Error: " + (e.stack || e));
                  });
                });
        };
        if (table == "ZXWZ") {
            theDb.open().then(function () {
                return Promise.resolve();
            }).then(function () {
                theDb['CHAT_MESSAGE'].where('to').equals(key).and(function (item) {
                    return item.clientId = clientId && item.chatApp == table;
                }).modify({ read: true })
                console.log(theDb['CHAT_MESSAGE'])
              .catch(function (e) {
                  console.log("Error: " + (e.stack || e));
              });
            });
        }




    }

    //

    MessageClient.prototype.updateAudioHistoryItemState = function (table, key, callback) {
        if (!key) {
            return;
        }
        var theLoginData = EquipmentData.Get();
        var clientId = theLoginData.loginId;
        var theDb = this.getDataStore(table);

        if (table == "YSFW") {
            theDb.open().then(function () {
                return Promise.resolve();
            })
                .then(function () {
                    theDb['CHAT_MESSAGE'].where('id').equals(parseInt( key)).modify({ listen: true });
                    console.log(theDb['CHAT_MESSAGE'])
                  .catch(function (e) {
                      console.log("Error: " + (e.stack || e));
                  });
                });
        };
        if (table == "ZXWZ") {
            theDb.open().then(function () {
                return Promise.resolve();
            }).then(function () {
                theDb['CHAT_MESSAGE'].where('id').equals(parseInt( key)).modify({ listen: true })
                console.log(theDb['CHAT_MESSAGE'])
              .catch(function (e) {
                  console.log("Error: " + (e.stack || e));
              });
            });
        }




    }
    //添加监听
    MessageClient.prototype.listen = function (messageHandlerObj) {
        this.innerMessageHander = messageHandlerObj;

        var me = this;
        var theUserData = EquipmentData.Get();
        var onMessage = {

            onMessage: function (msg) {
                if (!msg) {
                    return;
                }
                msg.messageTime = msg.messageTime || new Date().getTime();
                if (msg.to.indexOf("tary_") >= 0) {

                    $(".template").find("audio")[0].play()

                    messageHandlerObj.onMessage(msg);
                    me.addHistoryItem("YSFW", msg);
                } else {
                    if (location.href.indexOf(APP.URL.WEBCHAT) < 0) {
                        me.addHistoryItem("ZXWZ", msg);
                        $(".template").find("audio")[0].play()
                    } else {
                        if (msg.from == theUserData.loginId) {
                            messageHandlerObj.onMessage(msg);
                            me.addHistoryItem("ZXWZ", msg);
                        } else {
                            messageHandlerObj.onMessage(msg);
                            me.addHistoryItem("ZXWZ", msg);
                            $(".template").find("audio")[0].play()
                        }

                    }

                }

            }
        };

        instance.listen(onMessage);
    }
    //注册用户
    MessageClient.prototype.register = function (user, pwd, nickname) {
        instance.register(user, pwd, nickname);
    }
    //发送消息
    MessageClient.prototype.send = function (messageItem) {
        //instance.send(messageItem);
        sendMessageToServer(messageItem);
    }
    //用户登录
    MessageClient.prototype.login = function (user, pwd) {
        instance.login(user, pwd);
    }
    //用户登出
    MessageClient.prototype.logoff = function () {
        instance.logoff();

    }
    MessageClient.prototype.handleEmoji = function (str) {
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
    //获取聊天室列表
    MessageClient.prototype.getChatRoomList = function (phone, callback) {
        var theUrl = "/medicalcare/web/SecretaryChatroomController/getChatRoomListOfSecretary.action";

        var theClientState = EquipmentData.GetClientState();
        CallGetService(theUrl, {
            parameter: JSON.stringify({ secretaryPhone: theClientState.phone }),
            equipmentData: JSON.stringify(theClientState)
        }, function (data) {
            console.log('返回消息:' + JSON.stringify(data));
            if (data.code == 400) {
                APP.Logout();
                return;
            }
            callback && callback(data);
        });
    }

    MessageClient.prototype.selectSecretaryPatientLink = function (phone, callback) {
        var theUrl = "/medicalcare/web/secretaryPatientLink/select.action";
        var theClientState = EquipmentData.GetClientState();
        CallGetService(theUrl, {
            patientPhone: phone,
            equipmentData: JSON.stringify(theClientState)
        }, function (data) {
            console.log('返回消息:' + JSON.stringify(data));
            if (data.code == 400) {
                APP.Logout();
                return;
            }
            callback && callback(data);
        });
    }
    //    /web/secretaryDoctorsLink/doctorMainInfo.action?parameter={"doctorsLoginId":""}&equipmentData={xxxxxxxxx}
    //小秘书获取医生单聊列表:"/medicalcare/web/secretaryDoctorsLink/getDoctorList.action";
    MessageClient.prototype.getDoctorList = function (phone, callback) {

        var theUrl = "/medicalcare/web/secretaryDoctorsLink/getDoctorList.action";
        var theClientState = EquipmentData.GetClientState();
        CallGetService(theUrl, {
            parameter: JSON.stringify({ secretaryPhone: theClientState.phone }),
            equipmentData: JSON.stringify(theClientState)
        }, function (data) {
            console.log('返回消息:' + JSON.stringify(data));
            if (data.code == 400) {
                APP.Logout();
                return;
            }
            callback && callback(data);
        });
    }


    MessageClient.prototype.getDoctorMessage = function (doctoryid, callback) {
        var theUrl = "/medicalcare/web/secretaryDoctorsLink/doctorMainInfo.action";
        var theClientState = EquipmentData.GetClientState();
        CallGetService(theUrl, {
            parameter: JSON.stringify({ doctorsLoginId: doctoryid }),
            equipmentData: JSON.stringify(theClientState)
        }, function (data) {
            console.log('返回消息:' + JSON.stringify(data));
            if (data.code == 400) {
                APP.Logout();
                return;
            }
            callback && callback(data);
        });
    }

    //获得医生信息

    this.enableLocalHistory();
    if (hasLoadHistory == false) {
        hasLoadHistory = true;
        var me = this;
        setTimeout(function () {
            me.getHistory(theInnerTable, function (data) {
                me.innerMessageHander.onMessage(data);
            })
        }, 300)

    }

}
//音频
//滚动条悬浮

function allT(ele) {
    console.log(ele)
    var t = ele.duration;
    console.log(t)
    $(ele).next().next().html(Math.floor(t));

}
function audioPlay(ele) {
            debugger
    var preId = $(ele).closest('.field').prev('.field').val();
    theIMClient.updateAudioHistoryItemState(theIMClient.InnerTable, preId);

    var audio = $(ele).find("audio")[0];
    var t = audio.duration;
    var allVoice = document.getElementsByClassName("voice-item")

    for (var i = 0; i < allVoice.length; i++) {
        (function (i) {
            $(".voice-icon").removeClass("voice-gif").addClass("voice-png");
            var time = allVoice[i].duration
            $(".time")[i].innerHTML = Math.floor(time)
            allVoice[i].load();
        })(i);
    }


    $(ele).find(".litter").css("display", 'none')
    if (!audio.ended && !audio.paused) {
        audio.pause();
        audio.load();
        $(ele).find(".voice-icon").removeClass("voice-gif").addClass("voice-png");
    } else {
        var timer = setInterval(function () {
            $(ele).find(".time").html(Math.floor(t) - Math.floor(audio.currentTime))
            if (Math.floor(t) - Math.floor(audio.currentTime) == 0) {
                $(ele).find(".time").html(Math.floor(t))
                clearInterval(timer);

            }
        }, 1000)

        $(ele).find(".voice-icon").removeClass("voice-png").addClass("voice-gif");
        audio.play();


    }
    $(audio).bind('ended', function () {
        $(ele).find(".voice-icon").removeClass("voice-gif").addClass("voice-png");
    });

}
//视屏播放
