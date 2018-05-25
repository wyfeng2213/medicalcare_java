package com.cmcc.medicalcare.service.Impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.inter.jiuzhoutong.MemberUserUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.PatientUserUtils;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.service.IPatientUserService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="patientUserService")
@Transactional
public class PatientUserServiceImpl extends GenericServiceImpl<PatientUser, Integer> implements IPatientUserService{
	
	@Override
	public PatientUser findPatientUser(String patientPhone) {
		//查询本地表患者用户信息
		PatientUser patientUser = this.findByParam("selectByPhone", patientPhone);
		
		if (null == patientUser) {//本地表不存在
			return null;
		} else {
			try {
				String identification =  patientUser.getIdentification();
				if (StringUtils.isNotBlank(identification)) {
					//同步九州通会员信息
					this.saveMemberInfo(patientUser.getPhone(), patientUser.getName(), patientUser.getIdentification(), "9", "");
					
					//同步九州通患者信息
					this.savePatientInfo(patientUser.getPhone(), patientUser.getName(), patientUser.getPhone(), "", "", "", "", patientUser.getIdentification());
				} else {
					//查询九州通数据同步本地历史数据
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return patientUser;
		}
	}
	
	@Override
	public String findPatientUser(String patientId, String memberId) {
		//查询本地表患者用户信息
		String jsonStr = PatientUserUtils.findPatient(patientId, memberId);
		
		return jsonStr;
	}

	/**
	 * 注册九州通会员信息
	 */
	@Override
	public String saveMemberInfo(String phone, String name, String idNumber, String gender, String age) {
		String return_str = null;
		try {
			return_str = MemberUserUtils.saveMember(phone, name, idNumber, gender, age);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return return_str;
	}
	
	/**
	 * 注册九州通患者信息
	 */
	@Override
	public String savePatientInfo(String phone, String name, String memberPhone, String age, String gender,
			String occupation, String height, String idNumber) {
		String return_str = null;
		try {
			return_str = PatientUserUtils.savePatient(phone, name, memberPhone, age, gender, occupation, height, idNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return return_str;
	}
	
	/**
	 * 更新九州通会员信息
	 */
	@Override
	public String updateMemberInfo(String phone, String name, String updatePhone, String idNumber, String gender,
			String age) {
		String return_str = null;
		try {
			return_str = MemberUserUtils.updateMember(phone, name, updatePhone, idNumber, gender, age);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return return_str;
	}

	/**
	 * 更新九州通患者信息
	 */
	@Override
	public String updatePatientInfo(String phone, String name, String memberPhone, String age,
			String gender, String occupation, String height, String idNumber, String memberId, String patientId) {
		String return_str = null;
		try {
			return_str = PatientUserUtils.updatePatient(phone, name, patientId, memberPhone, age, gender, occupation, height, idNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return return_str;
	}
	
}
