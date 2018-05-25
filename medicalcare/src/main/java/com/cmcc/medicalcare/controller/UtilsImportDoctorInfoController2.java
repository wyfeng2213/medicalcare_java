package com.cmcc.medicalcare.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.exception.CoderException;
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.DoctorsTeamUserLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.ExcelDoctorUserEntity2;
import com.cmcc.medicalcare.service.IContractedHospitalsDoctorsLinkService;
import com.cmcc.medicalcare.service.IContractedHospitalsService;
import com.cmcc.medicalcare.service.IDoctorsLoginInfoService;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.IDoctorsTeamUserLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.ISecretaryDoctorsLinkService;
import com.cmcc.medicalcare.utils.DES;
import com.easemob.server.api.impl.EasemobIMUsers;

import io.swagger.client.model.Nickname;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import net.sf.json.JSONArray;

/**
 * 
 * 导入医生用户工具类
 *
 */
@Controller
@RequestMapping("/utils2")
public class UtilsImportDoctorInfoController2 extends BaseController {

	@Resource
	private IDoctorsUserService doctorsUserService;

	@Resource
	private ISecretaryDoctorsLinkService secretaryDoctorsLinkService;

	@Resource
	private IDoctorsTeamService doctorsTeamService;

	@Resource
	private IDoctorsTeamUserLinkService doctorsTeamUserLinkService;
	
	@Resource
	private IDoctorsLoginInfoService doctorsLoginInfoService;
	
	@Resource
	private IContractedHospitalsService contractedHospitalsService;
	
	@Resource
	private IContractedHospitalsDoctorsLinkService contractedHospitalsDoctorsLinkService;
	
	/**
	 * 
	* @Title: ImportDoctors 
	* @Description: TODO 
	* @param @param request
	* @param @return    设定文件 
	* @return Results<String>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "ImportDoctors")
	@ResponseBody
	@SystemControllerLog(description = "导入医生用户数据")
	public Results<Map<String, Object>> ImportDoctors(HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		String filePath = "C:/Users/zds/Desktop/aaaaaaa/aaa/20170928003.xlsx";
		List<ExcelDoctorUserEntity2> excelDoctorUserEntity2List = null;
		try {
			excelDoctorUserEntity2List = readDoctorsUserXlsx(filePath); //读取Excel获取记录集
		} catch (Exception e1) {
			e1.printStackTrace();
			results.setCode(MessageCode.CODE_501);//没有任何数据
			results.setMessage("导入失败！！！");
			return results;
		}
    	if (excelDoctorUserEntity2List != null) {
    		int totalCount = excelDoctorUserEntity2List.size(); //总数
    		int successCount = 0; //成功数
    		int failCount = 0; //失败数
    		for(int i=0; i<excelDoctorUserEntity2List.size(); i++) {//遍历记录集
    			ExcelDoctorUserEntity2 excelDoctorUserEntity2 = excelDoctorUserEntity2List.get(i);//获取当前医生实体
    			String doctorsPhone = excelDoctorUserEntity2.getPhone();
    			String doctorsName = excelDoctorUserEntity2.getName();
    			//记录中电话，姓名为空的不入库
    			if (excelDoctorUserEntity2 != null && StringUtils.isNotBlank(doctorsPhone) && StringUtils.isNotBlank(doctorsName)) {
    				DoctorsUser doctorsUser_ = doctorsUserService.findByParam("selectByPhone", doctorsPhone);
            		if (null == doctorsUser_) {//重复记录不入库
            			/**
            			 * 先创建环信用户
            			 */
            			EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
            			RegisterUsers users = new RegisterUsers();
            			User user = new User().username(Constant.doctor + doctorsPhone).password("mingyizaixian123456789");// 环信测试帐号
        				users.add(user);
        				Object result = easemobIMUsers.createNewIMUserSingle(users);
        				if (result != null) {//环信用户创建成功
        					// 解释result
            				JSONObject resultObject = JSONObject.parseObject(result.toString());
            				JSONArray entities = JSONArray.fromObject(resultObject.get("entities"));
            				String entity = entities.get(0).toString();
            				JSONObject entityObject = JSONObject.parseObject(entity);
            				String uuid = (String) entityObject.getString("uuid");
        					Nickname nickName = new Nickname();
        					nickName.setNickname(excelDoctorUserEntity2List.get(i).getName());
        					easemobIMUsers.modifyIMUserNickNameWithAdminToken(Constant.doctor + doctorsPhone, nickName); //添加昵称

        					//本地入库
        					DoctorsUser doctorsUser = new DoctorsUser();
        					doctorsUser.setName(doctorsName);
        					doctorsUser.setPhone(doctorsPhone);
        					doctorsUser.setDepartment(excelDoctorUserEntity2.getDepartment());
        					doctorsUser.setTitle(excelDoctorUserEntity2.getTitle());
        					String introduction = excelDoctorUserEntity2.getIntroduction() + "----" + excelDoctorUserEntity2.getIntroduction2();
        					doctorsUser.setIntroduction(introduction);
        					doctorsUser.setHospital(excelDoctorUserEntity2.getHospital());
        					doctorsUser.setGraphicConsultingPrice(excelDoctorUserEntity2.getGraphicConsultingPrice());
        					doctorsUser.setVideoConsultationPrice(excelDoctorUserEntity2.getVideoConsultationPrice());
        					doctorsUser.setDoctortype(1);
        					doctorsUser.setStatus(1);
        					doctorsUser.setTestAccount(1);
        					doctorsUser.setEasemobUuid(uuid);// 环信返回来的uuid
        					try {
								doctorsUser.setPassword(DES.encryptDESBeas64("mingyizaixian123456789"));
							} catch (CoderException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
        					doctorsUser.setLoginId(Constant.doctor + doctorsPhone);
        					doctorsUser.setCreatetime(new Date());
        					doctorsUserService.insert(doctorsUser);
        					
        					// 维护团队表
        					String hospitalName = excelDoctorUserEntity2.getHospital();
        					if (StringUtils.isNotBlank(hospitalName) && !hospitalName.contains("退休")) {
        						Integer doctorsTeamId = null;
            					DoctorsTeam doctorsTeam_ = doctorsTeamService.findByParam("selectByHospitalName", hospitalName);
            					if (null == doctorsTeam_) {
            						DoctorsTeam doctorsTeam = new DoctorsTeam();
            						doctorsTeam.setName(hospitalName);
                					doctorsTeam.setCreatetime(new Date());
                					doctorsTeamService.insert(doctorsTeam);
                					doctorsTeamId = doctorsTeam.getId();
            					} else {
            						doctorsTeamId = doctorsTeam_.getId();
            					}

            					// 维护团队成员关系表
            					Map<String, Object> paramMap = new HashMap<String,Object>();
            					paramMap.put("doctors_phone", doctorsPhone);
            					paramMap.put("team_id", doctorsTeamId);
            					List<DoctorsTeamUserLink> doctorsTeamUserLinkList = doctorsTeamUserLinkService.getList("selectByTeamidAndPhone", paramMap);
            					if (null == doctorsTeamUserLinkList || doctorsTeamUserLinkList.size() <= 0) {
            						DoctorsTeamUserLink doctorsTeamUserLink = new DoctorsTeamUserLink();
                					doctorsTeamUserLink.setDoctorsLoginId(Constant.doctor + doctorsPhone);
                					doctorsTeamUserLink.setDoctorsName(doctorsName);
                					doctorsTeamUserLink.setDoctorsPhone(doctorsPhone);
                					doctorsTeamUserLink.setTeamName(hospitalName);
                					doctorsTeamUserLink.setIsleader(0);
                					doctorsTeamUserLink.setStatus(0);
                					doctorsTeamUserLink.setTeamId(doctorsTeamId);
                					doctorsTeamUserLink.setCreatetime(new Date());
                					doctorsTeamUserLinkService.insert(doctorsTeamUserLink);
            					}
        					}
        					
        					Integer id = doctorsUser.getId();
        					if (id != null) { //导入成功
        						successCount++; //成功数加1
        					}
        				}
            		}
    			}
        	}
    		failCount = totalCount - successCount;
    		map.put("总条数", totalCount);
			map.put("成功条数", successCount);
			map.put("失败条数", failCount);
			results.setData(map);
            results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
    	}

		return results;
	}

	/**
	 * 读取Xlsx格式
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public List<ExcelDoctorUserEntity2> readDoctorsUserXlsx(String path) {
		System.out.println("Processing..." + path);
		InputStream is = null;
		XSSFWorkbook xssfWorkbook = null;
		try {
			is = new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			if (is != null)
				xssfWorkbook = new XSSFWorkbook(is);
			if (is != null)
				is.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		ExcelDoctorUserEntity2 excelDoctorUserEntity2 = null;
		List<ExcelDoctorUserEntity2> list = new ArrayList<ExcelDoctorUserEntity2>();

		if (null == xssfWorkbook)
			return null;
		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (null == xssfSheet) {
				continue;
			}
			// Read the Row
			for (int rowNum = 3; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (null == xssfRow) {
					break;
				} else {
					excelDoctorUserEntity2 = new ExcelDoctorUserEntity2();
					
					excelDoctorUserEntity2.setName(getStringValue(xssfRow.getCell(0)));
					excelDoctorUserEntity2.setPhone(getStringValue(xssfRow.getCell(1)));
					excelDoctorUserEntity2.setDepartment(getStringValue(xssfRow.getCell(2)));
					excelDoctorUserEntity2.setTitle(getStringValue(xssfRow.getCell(3)));
					excelDoctorUserEntity2.setIntroduction(getStringValue(xssfRow.getCell(4)));
					excelDoctorUserEntity2.setIntroduction2(getStringValue(xssfRow.getCell(5)));
					excelDoctorUserEntity2.setHospital(getStringValue(xssfRow.getCell(6)));
					excelDoctorUserEntity2.setGraphicConsultingPrice(getStringValue(xssfRow.getCell(7)));
					excelDoctorUserEntity2.setVideoConsultationPrice(getStringValue(xssfRow.getCell(8)));

					if (list != null)
						list.add(excelDoctorUserEntity2);
				}

			}
		}
		
		return list;
		
	}

	/**
	 * 获取字符串值
	 * 
	 * @param xssfCell
	 * @return
	 */
	private static String getStringValue(XSSFCell xssfCell) {
		if (null == xssfCell)
			return null;
		if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) { // boolean类型
			return String.valueOf(xssfCell.getBooleanCellValue());
		} else if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) { // 数字类型
			if (HSSFDateUtil.isCellDateFormatted(xssfCell)) {//如果是date类型则 ，获取该cell的date值
				return HSSFDateUtil.getJavaDate(xssfCell.getNumericCellValue()).toString();
			}
			xssfCell.setCellType(XSSFCell.CELL_TYPE_STRING);
			// return xssfCell.getStringCellValue();
			return new DecimalFormat("#.##").format(Double.valueOf(xssfCell.getStringCellValue()));
		} else if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) { // 表达式类型
			// return xssfCell.getCellFormula();
			String value = null;
			try {
				DecimalFormat df = new DecimalFormat("#.##");
				value = df.format(Double.valueOf(String.valueOf(xssfCell.getNumericCellValue())));
			} catch (IllegalStateException e) {
				try {
					DecimalFormat df = new DecimalFormat("#.##");
					value = df.format(Double.valueOf(String.valueOf(xssfCell.getRichStringCellValue())));
				} catch (Exception e1) {
					value = xssfCell.getCellFormula();
				}
			}
			return value;
		} else if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_STRING) { // 字符串类型
			return xssfCell.getStringCellValue();
		} else {
			return "";
		}
	}
	
}
