package com.cmcc.medicalcare.inter.jiuzhoutong;

import java.util.HashMap;
import java.util.Map;

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
public class MemberAddressUtils {

	private final static String findAddressUrl = Config.jiuZhouTongTestUrl + "/out/findAddress?";
//	private final static String findAddressUrl = Constant.URL + "/out/findAddress?";
	
	private final static String saveAddressUrl = Config.jiuZhouTongTestUrl + "/out/saveAddress?";
	
	private final static String updateAddressUrl =Config.jiuZhouTongTestUrl + "/out/updateAddress?";
	
	private final static String setDefaultAddressUrl = Config.jiuZhouTongTestUrl + "/out/setDefaultAddress?";

	/**
	 * 2.9 会员地址查询 @Title: findAddress @Description: TODO @param @param
	 * memberPhone @param @return 设定文件 @return String 返回类型 @throws
	 */
	public static String findAddress(String memberPhone) {
		String method = "/out/findAddress";

		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signstr = ThirdInterfaceUtils.getMD5signstr(method, timestamp);

		String param = "memberPhone=" + memberPhone;

		String url = ThirdInterfaceUtils.getURL(findAddressUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param, "UTF-8");
		return jsonStr;
	}

	public static void main(String[] args) {
//		System.out.println(MemberAddressUtils.findAddress("15876587133"));
		System.out.println(MemberAddressUtils.saveAddress("珠江西路全球通大厦", "15876587133", "吴思铭", "15876587133","广东省","广州市","天河区"));
//		System.out.println(MemberAddressUtils.updateAddress("6", "天河区全球通", "15876587133", "David", "15876587133"));
	}
	
	/**
	 * 2.10	会员地址添加
	* @Title: saveAddress 
	* @Description: TODO 
	* @param @param expressAddress
	* @param @param memberPhone
	* @param @param consignee
	* @param @param consigneePhone
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String saveAddress(String expressAddress, String memberPhone, String consignee,
			String consigneePhone,String province,String city,String area) {
		String method = "/out/saveAddress";

		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signstr = ThirdInterfaceUtils.getMD5signstr(method, timestamp);

		String param = "expressAddress=" + expressAddress + "&memberPhone=" + memberPhone + "&consignee=" + consignee
				+ "&consigneePhone=" + consigneePhone+"&province="+province+"&city="+city+"&area="+area;

		String url = ThirdInterfaceUtils.getURL(saveAddressUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param, "UTF-8");
		return jsonStr;
	}
	
	/**
	 * 2.11	会员地址修改
	* @Title: updateAddress 
	* @Description: TODO 
	* @param @param addressId
	* @param @param expressAddress
	* @param @param memberPhone
	* @param @param consignee
	* @param @param consigneePhone
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String updateAddress(String addressId , String expressAddress, String memberPhone, String consignee,
			String consigneePhone, String province, String cityName, String area) {
		String method = "/out/updateAddress";

		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signstr = ThirdInterfaceUtils.getMD5signstr(method, timestamp);

		String param = "addressId="+addressId+"&expressAddress=" + expressAddress + "&memberPhone=" + memberPhone + "&consignee=" + consignee
				+ "&consigneePhone=" + consigneePhone + "&province=" + province + "&city=" + cityName + "&area=" + area;

		String url = ThirdInterfaceUtils.getURL(updateAddressUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param, "UTF-8");
		return jsonStr;
	}

	/**
	 * 2.12	设置会员默认地址
	* @Title: setDefaultAddress 
	* @Description: TODO 
	* @param @param addressId
	* @param @param memberPhone
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String setDefaultAddress(String addressId , String memberPhone) {
		String method = "/out/setDefaultAddress";

		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signstr = ThirdInterfaceUtils.getMD5signstr(method, timestamp);

		String param = "addressId="+addressId+ "&memberPhone=" + memberPhone;

		String url = ThirdInterfaceUtils.getURL(setDefaultAddressUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param, "UTF-8");
		return jsonStr;
	}

}
