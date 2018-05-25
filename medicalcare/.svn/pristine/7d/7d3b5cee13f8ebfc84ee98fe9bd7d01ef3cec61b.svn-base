/**   
* @Title: DoctorsTeamController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:43:20 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.doctor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.DoctorsTeamInvite;
import com.cmcc.medicalcare.model.DoctorsTeamUserLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.service.IDoctorsTeamInviteService;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.IDoctorsTeamUserLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.utils.EmojiFilter;

/**
 * @ClassName: 医生团队资料
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:43:20
 * 
 */
@Controller
@RequestMapping("/app/doctorsTeam")
public class DoctorsTeamController {

	@Resource
	private IDoctorsTeamService doctorsTeamService;

	@Resource
	private IDoctorsTeamUserLinkService doctorsTeamUserLinkService;

	@Resource
	private IDoctorsUserService doctorsUserService;
	
	@Resource
	private IDoctorsTeamInviteService doctorsTeamInviteService;

	/**
	 * parameter={"teamId":"","doctorsPhone":""}
	 * 
	 * @Title: select @Description: TODO @param @param request @param @return
	 *         设定文件 @return Results<List<DoctorsTeam>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "查询团队资料和成员")
	@RequestMapping(value = "selectTeamInfo")
	@ResponseBody
	public Results<Map<String, Object>> selectTeamInfo(HttpServletRequest request, String parameter) {
		// 对应doctors_team表,根据团队id查找团队信息；根据团队id和医生号码找到该医生查看某团队的状态，取消提示红点
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DoctorsTeamUserLink> listDoctorsTeamUser = null;
		DoctorsUser doctorsUser = null;
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String team_id = dataJsonObject.getString("teamId");
		String doctorsPhone = dataJsonObject.getString("doctorsPhone");

		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("team_id", team_id);
		paraMap.put("doctors_phone", doctorsPhone);

		Map<String, Object> paraMap2 = new HashMap<String, Object>();
		paraMap2.put("team_id", team_id);

		if (StringUtils.isNotBlank(team_id) && StringUtils.isNotBlank(doctorsPhone)) {
			int id = Integer.parseInt(team_id.trim());
			String phone = doctorsPhone;
			doctorsUser = doctorsUserService.findByParam("selectByPhone", phone);
			List<DoctorsTeamUserLink> doctorsTeamUserLinkList = doctorsTeamUserLinkService.getList("selectByTeamidAndPhone",
					paraMap);

			if (doctorsTeamUserLinkList != null&&doctorsTeamUserLinkList.size()>0) {
				DoctorsTeamUserLink doctorsTeamUserLink = doctorsTeamUserLinkList.get(0);
				doctorsTeamUserLink.setStatus(0);// 更新团队某成员的查看团队资料状态0为查看过，1为未查看过
				doctorsTeamUserLink.setUpdatetime(new Date());
				doctorsTeamUserLinkService.update("updateByPrimaryKey", doctorsTeamUserLink);
				DoctorsTeam doctorsTeam = doctorsTeamService.findByParam("selectByPrimaryKey", id);
				listDoctorsTeamUser = doctorsTeamUserLinkService.getList("selectByTeamId", paraMap2);
				for(int i=0;i<listDoctorsTeamUser.size();i++){
					String doctors_Phone = listDoctorsTeamUser.get(i).getDoctorsPhone();
					DoctorsUser doctorsUser2 = doctorsUserService.findByParam("selectByDoctorsPhone", doctors_Phone);
					listDoctorsTeamUser.get(i).setDoctorsUser(doctorsUser2);
				}
				
				 if (doctorsPhone.equals(doctorsTeam.getDoctorsPhone())) {
				 map.put("isleader", "yes");// 是团队领导显示"+""-"号,修改团队名称和团队介绍
				 } else {
				 map.put("isleader", "no");// 不显示"+""-"号和不显示修改团队名称和团队介绍
				 }
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				map.put("doctorsUser", doctorsUser);
				map.put("doctorsTeam", doctorsTeam);
				map.put("listDoctorsTeamUser", listDoctorsTeamUser);
				results.setData(map);
				return results;
			} else {
				results.setCode(MessageCode.CODE_404);
				results.setMessage(MessageCode.MESSAGE_404);
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	/**
	 * parameter={"teamId":"","introduction":""}
	 * 
	 * @Title: updateTeamIntroduction @Description: TODO @param @param
	 *         request @param @param parameter @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "更新团队介绍")
	@RequestMapping(value = "updateTeamIntroduction")
	@ResponseBody
	public Results<Map<String, Object>> updateTeamIntroduction(HttpServletRequest request, String parameter) {
		// 对应doctors_team表,根据团队id查找团队信息，更新团队介绍
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String team_id = dataJsonObject.getString("teamId");
		String introduction = dataJsonObject.getString("introduction");

		if (StringUtils.isNotBlank(team_id)) {
			int id = Integer.parseInt(team_id);
			DoctorsTeam doctorsTeam = doctorsTeamService.findByParam("selectByPrimaryKey", id);
			doctorsTeam.setIntroduction(EmojiFilter.filterEmoji(introduction));
			doctorsTeam.setUpdatetime(new Date());
			doctorsTeamService.update("updateByPrimaryKey", doctorsTeam);
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

	/**
	 * parameter={"teamId":"","teamName":""}
	 * 
	 * @Title: updateTeamIntroduction @Description: TODO @param @param
	 *         request @param @param parameter @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "更新团队名称")
	@RequestMapping(value = "updateTeamName")
	@ResponseBody
	public Results<Map<String, Object>> updateTeamName(HttpServletRequest request, String parameter) {
		// 对应doctors_team表,根据团队id查找团队信息，更新团队名称
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String team_id = dataJsonObject.getString("teamId");
		String teamName = dataJsonObject.getString("teamName");

		if (StringUtils.isNotBlank(team_id)) {
			int id = Integer.parseInt(team_id);
			DoctorsTeam doctorsTeam = doctorsTeamService.findByParam("selectByPrimaryKey", id);
			doctorsTeam.setName(teamName);
			doctorsTeam.setUpdatetime(new Date());
			doctorsTeamService.update("updateByPrimaryKey", doctorsTeam);
			
			String doctorsPhone = doctorsTeam.getDoctorsPhone();
			//更新doctors_team_invite表(invite_phone)
			List<DoctorsTeamInvite> listDoctorsTeamInvite = doctorsTeamInviteService.getList("selectByDoctorsPhone", doctorsPhone);
			if (listDoctorsTeamInvite.size() > 0) {
				for (int i=0; i<listDoctorsTeamInvite.size(); i++) {
					if (doctorsPhone.equals(listDoctorsTeamInvite.get(i).getInvitePhone())) {
						listDoctorsTeamInvite.get(i).setTeamName(teamName);
						doctorsTeamInviteService.update("updateByPrimaryKeySelective", listDoctorsTeamInvite.get(i));
					}
				}
			}
			
			//更新doctors_team_user_link表
			List<DoctorsTeamUserLink> listDoctorsTeamUserLink = doctorsTeamUserLinkService.getList("selectByDoctorsPhone", doctorsPhone);
			if (listDoctorsTeamUserLink.size() > 0) {
				for (int i=0; i<listDoctorsTeamUserLink.size(); i++) {
					listDoctorsTeamUserLink.get(i).setTeamName(teamName);
					doctorsTeamUserLinkService.update("updateByPrimaryKeySelective", listDoctorsTeamUserLink.get(i));
				}
			}
			
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
