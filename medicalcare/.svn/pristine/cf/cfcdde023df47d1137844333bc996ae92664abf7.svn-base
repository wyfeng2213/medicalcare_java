package com.cmcc.medicalcare.controller.sys.doctor;

import java.io.File;
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
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.BaseController;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.exception.CoderException;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.ContractedHospitals;
import com.cmcc.medicalcare.model.ContractedHospitalsDoctorsLink;
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.DoctorsTeamUserLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.ExcelDoctorUserEntity;
import com.cmcc.medicalcare.model.ExcelDoctorUserEntity3;
import com.cmcc.medicalcare.service.IContractedHospitalsDoctorsLinkService;
import com.cmcc.medicalcare.service.IContractedHospitalsService;
import com.cmcc.medicalcare.service.IDoctorsLoginInfoService;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.IDoctorsTeamUserLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.utils.DES;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.easemob.server.api.impl.EasemobIMUsers;

import io.swagger.client.model.Nickname;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import net.sf.json.JSONArray;

/**
 * 医生档案管理
 * 
 * @author wsm
 *
 */
@Controller
@RequestMapping("/sys/doctorRecord")
public class SystemDoctorsRecordController extends BaseController {

	// 输出日志
	private static Logger log = Logger.getLogger(SystemDoctorsRecordController.class);

	/**
	 * doctorsUserService
	 */
	@Resource
	private IDoctorsUserService doctorsUserService;

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
	 * 用户管理页
	 *
	 * @return
	 */
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	@SystemControllerLog(description = "跳转到医生档案管理页面")
	public String manager() {
		return "/doctor/doctorRecord";
	}

	/**
	 * 用户管理页
	 *
	 * @return
	 */
	@RequestMapping(value = "/findUserPage")
	@ResponseBody
	@SystemControllerLog(description = "查找医生档案")
	public Map<String, Object> findUserPage(HttpServletResponse response, DoctorsUser doctorsUser, String hospitalId2,
			@RequestParam(value = "page", defaultValue = "1") Long page,
			@RequestParam(value = "rows", defaultValue = "10") Long rows) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("phone", doctorsUser.getPhone());
		map.put("name", doctorsUser.getName());
		map.put("title", doctorsUser.getTitle());
		map.put("hospital", doctorsUser.getHospital());
		map.put("department", doctorsUser.getDepartment());
		Pager<DoctorsUser> systemUserPage = new Pager<DoctorsUser>();
		try {

			if (StringUtils.isNotBlank(hospitalId2) && !"null".equalsIgnoreCase(hospitalId2)) {
				map.put("hospitalId", hospitalId2);
				systemUserPage = doctorsUserService.getList("selectAllBySys2", getPageIndex(page), getPageSize(rows),
						map);
			} else {
				systemUserPage = doctorsUserService.getList("selectAllBySys", getPageIndex(page), getPageSize(rows),
						map);
			}

			map.put("total", systemUserPage.getTotalCount());
			map.put("rows", systemUserPage.getList());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", "");
		}
		return map;
	}

	/**
	 * 详情医生档案页
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryPage")
	@SystemControllerLog(description = "跳转到医生档案详情页面")
	public String queryPage(int id, Model model) {
		try {
			DoctorsUser doctorUser = doctorsUserService.findById("selectByPrimaryKey", id);
			model.addAttribute("doctorRecord", doctorUser);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/doctor/doctorRecordQuery";
	}

	@RequestMapping("/addPage")
	public String addPage(Model model) {
		List<DoctorsTeam> doctorsTeamList = doctorsTeamService.getList("selectAll", null);
		model.addAttribute("doctorsTeamList", doctorsTeamList);
		return "/doctor/doctorRecordAdd";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "新增医生")
	public Results<Map<String, Object>> add(HttpServletRequest request, DoctorsUser doctorsUser_, MultipartFile file,
			MultipartFile certificate) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();

		DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorsUser_.getPhone());

		if (doctorsUser != null) {
			results.setCode(MessageCode.CODE_405);
			results.setMessage(MessageCode.MESSAGE_405);
			return results;
		}

		try {
			if (!file.isEmpty() && !FileFilterUtils.isMediaFileType(file.getOriginalFilename())) {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("不支持该文件格式上传");
				return results;
			}

			if (!certificate.isEmpty() && !FileFilterUtils.isMediaFileType(certificate.getOriginalFilename())) {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("不支持该文件格式上传");
				return results;
			} else if (certificate.isEmpty()) {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("证件图片不能为空");
				return results;
			}

			String prefix = "doctors_" + doctorsUser_.getPhone() + "_"
					+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_";
			String fileUrl = "";
			if (!file.isEmpty()) {
				fileUrl = UploadFilesUtils.saveFile(null, null, request, file, prefix); // 保存图片
			}

			String certificateUrl = "";
			if (!certificate.isEmpty()) {
				certificateUrl = UploadFilesUtils.saveFile(null, null, request, certificate, prefix); // 保存图片
			}

			List<DoctorsTeam> list = doctorsTeamService.getList("selectByHospitalName", doctorsUser_.getHospital());

			Integer hospitalId;
			if (list == null || list.size() == 0) {
				DoctorsTeam doctorsTeam = new DoctorsTeam();
				doctorsTeam.setArea(doctorsUser_.getArea());
				doctorsTeam.setCityName(doctorsUser_.getCityName());
				doctorsTeam.setCreatetime(new Date());
				doctorsTeam.setName(doctorsUser_.getHospital());
				doctorsTeam.setProvince(doctorsUser_.getProvince());
				doctorsTeamService.insert(doctorsTeam);
				hospitalId = doctorsTeam.getId();
			} else {
				hospitalId = list.get(0).getId();
			}

			doctorsUser_.setLoginId(Constant.doctor + doctorsUser_.getPhone());
			doctorsUser_.setPassword(DES.encryptDESBeas64(Constant.password));
			doctorsUser_.setStatus(0);
			doctorsUser_.setHeadUrl(fileUrl);
			doctorsUser_.setCertificateUrl(certificateUrl);
			doctorsUser_.setCreatetime(new Date());
			doctorsUser_.setHospitalId(hospitalId);

			doctorsUserService.insert(doctorsUser_);

			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);

			return results;
		} catch (Exception e) {
			// TODO: handle exception
			results.setCode(MessageCode.CODE_404);
			results.setMessage(e.getMessage());
			return results;
		}
	}

	/**
	 * 编辑医生档案页
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(int id, Model model) {
		try {
			DoctorsUser doctorUser = doctorsUserService.findById("selectByPrimaryKey", id);
			model.addAttribute("doctorRecord", doctorUser);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/doctor/doctorRecordEdit";
	}

	// /**
	// * 编辑医生档案
	// *
	// * @param mediaCategory
	// * @return
	// */
	@RequestMapping("/edit")
	@ResponseBody
	public Results<Map<String, Object>> edit(DoctorsUser doctorsUser_, HttpServletRequest request, MultipartFile file,
			MultipartFile certificate) {
		Results<Map<String, Object>> result = new Results<Map<String, Object>>();
		try {
			DoctorsUser doctorsUser = doctorsUserService.findById("selectByPrimaryKey", doctorsUser_.getId());
			doctorsUser.setId(doctorsUser_.getId());
			doctorsUser.setName(doctorsUser_.getName());
			doctorsUser.setPassword(doctorsUser_.getPassword());
			doctorsUser.setPhone(doctorsUser_.getPhone());
			doctorsUser.setSex(doctorsUser_.getSex());
			doctorsUser.setTitle(doctorsUser_.getTitle());
			doctorsUser.setHospital(doctorsUser_.getHospital());

			if (!file.isEmpty() && !FileFilterUtils.isMediaFileType(file.getOriginalFilename())) {
				result.setCode(MessageCode.CODE_404);
				result.setMessage("不支持该文件格式上传");
				return result;
			}

			if (!certificate.isEmpty() && !FileFilterUtils.isMediaFileType(certificate.getOriginalFilename())) {
				result.setCode(MessageCode.CODE_404);
				result.setMessage("不支持该文件格式上传");
				return result;
			}

			String prefix = "doctors_" + doctorsUser_.getPhone() + "_"
					+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_";
			String fileUrl = "";
			if (!file.isEmpty()) {
				fileUrl = UploadFilesUtils.saveFile(null, null, request, file, prefix); // 保存图片
				doctorsUser.setHeadUrl(fileUrl);
			}

			String certificateUrl = "";
			if (!certificate.isEmpty()) {
				certificateUrl = UploadFilesUtils.saveFile(null, null, request, certificate, prefix); // 保存图片
				doctorsUser.setCertificateUrl(certificateUrl);
			}

			doctorsUser.setDepartment(doctorsUser_.getDepartment());
			doctorsUser.setDepartmentTelephone(doctorsUser_.getDepartmentTelephone());
			doctorsUser.setIntroduction(doctorsUser_.getIntroduction());
			doctorsUser.setProvince(doctorsUser_.getProvince());
			doctorsUser.setCityName(doctorsUser_.getCityName());
			doctorsUser.setArea(doctorsUser_.getArea());
			// doctorsUser.setStartWorkingTime(doctorsUser_.getStartWorkingTime());
			doctorsUser.setRecommended(doctorsUser_.getRecommended());
			// doctorsUser.setGraphicConsulting(doctorsUser_.getGraphicConsulting());
			doctorsUser.setUpdatetime(new Date(System.currentTimeMillis()));
			doctorsUser.setStartWorkingTime(doctorsUser_.getStartWorkingTime());
			doctorsUser.setIsVideoConsultation(doctorsUser_.getIsVideoConsultation());
			doctorsUser.setEasemobUuid(doctorsUser_.getEasemobUuid());
			doctorsUser.setSequence(doctorsUser_.getSequence());

			doctorsUserService.update("updateByPrimaryKeySelective", doctorsUser);
			System.out.println("---------------成功------");

			result.setCode(MessageCode.CODE_200);
			result.setMessage("成功");
			return result;
		} catch (RuntimeException e) {
			log.error("修改医生档案失败：{}", e);
			result.setCode(MessageCode.CODE_404);
			result.setMessage(e.getMessage());
			return result;
		}
	}

	@RequestMapping(value = "/downLoadFile")
	@ResponseBody
	public ResponseEntity<byte[]> download(HttpServletRequest request, Model model) throws Exception {
		String filename = "粤健康-医生表更新（模板）-20171129.xlsx";
		// 下载文件路径
		String path = request.getServletContext().getRealPath("/upload/");
		File file = new File(path + File.separator + filename);
		HttpHeaders headers = new HttpHeaders();
		// 下载显示的文件名，解决中文名称乱码问题
		String downloadFielName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
		// 通知浏览器以attachment（下载方式）打开图片
		headers.setContentDispositionFormData("attachment", downloadFielName);
		// application/octet-stream ： 二进制流数据（最常见的文件下载）。
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}

	
	
	@RequestMapping(value = "/batchImportPage", method = RequestMethod.GET)
	@SystemControllerLog(description = "跳转到批量导入医生页面")
	public String batchImportPage() {
		return "/doctor/doctorRecordBatchImport";
	}

	
	
	@RequestMapping(value = "/batchImport")
	@SystemControllerLog(description = "批量导入医生")
	@ResponseBody
	public Results<Map<String, Object>> batchImport(HttpServletRequest request, MultipartFile file) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		String fileName = file.getOriginalFilename();
		String postfix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		InputStream is = null;
		List<ExcelDoctorUserEntity3> excelDoctorUserEntityList = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (postfix != "") {
				is = file.getInputStream();
				if ("xls".equalsIgnoreCase(postfix)) {
					excelDoctorUserEntityList = readDoctorsUserXls(is);
				} else if ("xlsx".equalsIgnoreCase(postfix)) {
					excelDoctorUserEntityList = readDoctorsUserXlsx(is);
				}else {
		            results.setCode(MessageCode.CODE_404);
					results.setMessage("格式不符合");
				}
				
				if (excelDoctorUserEntityList != null) {
		    		int totalCount = excelDoctorUserEntityList.size(); //总数
		    		int successCount = 0; //成功数
		    		int failCount = 0; //失败数
		    		for(int i=0; i<excelDoctorUserEntityList.size(); i++) {//遍历记录集
		    			ExcelDoctorUserEntity3 excelDoctorUserEntity = excelDoctorUserEntityList.get(i);//获取当前医生实体
		    			String doctorsPhone = excelDoctorUserEntity.getPhone();
		    			String doctorsName = excelDoctorUserEntity.getName();
		    			String hospitalName = excelDoctorUserEntity.getHospital();
		    			//记录中电话，姓名，就职医院为空的不入库
		    			if (excelDoctorUserEntity != null && StringUtils.isNotBlank(doctorsPhone) 
		    					&& StringUtils.isNotBlank(doctorsName) && StringUtils.isNotBlank(hospitalName)) {
		    				
		    				// 维护团队表
        					Integer doctorsTeamId = null;
        					DoctorsTeam doctorsTeam_ = doctorsTeamService.findByParam("selectByHospitalName", hospitalName);
        					if (null == doctorsTeam_) {
        						DoctorsTeam doctorsTeam = new DoctorsTeam();
        						doctorsTeam.setName(hospitalName);
        						doctorsTeam.setProvince(excelDoctorUserEntity.getProvince());
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
		    				
		    				DoctorsUser doctorsUser_ = doctorsUserService.findByParam("selectByPhone", doctorsPhone);
		            		if (null == doctorsUser_) {//重复记录不入库
		            			/**
		            			 * 先创建环信用户
		            			 */
		            			EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
		            			RegisterUsers users = new RegisterUsers();
		            			User user = new User().username(Constant.doctor + doctorsPhone).password("mingyizaixian123456789");// 环信测试帐号
		        				users.add(user);
		        				Object result_ = easemobIMUsers.createNewIMUserSingle(users);
		        				if (result_ != null) {//环信用户创建成功
		        					// 解释result
		            				JSONObject resultObject = JSONObject.parseObject(result_.toString());
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
		        					doctorsUser.setHospitalId(doctorsTeamId);
		        					doctorsUser.setDepartment(excelDoctorUserEntity.getDepartment());
		        					doctorsUser.setStartWorkingTime(excelDoctorUserEntity.getStartWorkingTime());
		        					doctorsUser.setDepartmentTelephone(excelDoctorUserEntity.getDepartmentTelephone());
		        					doctorsUser.setIntroduction(excelDoctorUserEntity.getIntroduction());
		        					doctorsUser.setProvince(excelDoctorUserEntity.getProvince());
		        					doctorsUser.setCityName(excelDoctorUserEntity.getCityName());
		        					doctorsUser.setArea(excelDoctorUserEntity.getArea());
		        					doctorsUser.setStatus(1);
		        					doctorsUser.setDoctortype(1);
		        					doctorsUser.setEasemobUuid(uuid);// 环信返回来的uuid
		        					doctorsUser.setTestAccount(1);
		        					doctorsUser.setSequence(999);
		        					try {
										doctorsUser.setPassword(DES.encryptDESBeas64("mingyizaixian123456789"));
									} catch (CoderException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
		        					doctorsUser.setLoginId(Constant.doctor + doctorsPhone);
		        					doctorsUser.setCreatetime(new Date());
		        					doctorsUserService.insert(doctorsUser);
		       

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
				
			} else {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("失败");
				return results;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			results.setCode(MessageCode.CODE_404);
			results.setMessage("失败");
			return results;
		}finally {
			if (is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return results;
	}

	
	/**
	 * 读取Xls格式
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public List<ExcelDoctorUserEntity3> readDoctorsUserXls(InputStream is) {
		HSSFWorkbook hssfWorkbook = null;
		SimpleDateFormat df_1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		SimpleDateFormat df_2 = new SimpleDateFormat("yyyy-MM-dd");
	
		try {
			if (is != null)
				hssfWorkbook = new HSSFWorkbook(is);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ExcelDoctorUserEntity3 excelDoctorUserEntity = null;
		List<ExcelDoctorUserEntity3> list = new ArrayList<ExcelDoctorUserEntity3>();

		if (null == hssfWorkbook)
			return null;
		// Read the Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (null == hssfSheet) {
				continue;
			}
			// Read the Row
			for (int rowNum = 2; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (null == hssfRow) {
					break;
				} else {
					excelDoctorUserEntity = new ExcelDoctorUserEntity3();
					
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
					excelDoctorUserEntity.setProvince(getStringValue(hssfRow.getCell(10)));
					excelDoctorUserEntity.setCityName(getStringValue(hssfRow.getCell(11)));
					excelDoctorUserEntity.setArea(getStringValue(hssfRow.getCell(12)));
					excelDoctorUserEntity.setHospitalPhone(getStringValue(hssfRow.getCell(13)));
					excelDoctorUserEntity.setHospitalAddr(getStringValue(hssfRow.getCell(14)));

					if (list != null)
						list.add(excelDoctorUserEntity);
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 读取Xlsx格式
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public List<ExcelDoctorUserEntity3> readDoctorsUserXlsx(InputStream is) {
		XSSFWorkbook xssfWorkbook = null;
		SimpleDateFormat df_1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		SimpleDateFormat df_2 = new SimpleDateFormat("yyyy-MM-dd");

		try {
			if (is != null)
				xssfWorkbook = new XSSFWorkbook(is);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ExcelDoctorUserEntity3 excelDoctorUserEntity = null;
		List<ExcelDoctorUserEntity3> list = new ArrayList<ExcelDoctorUserEntity3>();

		if (null == xssfWorkbook)
			return null;
		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (null == xssfSheet) {
				continue;
			}
			// Read the Row
			for (int rowNum = 2; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (null == xssfRow) {
					break;
				} else {
					excelDoctorUserEntity = new ExcelDoctorUserEntity3();
					
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
					excelDoctorUserEntity.setProvince(getStringValue(xssfRow.getCell(10)));
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
