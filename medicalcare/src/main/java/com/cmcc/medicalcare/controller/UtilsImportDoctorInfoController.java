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
import com.cmcc.medicalcare.model.ContractedHospitals;
import com.cmcc.medicalcare.model.ContractedHospitalsDoctorsLink;
import com.cmcc.medicalcare.model.DoctorsLoginInfo;
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.DoctorsTeamUserLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.ExcelDoctorUserEntity;
import com.cmcc.medicalcare.service.IContractedHospitalsDoctorsLinkService;
import com.cmcc.medicalcare.service.IContractedHospitalsService;
import com.cmcc.medicalcare.service.IDoctorsLoginInfoService;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.IDoctorsTeamUserLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.ISecretaryDoctorsLinkService;
import com.cmcc.medicalcare.utils.DES;
import com.cmcc.medicalcare.utils.MD5;
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
@RequestMapping("/utils")
public class UtilsImportDoctorInfoController extends BaseController {

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
		
		String filePath = "C:/Users/zds/Desktop/aaaaaaa/20170802002.xlsx";
		List<ExcelDoctorUserEntity> excelDoctorUserEntityList = null;
		try {
			excelDoctorUserEntityList = readDoctorsUserExcel(filePath); //读取Excel获取记录集
		} catch (IOException e1) {
			e1.printStackTrace();
			results.setCode(MessageCode.CODE_501);//没有任何数据
			results.setMessage("导入失败！！！");
			return results;
		}
    	if (excelDoctorUserEntityList != null) {
    		int totalCount = excelDoctorUserEntityList.size(); //总数
    		int successCount = 0; //成功数
    		int failCount = 0; //失败数
    		for(int i=0; i<excelDoctorUserEntityList.size(); i++) {//遍历记录集
    			ExcelDoctorUserEntity excelDoctorUserEntity = excelDoctorUserEntityList.get(i);//获取当前医生实体
    			String doctorsPhone = excelDoctorUserEntity.getPhone();
    			String doctorsName = excelDoctorUserEntity.getName();
    			String hospitalName = excelDoctorUserEntity.getHospital();
    			//记录中电话，姓名，就职医院为空的不入库
    			if (excelDoctorUserEntity != null && StringUtils.isNotBlank(doctorsPhone) 
    					&& StringUtils.isNotBlank(doctorsName) && StringUtils.isNotBlank(hospitalName)) {
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
        					nickName.setNickname(excelDoctorUserEntityList.get(i).getName());
        					easemobIMUsers.modifyIMUserNickNameWithAdminToken(Constant.doctor + doctorsPhone, nickName); //添加昵称

        					//本地入库
        					DoctorsUser doctorsUser = new DoctorsUser();
        					doctorsUser.setPhone(doctorsPhone);
        					doctorsUser.setName(doctorsName);
        					doctorsUser.setSex(excelDoctorUserEntity.getSex());
        					doctorsUser.setTitle(excelDoctorUserEntity.getTitle());
        					doctorsUser.setHospital(excelDoctorUserEntity.getHospital());
        					doctorsUser.setDepartment(excelDoctorUserEntity.getDepartment());
        					doctorsUser.setStartWorkingTime(excelDoctorUserEntity.getStartWorkingTime());
        					doctorsUser.setDepartmentTelephone(excelDoctorUserEntity.getDepartmentTelephone());
        					doctorsUser.setIntroduction(excelDoctorUserEntity.getIntroduction());
        					doctorsUser.setDoctortype(excelDoctorUserEntity.getDoctortype());
        					doctorsUser.setStatus(1);
        					doctorsUser.setEasemobUuid(uuid);// 环信返回来的uuid
        					doctorsUser.setTestAccount(1);
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
        					Integer doctorsTeamId = null;
        					DoctorsTeam doctorsTeam_ = doctorsTeamService.findByParam("selectByHospitalName", hospitalName);
        					if (null == doctorsTeam_) {
        						DoctorsTeam doctorsTeam = new DoctorsTeam();
        						doctorsTeam.setName(hospitalName);
        						doctorsTeam.setCityName(excelDoctorUserEntity.getCityName());
        						doctorsTeam.setArea(excelDoctorUserEntity.getArea());
        						doctorsTeam.setHospitalPhone(excelDoctorUserEntity.getHospitalPhone());
        						doctorsTeam.setHospitalAddr(excelDoctorUserEntity.getHospitalAddr());
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
        					
        					//如果是家庭医生，则维护contracted_hospitals表和contracted_hospitals_doctors_link表
        					if (2 == excelDoctorUserEntity.getDoctortype()) {
        						// 维护就职医院表contracted_hospitals
            					ContractedHospitals contractedHospitals_ = contractedHospitalsService.findByParam("selectByHospitalName", hospitalName);
            					if (null == contractedHospitals_) {
            						ContractedHospitals contractedHospitals = new ContractedHospitals();
            						contractedHospitals.setHospitalName(hospitalName);
            						contractedHospitals.setCityName(excelDoctorUserEntity.getCityName());
            						contractedHospitals.setArea(excelDoctorUserEntity.getArea());
            						contractedHospitals.setHospitalPhone(excelDoctorUserEntity.getHospitalPhone());
            						contractedHospitals.setHospitalAddr(excelDoctorUserEntity.getHospitalAddr());
            						contractedHospitals.setCreatetime(new Date());
            						contractedHospitalsService.insert(contractedHospitals);
            					}
            					
            					//维护医院与医生关联表contracted_hospitals_doctors_link
            					ContractedHospitals contractedHospitals_2 = contractedHospitalsService.findByParam("selectByHospitalName", hospitalName);
            					if (contractedHospitals_2 != null) {
            						Map<String, Object> paramMap2 = new HashMap<String, Object>();
                					paramMap2.put("hospitalId", contractedHospitals_2.getId());
                					paramMap2.put("doctorPhone", doctorsPhone);
                					ContractedHospitalsDoctorsLink contractedHospitalsDoctorsLink_ = contractedHospitalsDoctorsLinkService.findByParam(
                							"selectByHospitalIdAndDoctorPhone", paramMap2);
                					if (null == contractedHospitalsDoctorsLink_) {
                						ContractedHospitalsDoctorsLink contractedHospitalsDoctorsLink = new ContractedHospitalsDoctorsLink();
                						contractedHospitalsDoctorsLink.setHospitalId(contractedHospitals_2.getId());
                						contractedHospitalsDoctorsLink.setDoctorPhone(doctorsPhone);
                						contractedHospitalsDoctorsLink.setCreatetime(new Date());
                						contractedHospitalsDoctorsLinkService.insert(contractedHospitalsDoctorsLink);
                					}
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
	 * 读取医生excel信息
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public List<ExcelDoctorUserEntity> readDoctorsUserExcel(String path) throws IOException {
		if (null == path || "".equals(path.trim())) {
			return null;
		} else {
			String postfix = path.substring(path.lastIndexOf(".") + 1, path.length());
			if (postfix != "") {
				if ("xls".equals(postfix)) {
					return readDoctorsUserXls(path);
				} else if ("xlsx".equals(postfix)) {
					return readDoctorsUserXlsx(path);
				}
			} else {
				System.out.println(path + " : Not the Excel file!");
			}
		}
		return null;
	}


	/**
	 * 读取Xls格式
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public List<ExcelDoctorUserEntity> readDoctorsUserXls(String path) {
		System.out.println("Processing..." + path);
		InputStream is = null;
		HSSFWorkbook hssfWorkbook = null;
		SimpleDateFormat df_1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		SimpleDateFormat df_2 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			is = new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			if (is != null)
				hssfWorkbook = new HSSFWorkbook(is);
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

		ExcelDoctorUserEntity excelDoctorUserEntity = null;
		List<ExcelDoctorUserEntity> list = new ArrayList<ExcelDoctorUserEntity>();

		if (null == hssfWorkbook)
			return null;
		// Read the Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (null == hssfSheet) {
				continue;
			}
			// Read the Row
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (null == hssfRow) {
					break;
				} else {
					excelDoctorUserEntity = new ExcelDoctorUserEntity();
					
					excelDoctorUserEntity.setPhone(getStringValue(hssfRow.getCell(1)));
					excelDoctorUserEntity.setName(getStringValue(hssfRow.getCell(2)));
					excelDoctorUserEntity.setSex(getStringValue(hssfRow.getCell(3)));
					excelDoctorUserEntity.setTitle(getStringValue(hssfRow.getCell(4)));
					excelDoctorUserEntity.setHospital(getStringValue(hssfRow.getCell(5)));
					excelDoctorUserEntity.setDepartment(getStringValue(hssfRow.getCell(6)));
					try {
						String date_str = getStringValue(hssfRow.getCell(7)); 
						excelDoctorUserEntity.setStartWorkingTime(df_2.parse(df_2.format(df_1.parse(date_str))));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						excelDoctorUserEntity.setStartWorkingTime(null);
					}
					excelDoctorUserEntity.setDepartmentTelephone(getStringValue(hssfRow.getCell(8)));
					excelDoctorUserEntity.setIntroduction(getStringValue(hssfRow.getCell(9)));
					try {
						excelDoctorUserEntity.setDoctortype(Integer.parseInt(getStringValue(hssfRow.getCell(10))));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						excelDoctorUserEntity.setDoctortype(1);
					}
					excelDoctorUserEntity.setCityName(getStringValue(hssfRow.getCell(11)));
					excelDoctorUserEntity.setArea(getStringValue(hssfRow.getCell(12)));
					excelDoctorUserEntity.setHospitalPhone(getStringValue(hssfRow.getCell(13)));
					excelDoctorUserEntity.setHospitalAddr(getStringValue(hssfRow.getCell(14)));

					if (list != null)
						list.add(excelDoctorUserEntity);
				}
			}
		}

		/*
		 * try{ is.close(); }catch(IOException e){ throw e; }finally{ if(is !=
		 * null) is.close(); }
		 */

		return list;
	}

	/**
	 * 读取Xlsx格式
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public List<ExcelDoctorUserEntity> readDoctorsUserXlsx(String path) {
		System.out.println("Processing..." + path);
		InputStream is = null;
		XSSFWorkbook xssfWorkbook = null;
		SimpleDateFormat df_1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		SimpleDateFormat df_2 = new SimpleDateFormat("yyyy-MM-dd");
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

		ExcelDoctorUserEntity excelDoctorUserEntity = null;
		List<ExcelDoctorUserEntity> list = new ArrayList<ExcelDoctorUserEntity>();

		if (null == xssfWorkbook)
			return null;
		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (null == xssfSheet) {
				continue;
			}
			// Read the Row
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (null == xssfRow) {
					break;
				} else {
					excelDoctorUserEntity = new ExcelDoctorUserEntity();
					
					excelDoctorUserEntity.setPhone(getStringValue(xssfRow.getCell(1)));
					excelDoctorUserEntity.setName(getStringValue(xssfRow.getCell(2)));
					excelDoctorUserEntity.setSex(getStringValue(xssfRow.getCell(3)));
					excelDoctorUserEntity.setTitle(getStringValue(xssfRow.getCell(4)));
					excelDoctorUserEntity.setHospital(getStringValue(xssfRow.getCell(5)));
					excelDoctorUserEntity.setDepartment(getStringValue(xssfRow.getCell(6)));
					try {
						String date_str = getStringValue(xssfRow.getCell(7)); 
						excelDoctorUserEntity.setStartWorkingTime(df_2.parse(df_2.format(df_1.parse(date_str))));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						excelDoctorUserEntity.setStartWorkingTime(null);
					}
					excelDoctorUserEntity.setDepartmentTelephone(getStringValue(xssfRow.getCell(8)));
					excelDoctorUserEntity.setIntroduction(getStringValue(xssfRow.getCell(9)));
					try {
						excelDoctorUserEntity.setDoctortype(Integer.parseInt(getStringValue(xssfRow.getCell(10))));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						excelDoctorUserEntity.setDoctortype(1);
					}
					excelDoctorUserEntity.setCityName(getStringValue(xssfRow.getCell(11)));
					excelDoctorUserEntity.setArea(getStringValue(xssfRow.getCell(12)));
					excelDoctorUserEntity.setHospitalPhone(getStringValue(xssfRow.getCell(13)));
					excelDoctorUserEntity.setHospitalAddr(getStringValue(xssfRow.getCell(14)));

					if (list != null)
						list.add(excelDoctorUserEntity);
				}

			}
		}
		
		return list;
		
	}


	/**
	 * 获取字符串值
	 * 
	 * @param hssfCell
	 * @return
	 */
	private static String getStringValue(HSSFCell hssfCell) {
		if (null == hssfCell)
			return null;
		if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) { // boolean类型
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) { // 数字类型
			if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {//如果是date类型则 ，获取该cell的date值
				return HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue()).toString();
			}
			hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// return hssfCell.getStringCellValue();
			return new DecimalFormat("#.##").format(Double.valueOf(hssfCell.getStringCellValue()));
		} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) { // 表达式类型
			// return hssfCell.getCellFormula();
			String value = null;
			try {
				DecimalFormat df = new DecimalFormat("#.##");
				value = df.format(Double.valueOf(String.valueOf(hssfCell.getNumericCellValue())));
			} catch (IllegalStateException e) {
				try {
					DecimalFormat df = new DecimalFormat("#.##");
					value = df.format(Double.valueOf(String.valueOf(hssfCell.getRichStringCellValue())));
				} catch (Exception e1) {
					value = hssfCell.getCellFormula();
				}
			}
			return value;
		} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_STRING) { // 字符串类型
			return hssfCell.getStringCellValue();
		} else {
			return "";
		}
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
