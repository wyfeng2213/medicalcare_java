package com.cmcc.medicalcare.service.Impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.VideoInterrogationLog;
import com.cmcc.medicalcare.service.IVideoInterrogationLogService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="videoInterrogationLogService")
@Transactional
public class VideoInterrogationLogServiceImpl extends GenericServiceImpl<VideoInterrogationLog, Integer> implements IVideoInterrogationLogService{

	@Override
	public VideoInterrogationLog saveVideoInterrogationLog(Integer ordersId, String fromUserLoginid,
			Integer fromUserType, String toUserLoginid, Integer videoTime) {
		VideoInterrogationLog videoInterrogationLog = new VideoInterrogationLog();
		videoInterrogationLog.setOrdersId(ordersId);
		videoInterrogationLog.setFromUserLoginid(fromUserLoginid);
		videoInterrogationLog.setFromUserPhone(fromUserLoginid.split("_")[1]);
		videoInterrogationLog.setFromUserType(fromUserType);
		videoInterrogationLog.setToUserLoginid(toUserLoginid);
		videoInterrogationLog.setToUserPhone(fromUserLoginid.split("_")[1]);
		videoInterrogationLog.setVideoTime(videoTime);
		videoInterrogationLog.setState(0);
		videoInterrogationLog.setCreatetime(new Date());
		videoInterrogationLog.setUpdatetime(new Date());
		insert(videoInterrogationLog);
		
		return videoInterrogationLog;
	}

}