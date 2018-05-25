package com.cmcc.medicalcare.controller.app.patient;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.jiuzhoutong.MemberAddressUtils;
import com.cmcc.medicalcare.model.ReceiveAddress;
import com.cmcc.medicalcare.service.IReceiveAddressService;

/**
 * 
 * @author zds
 *
 */
@Controller
@RequestMapping("/app/receiveAddress")
public class ReceiveAddressController {

	@Resource
	private IReceiveAddressService receiveAddressService;

	/**
	 * 查询患者收货地址
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "selectReceiveAddress")
	@ResponseBody
	@SystemControllerLog(description = "查询患者收货地址")
	public Results<Map<String, Object>> selectReceiveAddress(HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		String equipmentData = request.getParameter("equipmentData");
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		String userPhone = dataJsonObject.getString("phone");
		if (StringUtils.isBlank(userPhone)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

		// List<ReceiveAddress> receiveAddressList =
		// receiveAddressService.getList("getReceiveAddress", userPhone);
		String jsonStr = MemberAddressUtils.findAddress(userPhone);
		JSONObject json = JSONObject.parseObject(jsonStr);
		int status = json.getInteger("status");
		if (status == 1) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage(json.getString("msg"));
			return results;
		}
		JSONArray jsonArray = json.getJSONArray("data");
		// map.put("receiveAddressList", receiveAddressList);
		map.put("receiveAddressJsonArray", jsonArray);
		results.setData(map);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;

	}

	/**
	 * 新增收货地址
	 * 
	 * @param request
	 * @param parameter={"receiveName":"收货人","receivePhone":"收货人手机号",
	 *            "isDefaultAddress":"是否默认地址：0否，1是",
	 *            "province":"省份","cityName":"城市","area":"区县","detailAddress":
	 *            "详细地址"}
	 * @return
	 */
	@RequestMapping(value = "saveReceiveAddress")
	@ResponseBody
	@SystemControllerLog(description = "新增收货地址")
	public Results<Map<String, Object>> saveReceiveAddress(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		String equipmentData = request.getParameter("equipmentData");
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		String userPhone = dataJsonObject.getString("phone");
		if (StringUtils.isBlank(userPhone)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

		JSONObject paramJsonObject = JSONObject.parseObject(parameter);
		String receiveName = paramJsonObject.getString("receiveName");
		String receivePhone = paramJsonObject.getString("receivePhone");
		Integer isDefaultAddress = paramJsonObject.getInteger("isDefaultAddress");
		String province = paramJsonObject.getString("province");
		String cityName = paramJsonObject.getString("cityName");
		String area = paramJsonObject.getString("area");
		String detailAddress = paramJsonObject.getString("detailAddress");
		if (StringUtils.isBlank(receivePhone) || StringUtils.isBlank(detailAddress)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

		// ReceiveAddress receiveAddress = new ReceiveAddress();
		// receiveAddress.setUserPhone(userPhone);
		// receiveAddress.setReceiveName(receiveName);
		// receiveAddress.setReceivePhone(receivePhone);
		// receiveAddress.setProvince(province);
		// receiveAddress.setCityName(cityName);
		// receiveAddress.setArea(area);
		// receiveAddress.setDetailAddress(detailAddress);
		// receiveAddress.setCreatetime(new Date());
		// if (1 == isDefaultAddress) { // 新增地址为默认地址
		// // 先更新原来的默认地址为普通地址
		// List<ReceiveAddress> receiveAddressList_ =
		// receiveAddressService.getList("getReceiveAddress", userPhone);
		// if (receiveAddressList_.size() > 0) {
		// for (int i = 0; i < receiveAddressList_.size(); i++) {
		// ReceiveAddress receiveAddress_ = receiveAddressList_.get(i);
		// if (1 == receiveAddress_.getIsDefaultAddress()) {
		// receiveAddress_.setIsDefaultAddress(0);
		// receiveAddress_.setUpdatetime(new Date());
		// receiveAddressService.update("updateByPrimaryKeySelective",
		// receiveAddress_);
		// }
		// }
		// }
		//
		// // 新增默认地址
		// receiveAddress.setIsDefaultAddress(1);
		// receiveAddressService.insert(receiveAddress);
		// } else {
		// receiveAddress.setIsDefaultAddress(0);
		// receiveAddressService.insert(receiveAddress);
		// }

		// List<ReceiveAddress> receiveAddressList =
		// receiveAddressService.getList("getReceiveAddress", userPhone);

		// map.put("receiveAddressList", receiveAddressList);

		MemberAddressUtils.saveAddress(detailAddress, userPhone, receiveName,
				receivePhone,province,cityName,area);
		String jsonStr2 = MemberAddressUtils.findAddress(userPhone);
		JSONObject json2 = JSONObject.parseObject(jsonStr2);
		JSONArray jsonArray = json2.getJSONArray("data");
		map.put("receiveAddressJsonArray", jsonArray);
		results.setData(map);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;

	}

	/**
	 * 设置默认收货地址
	 * 
	 * @param request
	 * @param parameter={"receiveAddressId":"收货地址id"}
	 * @return
	 */
	@RequestMapping(value = "updateDefaultAddress")
	@ResponseBody
	@SystemControllerLog(description = "设置默认收货地址")
	public Results<Map<String, Object>> updateDefaultAddress(HttpServletRequest request,
			@RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		String equipmentData = request.getParameter("equipmentData");
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		String userPhone = dataJsonObject.getString("phone");
		if (StringUtils.isBlank(userPhone)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

		JSONObject paramJsonObject = JSONObject.parseObject(parameter);
		Integer receiveAddressId = paramJsonObject.getInteger("receiveAddressId");
		if (null == receiveAddressId) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

		// ReceiveAddress receiveAddress =
		// receiveAddressService.findById("selectByPrimaryKey",
		// receiveAddressId);
		// if (null == receiveAddress) {
		// results.setCode(MessageCode.CODE_501);
		// results.setMessage("不存在该收货地址");
		// return results;
		// } else {
		// List<ReceiveAddress> receiveAddressList_ =
		// receiveAddressService.getList("getReceiveAddress", userPhone);
		// if (receiveAddressList_.size() > 0) {
		// for (int i=0; i<receiveAddressList_.size(); i++) {
		// ReceiveAddress receiveAddress_ = receiveAddressList_.get(i);
		// if (1 == receiveAddress_.getIsDefaultAddress()) {
		// receiveAddress_.setIsDefaultAddress(0);
		// receiveAddress_.setUpdatetime(new Date());
		// receiveAddressService.update("updateByPrimaryKeySelective",
		// receiveAddress_);
		// }
		// }
		// }
		//
		// receiveAddress.setIsDefaultAddress(1);
		// receiveAddress.setUpdatetime(new Date());
		// receiveAddressService.update("updateByPrimaryKeySelective",
		// receiveAddress);
		// }
		//
		// List<ReceiveAddress> receiveAddressList =
		// receiveAddressService.getList("getReceiveAddress", userPhone);
		String jsonStr = MemberAddressUtils.setDefaultAddress(String.valueOf(receiveAddressId), userPhone);
		JSONObject json = JSONObject.parseObject(jsonStr);
		int status = json.getInteger("status");
		if (status == 1) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage(json.getString("msg"));
			return results;
		} else {
			// map.put("receiveAddressList", receiveAddressList);
			String jsonStr2 = MemberAddressUtils.findAddress(userPhone);
			JSONObject json2 = JSONObject.parseObject(jsonStr2);
			JSONArray jsonArray = json2.getJSONArray("data");
			map.put("receiveAddressJsonArray", jsonArray);
			results.setData(map);
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
		}

	}

	/**
	 * parameter={"receiveAddressId":"收货地址id","receiveName":"收货人","receivePhone"
	 * :"收货人手机号", "province":"省份","cityName":"城市","area":"区县","detailAddress":
	 * "详细地址"}
	 *  @Title: updateAddress @Description: TODO @param @param
	 * request @param @param parameter @param @return 设定文件 @return
	 * Results<Map<String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "updateAddress")
	@ResponseBody
	@SystemControllerLog(description = "修改收货地址")
	public Results<Map<String, Object>> updateAddress(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		String equipmentData = request.getParameter("equipmentData");
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		String userPhone = dataJsonObject.getString("phone");
		if (StringUtils.isBlank(userPhone)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

		JSONObject paramJsonObject = JSONObject.parseObject(parameter);
		Integer receiveAddressId = paramJsonObject.getInteger("receiveAddressId");
		if (null == receiveAddressId) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		String receiveName = paramJsonObject.getString("receiveName");
		String receivePhone = paramJsonObject.getString("receivePhone");
		String province = paramJsonObject.getString("province");
		String cityName = paramJsonObject.getString("cityName");
		String area = paramJsonObject.getString("area");
		String detailAddress = paramJsonObject.getString("detailAddress");
		if (StringUtils.isBlank(receivePhone) || StringUtils.isBlank(detailAddress)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

		String jsonStr = MemberAddressUtils.updateAddress(String.valueOf(receiveAddressId), detailAddress, userPhone, receiveName, receivePhone, province, cityName, area);
		JSONObject json = JSONObject.parseObject(jsonStr);
		int status = json.getInteger("status");
		if (status == 1) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage(json.getString("msg"));
			return results;
		} else {
			String jsonStr2 = MemberAddressUtils.findAddress(userPhone);
			JSONObject json2 = JSONObject.parseObject(jsonStr2);
			JSONArray jsonArray = json2.getJSONArray("data");
			map.put("receiveAddressJsonArray", jsonArray);
			results.setData(map);
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
		}

	}

}
