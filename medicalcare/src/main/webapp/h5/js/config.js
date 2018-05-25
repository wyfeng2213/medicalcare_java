var apiOrigin = 'http://10.1.2.36:8080/';
//var apiOrigin = 'https://health.hivedata.com.cn:9011';
//var apiOrigin = 'http://120.197.89.249:8080/';
//接口
var api = {
	register: apiOrigin+'medicalcare/h5/patientLoginInfo/codeLogin.action',
	sendCode: apiOrigin+'medicalcare/h5/patientLoginInfo/sendCode.action',
	applyUserInfos: apiOrigin+'medicalcare/h5/patientUser/insert.action',
	select: apiOrigin+'medicalcare/h5/patientUser/select.action',
	checkDoctorPatientLink:apiOrigin+'medicalcare/h5/patientDoctorsLink/checkDoctorPatientLink.action'
}

//公共参数
var equipmentData = {"phone":"","userName":"","userRole":"patient","systemtype":"android6.0.1","model":"Xiaomi Redmi 4X","session":"","packageVesion":""}
