/**   
* @Title: DoctorsTeamInviteController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:44:51 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.doctor;

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
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.DoctorsTeamInvite;
import com.cmcc.medicalcare.model.DoctorsTeamUserLink;
import com.cmcc.medicalcare.service.IDoctorsTeamInviteService;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.IDoctorsTeamUserLinkService;

/**
 * @ClassName: 医生团队邀请信息
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:44:51
 * 
 */
@Controller
@RequestMapping("/app/doctorsTeamInvite")
public class DoctorsTeamInviteController{
	
	@Resource
	private IDoctorsTeamInviteService doctorsTeamInviteService;
	
	@Resource
	private IDoctorsTeamUserLinkService doctorsTeamUserLinkService;
	
	@Resource
	private IDoctorsTeamService doctorsTeamService;

	/**
	 * parameter={"invitedPhone":""}
	* @Title: checkTeamInvite 
	* @Description: TODO 
	* @param @param parameter
	* @param @param request
	* @param @return    设定文件 
	* @return Results<Map<String,Object>>    返回类型 
	* @throws
	 */
	@SystemControllerLog(description = "获取团队邀请提醒信息")
	@RequestMapping(value = "tipsOfTeamInvite") 
	@ResponseBody
	public Results<Map<String, Object>> tipsOfTeamInvite(@RequestParam String parameter,HttpServletRequest request) {
		//doctors_team_invite表，根据被邀请医生电话去查看state为0的记录，然后返回一个teamInviteList
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		List<DoctorsTeamInvite> teamInviteList = null;
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String invitedPhone = dataJsonObject.getString("invitedPhone");
		
		if (StringUtils.isNotBlank(invitedPhone)) {
			teamInviteList = doctorsTeamInviteService.getList("selectByInvitedPhone", invitedPhone);
			if (teamInviteList.size() > 0) {
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
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
	
	
	@SystemControllerLog(description = "获取团队邀请信息并更新查看状态和团队列表")
	@RequestMapping(value = "getTeamInvite") 
	@ResponseBody
	public Results<Map<String, Object>> getTeamInvite(@RequestParam String parameter,HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();
		String addMemberInfo = null; //被邀请加入的团队提醒信息
		String deleteMemberInfo = null; //被移除的团队提醒信息
		List<DoctorsTeamUserLink> leaderList = null;
		List<DoctorsTeamUserLink> joinerList = null;
		
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String invitedPhone = dataJsonObject.getString("invitedPhone");
		
		if (StringUtils.isNotBlank(invitedPhone)) {
			List<DoctorsTeamInvite> teamInviteList = doctorsTeamInviteService.getList("selectByInvitedPhone", invitedPhone);
			if (teamInviteList.size() > 0) { //存在新的提醒信息
				addMemberInfo = "您已加入";
				deleteMemberInfo = "您已被移出";
				DoctorsTeamInvite teamInvite = null;
				for (int i = 0; i < teamInviteList.size(); i++) {
					teamInvite = teamInviteList.get(i);
					if (1 == teamInvite.getStatus()) { //为添加的提醒信息
						addMemberInfo += teamInvite.getTeamName() + ",";
					} else { //为移除的提醒信息
						deleteMemberInfo += teamInvite.getTeamName() + ",";
					}
					
					//默认更新状态为已查看
					teamInvite.setState(1);
					doctorsTeamInviteService.update("updateByPrimaryKeySelective", teamInvite);
				}
				
				if (addMemberInfo.indexOf(",") != -1) { //去掉末尾的分号
					addMemberInfo = addMemberInfo.substring(0, addMemberInfo.length()-1);
				} else {
					addMemberInfo = null;
				}
				
				if (deleteMemberInfo.indexOf(",") != -1) { //去掉末尾的分号
					deleteMemberInfo = deleteMemberInfo.substring(0, deleteMemberInfo.length()-1);
				} else {
					deleteMemberInfo = null;
				}
				
				resultsMap.put("addMemberInfo", addMemberInfo);
				resultsMap.put("deleteMemberInfo", deleteMemberInfo);
				
			}
			
			//获取医生为领队的团队列表
			leaderList = doctorsTeamUserLinkService.getList("getLeaderList", invitedPhone);
			if (leaderList.size() > 0) {//填充团队实体
				DoctorsTeam doctorsTeam1 = null;
				for (int i = 0; i < leaderList.size(); i++) {
					doctorsTeam1 = doctorsTeamService.findById("selectByTeamId", leaderList.get(i).getTeamId());
					if (doctorsTeam1 != null) {
						leaderList.get(i).setDoctorsTeam(doctorsTeam1);
					}
				}
			}
			resultsMap.put("leaderList", leaderList);
			
			//获取医生为普通成员的团队列表,按最新时间倒序
			joinerList = doctorsTeamUserLinkService.getList("getJoinerList", invitedPhone);
			if (joinerList.size() > 0) {//填充团队实体
				DoctorsTeam doctorsTeam2 = null;
				for (int i = 0; i < joinerList.size(); i++) {
					doctorsTeam2 = doctorsTeamService.findById("selectByTeamId", joinerList.get(i).getTeamId());
					if (doctorsTeam2 != null) {
						joinerList.get(i).setDoctorsTeam(doctorsTeam2);
					}
				}
			}
			resultsMap.put("joinerList", joinerList);
			
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setData(resultsMap);
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
	}
	
}
