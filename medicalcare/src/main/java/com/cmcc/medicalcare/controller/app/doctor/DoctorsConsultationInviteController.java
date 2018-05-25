/**   
* @Title: DoctorsTeamInviteController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:44:51 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.doctor;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.DoctorsConsultation;
import com.cmcc.medicalcare.model.DoctorsConsultationInvite;
import com.cmcc.medicalcare.model.DoctorsConsultationUserLink;
import com.cmcc.medicalcare.service.IDoctorsConsultationInviteService;
import com.cmcc.medicalcare.service.IDoctorsConsultationService;
import com.cmcc.medicalcare.service.IDoctorsConsultationUserLinkService;

/**
 * @ClassName: 医生会诊团队邀请信息
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:44:51
 * 
 */
@Controller
@RequestMapping("/app/doctorsConsultationInvite")
public class DoctorsConsultationInviteController{
	
	@Resource
	private IDoctorsConsultationInviteService doctorsConsultationInviteService;
	
	@Resource
	private IDoctorsConsultationUserLinkService doctorsConsultationUserLinkService;
	
	@Resource
	private IDoctorsConsultationService doctorsConsultationService;
	
	/**
	 * parameter={"doctorsPhone":""}
	* @Title: checkTeamInvite 
	* @Description: TODO 
	* @param @param parameter
	* @param @param request
	* @param @return    设定文件 
	* @return Results<Map<String,Object>>    返回类型 
	* @throws
	 */
	@SystemControllerLog(description = "查询会诊邀请信息及所有发起和参与的会诊")
	@RequestMapping(value = "tipsOfInviteAndAllConsultations") 
	@ResponseBody
	public Results<Map<String, Object>> tipsOfInviteAndAllConsultations(@RequestParam String parameter,HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
//		List<DoctorsConsultation> listLeaderConsultations = new ArrayList<>(); //我发起的会诊
//		List<DoctorsConsultation> listJoinerConsultations = new ArrayList<>(); //我参与的会诊
		
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsPhone = dataJsonObject.getString("doctorsPhone");
		
		if (StringUtils.isNotBlank(doctorsPhone)) {
			List<DoctorsConsultationInvite> listDoctorsConsultationInvite = doctorsConsultationInviteService.getList("selectByInvitedPhone", doctorsPhone);
			/*if (listDoctorsConsultationInvite.size() > 0) { //存在新的提醒信息
				map.put("tipsCount", listDoctorsConsultationInvite.size());
				map.put("listDoctorsConsultationInvite", listDoctorsConsultationInvite);
			}*/
			map.put("tipsCount", listDoctorsConsultationInvite.size());
			map.put("listDoctorsConsultationInvite", listDoctorsConsultationInvite);
			
			//获取医生为领队的团队列表
			List<DoctorsConsultationUserLink> listLeaderLink = doctorsConsultationUserLinkService.getList("getLeaderList", doctorsPhone);
			/*if (listLeaderLink.size() > 0) {//填充团队实体
				DoctorsConsultation doctorsConsultation = null;
				for (int i = 0; i < listLeaderLink.size(); i++) {
					doctorsConsultation = doctorsConsultationService.findById("selectByTeamId", listLeaderLink.get(i).getTeamId());
					if (doctorsConsultation != null) {
						listLeaderConsultations.add(doctorsConsultation);
					}
				}
			}*/
			map.put("listLeaderLink", listLeaderLink);
			
			//获取医生为普通成员的团队列表,按最新时间倒序
			List<DoctorsConsultationUserLink> listjoinerLink = doctorsConsultationUserLinkService.getList("getJoinerList", doctorsPhone);
			/*if (listjoinerLink.size() > 0) {//填充团队实体
				DoctorsConsultation doctorsConsultation = null;
				for (int i = 0; i < listjoinerLink.size(); i++) {
					doctorsConsultation = doctorsConsultationService.findById("selectByTeamId", listjoinerLink.get(i).getTeamId());
					if (doctorsConsultation != null) {
						listJoinerConsultations.add(doctorsConsultation);
					}
				}
			}*/
			map.put("listjoinerLink", listjoinerLink);
			
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setData(map);
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
	}
	
}
