package com.cmcc.medicalcare.controller.sys;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.HealthyProducts;
import com.cmcc.medicalcare.model.MessageReminding;
import com.cmcc.medicalcare.service.IHealthyProductsService;
import com.cmcc.medicalcare.service.IMessageRemindingService;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.easemob.server.api.impl.EasemobSendMessage;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

/** 
 * 
 * @author zds
 *
 */
@Controller
@RequestMapping("/sys/systemMessageReminding")
public class SystemMessageRemindingController {
	
	@Resource
	private IHealthyProductsService healthyProductsService;
	
	@Resource
	private IMessageRemindingService messageRemindingService;
	
	/**
	 * 跳转管理页面
	 * @return
	 */
	@SystemControllerLog(description = "跳转管理页面")
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager() {
    	 return "/sys/messageReminding/systemMessageReminding";
    }

	
	
	/**
     * 用户管理页
     *
     * @return
     */
	@SystemControllerLog(description = "跳转新增页面")
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage() {
        return "/sys/messageReminding/systemMessageRemindingAdd";
    }
	
	
	/**
	 * "发送消息
	 * @param healthyProducts
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "发送消息")
	public Results<String> add(HttpServletRequest request,MessageReminding messageReminding,MultipartFile file){
		Results<String> results = new Results<String>();
		try {
			if (!file.isEmpty() && !FileFilterUtils.isImageFileType(file.getOriginalFilename())) {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("不支持该文件格式上传");
				return results;
			}
			String prefix =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ "_";
			if (!file.isEmpty()) {
				String fileUrl = UploadFilesUtils.saveFile(null,null,request, file, prefix); // 保存图片
				messageReminding.setMessageIcon(fileUrl);;
			}
			messageReminding.setPatientLoginId(Constant.patient+messageReminding.getPatientPhone());
			messageReminding.setCreatetime(new Date());
			messageReminding.setMessageStatus(0);
			messageReminding.setMessageType(3);
			messageRemindingService.insert(messageReminding);
			
			EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent();
			
			Map<String, Object> ext = new HashMap<String, Object>();
			ext.put("messageType", "messageReminding");
			
			// 获取未读消息数
			List<MessageReminding> messageRemindingList = messageRemindingService.getList("getUnreadMessage", messageReminding.getPatientPhone());
			
			ext.put("UnreadMessageSize", messageRemindingList.size());
			msgContent.type(MsgContent.TypeEnum.CMD).msg(null);
			UserName userName = new UserName();
			userName.add(Constant.patient+messageReminding.getPatientPhone());
			msg.from(Constant.SYSTEM).target(userName).targetType("users").msg(msgContent).ext(ext);
			easemobSendMessage.sendMessage(msg);
			
			results.setCode(MessageCode.CODE_200);
			results.setMessage("添加成功");
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			results.setMessage(e.getMessage());
			results.setCode(MessageCode.CODE_501);
			return results;
		}
	}
	
	/**
	 * 获取healthyProductslist
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "获取消息通知list")
	public Map<String, Object> findPage(HttpServletRequest request, MessageReminding messageReminding,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows){
		
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pager<MessageReminding> pagers = messageRemindingService.getList("findPage", page, rows, messageReminding);
			map.put("total", pagers.getTotalCount());
			map.put("rows", pagers.getList());
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 删除
	 * @param healthyProducts
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "删除消息通知")
	public Results<String> delete(Integer id){
		Results<String> results = new Results<String>();
		try {
			MessageReminding messageReminding = new MessageReminding();
			messageReminding.setId(id);
			messageRemindingService.delete("deleteByPrimaryKey", messageReminding);
			results.setCode(MessageCode.CODE_200);
			results.setMessage("删除成功");
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			results.setMessage(e.getMessage());
			results.setCode(MessageCode.CODE_501);
			return results;
		}
	}
	
	
}
