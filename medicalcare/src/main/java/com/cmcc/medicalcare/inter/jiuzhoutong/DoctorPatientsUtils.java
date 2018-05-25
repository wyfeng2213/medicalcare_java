package com.cmcc.medicalcare.inter.jiuzhoutong;

import com.cmcc.medicalcare.config.Config;
import com.cmcc.medicalcare.utils.HTTPUtils;
import com.cmcc.medicalcare.utils.ThirdInterfaceUtils;
import com.cmcc.medicalcare.utils.Toolkit;

/**
 * @ClassName:医患关系
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:05:41
 * 
 */
public class DoctorPatientsUtils {

	// private final static String uploadPrescriptionUrl =
	// Config.jiuZhouTongTestUrl + "/out/updateDoctorLoginCode?";

	private final static String saveDoctorPatientUrl = Config.jiuZhouTongTestUrl + "/out/saveDoctorPatient?";

	private final static String findDoctorPatientUrl = Config.jiuZhouTongTestUrl + "/out/findDoctorPatient?";
	
	private final static String updateDoctorPatientUrl = Config.jiuZhouTongTestUrl + "/out/updateDoctorPatient?";

	public static String saveDoctorPatient(String doctorId, String doctorName, String doctorPhone, String memberId,
			String memberPhone, String patientId, String patientName, String patientPhone, String remark) {
		String method = "/out/saveDoctorPatient";

		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signstr = ThirdInterfaceUtils.getMD5signstr(method, timestamp);

		String param = "doctorId=" + doctorId + "&doctorName=" + doctorName + "&doctorPhone=" + doctorPhone
				+ "&memberId=" + memberId + "&memberPhone="+memberPhone+"&patientId=" + patientId + "&patientName=" + patientName + "&patientPhone="
				+ patientPhone + "&remark=" + remark;
		String url = ThirdInterfaceUtils.getURL(saveDoctorPatientUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param, "UTF-8");
		return jsonStr;
	}

	// public static void main(String[] args) {
	// String imgUrl =
	// "http://120.197.89.249:9001/medicalmedia/download/patient_13710240423_20170714113422_1500003262084.jpg";
	// String memberId = "3";
	// String patientId = "3";
	// String doctorId = "4";
	// System.out.println(PaymentUtils.uploadPrescription(memberId,
	// patientId, doctorId, imgUrl));
	// }

	public static String findDoctorPatient(String doctorId, String memberId, String patientId, String doctorPatientId,
			String startNumber) {
		String method = "/out/findDoctorPatient";

		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signstr = ThirdInterfaceUtils.getMD5signstr(method, timestamp);

		String param = "doctorId=" + doctorId + "&memberId=" + memberId + "&patientId=" + patientId
				+ "&doctorPatientId=" + doctorPatientId + "&startNumber=" + startNumber;
		String url = ThirdInterfaceUtils.getURL(findDoctorPatientUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param, "UTF-8");
		return jsonStr;
	}
	
	
	/**
	 * 3.25	医患关系-最后就诊时间修改
	* @Title: updateDoctorPatient 
	* @Description: TODO 
	* @param @param doctorPatientId
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String updateDoctorPatient(String doctorPatientId) {
		String method = "/out/updateDoctorPatient";

		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signstr = ThirdInterfaceUtils.getMD5signstr(method, timestamp);

		String param = "doctorPatientId=" + doctorPatientId;
		String url = ThirdInterfaceUtils.getURL(updateDoctorPatientUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param, "UTF-8");
		return jsonStr;
	}
}
