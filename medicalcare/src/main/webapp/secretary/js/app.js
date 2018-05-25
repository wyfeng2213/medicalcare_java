/*应用页面的配置地址*/
//2017-7-16
var APP = {};
APP.URL = {
    LOGIN: 'login.html',
    WEBCHAT: 'index.html'
}

APP.Init = function () {
    var theUserData = EquipmentData.Get();
    if (!theUserData) {
        if (location.href.indexOf(APP.URL.LOGIN) < 0) {
            APP.Go(APP.URL.LOGIN);
        }

    }
}


APP.Go = function (page) {
    if (page) {
        location.href = page;
    }

}

APP.Login = function (res, callback) {
    //debugger
    data = res.data;
    if (data && res.code == 200) {
        console.log(data)
        var theSession = data.session;
        var theUser = data.secretaryUser;
        var newEData = new EquipmentData();
        newEData.session = theSession;
        newEData.phone = theUser.phone;
        newEData.userName = theUser.name;
        newEData.loginId = theUser.loginId;
        newEData.password = theUser.password;
        newEData.headUrl = "images/secretary.jpg";
        EquipmentData.Save(newEData);
        callback && callback();

       
    }
}
APP.Logout = function (){
  // Dexie.delete('ChatDatabase');
    sessionStorage.clear();
    APP.Go(APP.URL.LOGIN);
    localStorage.clear();
}
/*医生信息*/
function DoctorsUser()
{
    this.createtime=null;
    this.doctorsId=null;
    this.doctorsLoginId=null;
    this.doctorsName=null;
    this.doctorsPhone=null;
}
/*服务端返回的用户信息*/
function SecretaryUser() {
    this.createtime = "";
    this.hospital = "";
    this.id = 3;
    this.loginId = "";
    this.name = "";
    this.password = "";
    this.phone = "";
    this.updatetime = "";
}


/*客户端的设备信息 登录后获取到的设备信息*/
function EquipmentData() {
    this.phone = "";
    this.userName = "";
    this.userRole = "secretary";
    this.systemtype = "";
    this.model = "";
    this.packageVesion = "";
    this.session = "";
    this.headUrl=""
    this.loginId = "";
    this.password = "";
}

/*聊天室列表*/
function ChatRoom() {
    this.chatroomDescription = "";
    this.chatroomId = "";
    this.chatroomMaxusers = "";
    this.chatroomName = "";
    this.chatroomOwner = "";
    this.createtime = "";
    this.doctorsId = "";
    this.doctorsLoginId = "";
    this.doctorsName = "";
    this.doctorsPhone = "";
    this.id = "";
    this.patientId = "";
    this.patientLoginId = "";
    this.patientName = "";
    this.patientPhone = "";
    this.remark = "";
    this.secretaryId = "";
    this.secretaryLoginId = "";
    this.secretaryName = "";
    this.secretaryPhone = "";
    this.updatetime = "";
}

EquipmentData.Save = function (data) {
    var theSaveKey = "UserData";
    sessionStorage.setItem(theSaveKey, JSON.stringify(data));

}

/*请求服务端获取的参数*/
EquipmentData.GetClientState = function () {
    var theSaveKey = "UserData";
    var theData = sessionStorage.getItem(theSaveKey);
   
    if (theData) {
        var theData = JSON.parse(theData);
        return {
            phone: theData.phone,
            userName: theData.userName,
            userRole: theData.userRole,
            systemtype: theData.systemtype,
            model: theData.model,
            packageVesion: theData.packageVesion,
            session: theData.session
        };
    }
    else {
        return null;
    }
}
EquipmentData.Get = function () {
    var theSaveKey = "UserData";
    var theData = sessionStorage.getItem(theSaveKey);
    if (theData) {
        return JSON.parse(theData);
    }
    else {
        return null;
    }

}

EquipmentData.Clear = function () {
    sessionStorage.clear();
}

APP.Init();

//得到患者的信息
APP.getPatientUserData = function (phone, callback) {
   
    var theUrl = "/medicalcare/web/secretaryPatientLink/select.action";
    var theClientState = EquipmentData.GetClientState();
    CallGetService(theUrl, {
        patientPhone:phone,
        equipmentData: JSON.stringify(theClientState)
    }, function (data){
        console.log('返回消息:' + JSON.stringify(data));
        if (data.code == 400) {
            APP.Logout();
            return;
        }
        callback && callback(data);
    });
}