package com.cmcc.medicalcare.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.exception.CoderException;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.model.UserNJ;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.cmcc.medicalcare.service.IUserNJService;
import com.cmcc.medicalcare.utils.DES;
import com.cmcc.medicalcare.utils.MD5;
import com.cmcc.medicalcare.utils.Toolkit;
import com.easemob.server.api.impl.EasemobIMUsers;

import io.swagger.client.model.Nickname;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;

@Controller
@RequestMapping("/importNJPatientUser")
public class UtilsImportNJPatientUserController {
	
	@Resource
	private IUserNJService userNJService;
	
	@Resource
	private IPatientUserService patientUserService;
	
	/**
	 * 导入念加患者用户
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "importNJUser") 
	@ResponseBody 
	@SystemControllerLog(description = "导入念加患者用户")    
	public Results<String> importNJUser(HttpServletRequest request) {
		Results<String> results = new Results<String>();
		
		List<UserNJ> userList = userNJService.getList("selectAll", null);
		if (userList != null && userList.size() > 0) {
			for (int i = 0; i < userList.size(); i++) {
				UserNJ user_nj = userList.get(i);
				if (user_nj != null) {
					String patientPhone = user_nj.getUsername();
					if (Toolkit.isMobileNO(patientPhone)) {
						//查询患者信息
						PatientUser patientUser_ = patientUserService.findByParam("selectByPhone", patientPhone);
						if (null == patientUser_) {
							//创建环信用户
							EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
							RegisterUsers users = new RegisterUsers();
							User user = new User().username(Constant.patient + patientPhone).password("mingyizaixian123456789");// 环信测试帐号
							users.add(user);
							Object result = easemobIMUsers.createNewIMUserSingle(users);
							if (result != null) {
								//修改昵称
								String nickname = user_nj.getNickname();
								if (StringUtils.isBlank(nickname)) nickname = "未填写";
								Nickname nickName = new Nickname();
								nickName.setNickname(nickname);
								easemobIMUsers.modifyIMUserNickNameWithAdminToken(Constant.patient + patientPhone, nickName);
								
								//入库本地患者用户表
								PatientUser patientUser = new PatientUser();
								try {
									patientUser.setPassword(DES.encryptDESBeas64("mingyizaixian123456789"));
								} catch (CoderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								patientUser.setLoginId(Constant.patient + patientPhone);
								patientUser.setPhone(patientPhone);
								patientUser.setName(user_nj.getNickname());
								patientUser.setSex(user_nj.getSex().toString());
								patientUser.setBirthday(user_nj.getBirth());
								patientUser.setRemarks("同步念加用户");
								patientUser.setCreatetime(new Date());
								patientUserService.insert(patientUser);
							}
						}
					}
				}
			}
			
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
		} else {
			results.setCode(MessageCode.CODE_204);
			results.setMessage("没有数据");
			return results;
		}
		
	}
	
}
