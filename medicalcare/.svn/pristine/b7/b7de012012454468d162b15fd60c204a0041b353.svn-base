package com.cmcc.medicalcare.inter.jiuzhoutong;

import com.cmcc.medicalcare.config.Config;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.utils.HTTPUtils;
import com.cmcc.medicalcare.utils.ThirdInterfaceUtils;
import com.cmcc.medicalcare.utils.Toolkit;

/**
 * @ClassName:
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:05:41
 * 
 */
public class DoctorPrescriptionUtils {

	// private final static String uploadPrescriptionUrl =
	// Config.jiuZhouTongTestUrl + "/out/updateDoctorLoginCode?";

	private final static String uploadPrescriptionUrl = Config.jiuZhouTongTestUrl + "/out/uploadPrescription?";

	// private final static String findPrescriptionUrl = Constant.URL +
	// "/out/findPrescription?";
	private final static String findPrescriptionUrl = Config.jiuZhouTongTestUrl + "/out/findPrescription?";

	/**
	 * 3.17 上传处方接口 @Title: uploadPrescription @Description: TODO @param @param
	 * memberId @param @param patientId @param @param doctorId @param @param
	 * imgFile @param @param imgUrl @param @return 设定文件 @return String
	 * 返回类型 @throws
	 */
	public static String uploadPrescription(String memberId, String patientId, String doctorId, String imgUrl,
			String serviceMoney, String fee) {
		String method = "/out/uploadPrescription";

		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signstr = ThirdInterfaceUtils.getMD5signstr(method, timestamp);

		String param = "memberId=" + memberId + "&patientId=" + patientId + "&doctorId=" + doctorId + "&imgUrl="
				+ imgUrl + "&serviceMoney=" + serviceMoney + "&fee=" + fee;
		String url = ThirdInterfaceUtils.getURL(uploadPrescriptionUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param, "UTF-8");
		return jsonStr;
	}

	// public static void main(String[] args) {
	// String imgUrl =
	// "http://120.197.89.249:9001/medicalmedia/download/patient_13710240423_20170714113422_1500003262084.jpg";
	// String memberId = "3";
	// String patientId = "3";
	// String doctorId = "4";
	// System.out.println(DoctorPrescriptionUtils.uploadPrescription(memberId,
	// patientId, doctorId, imgUrl));
	// }

	/**
	 * 3.18 处方查询接口 @Title: findPrescription @Description: TODO @param @param
	 * memberId @param @param patientId @param @param doctorId @param @param
	 * startTime @param @param endTime @param @param
	 * prescriptionId @param @param startNumber @param @return 设定文件 @return
	 * String 返回类型 @throws
	 */
	public static String findPrescription(String memberId, String patientId, String doctorId, String startTime,
			String endTime, String prescriptionId, String startNumber) {
		String method = "/out/findPrescription";

		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signstr = ThirdInterfaceUtils.getMD5signstr(method, timestamp);

		String param = "memberId=" + memberId + "&patientId=" + patientId + "&doctorId=" + doctorId + "&startTime="
				+ startTime + "&endTime=" + endTime + "&prescriptionId=" + prescriptionId + "&startNumber="
				+ startNumber;

		String url = ThirdInterfaceUtils.getURL(findPrescriptionUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param, "UTF-8");
		return jsonStr;
	}

	public static void main(String[] args) {
		String memberId = "35";
		String patientId = "47";
		String doctorId = "9";
		System.out.println(DoctorPrescriptionUtils.findPrescription(memberId, patientId, doctorId, "", "", "", ""));
	}
}
