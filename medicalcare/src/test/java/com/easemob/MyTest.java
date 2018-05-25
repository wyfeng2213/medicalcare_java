/**   
* @Title: MyTest.java 
* @Package com.easemob 
* @Description: TODO
* @author adminstrator   
* @date 2017年5月4日 上午9:54:02 
* @version V1.0   
*/
package com.easemob;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.model.PatientReferral;
import com.easemob.server.api.impl.EasemobChatGroup;
import com.easemob.server.api.impl.EasemobIMUsers;
import com.easemob.server.api.impl.EasemobSendMessage;
import com.easemob.server.comm.HttpToEasemobUtil;

import io.swagger.client.model.ModifyGroup;
import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

/**
 * @ClassName: MyTest
 * @Description: TODO
 * @author adminstrator
 * @date 2017年5月4日 上午9:54:02
 * 
 */
public class MyTest {

	private EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
//	private EasemobChatRoom easemobChatRoom = new EasemobChatRoom();
//	private EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
//
//	@Test
//	public void createGroup() {
//		Group group = new Group();
//		UserName userName = new UserName();
//		userName.add("doctor_14715001547");
//		userName.add("scretary_12345678912");
//		userName.add("doctor_15876587133");// groupid: 16983832723457
//		group.groupname("测试").desc("吴思铭")._public(true).maxusers(3).approval(false).owner("doctor_15876587133")
//				.members(userName);
//		Object result = easemobChatGroup.createChatGroup(group);
//		System.out.println(result.toString());
//	}
//
//	/**
//	 * { "action" : "delete", "application" :
//	 * "33313130-263a-11e7-b145-5962e0bcac56", "uri" :
//	 * "http://a1.easemob.com/1140170421178546/mingyizaixian/chatgroups/16983832723457/users/doctor_14715001547%2Cscretary_12345678912",
//	 * "entities" : [ ], "data" : [ { "result" : true, "action" :
//	 * "remove_member", "user" : "scretary_12345678912", "groupid" :
//	 * "16983832723457" }, { "result" : true, "action" : "remove_member", "user"
//	 * : "doctor_14715001547", "groupid" : "16983832723457" } ], "timestamp" :
//	 * 1495596175072, "duration" : 0, "organization" : "1140170421178546",
//	 * "applicationName" : "mingyizaixian" }
//	 * 
//	 */
//	@Test
//	public void removeUsersFromGroup() {
//		String groupId = "16983832723457";
//		String[] userIds = new String[2];
//		userIds[0] = "doctor_14715001547";
//		userIds[1] = "scretary_12345678912";
//		Object result = easemobChatGroup.removeBatchUsersFromChatGroup(groupId, userIds);
//		System.out.println(result.toString());
//	}
//
//	/**
//	 * { "action" : "get", "application" :
//	 * "33313130-263a-11e7-b145-5962e0bcac56", "uri" :
//	 * "http://a1.easemob.com/1140170421178546/mingyizaixian/chatgroups/16983832723457/users",
//	 * "entities" : [ ], "data" : [ { "member" : "scretary_12345678912" }, {
//	 * "owner" : "doctor_15876587133" }, { "member" : "doctor_14715001547" } ],
//	 * "timestamp" : 1495596005614, "duration" : 0, "organization" :
//	 * "1140170421178546", "applicationName" : "mingyizaixian", "count" : 3 }
//	 */
//	@Test
//	public void getUsersFromGroup() {
//		String groupId = "16983832723457";
//		Object result = easemobChatGroup.getChatGroupUsers(groupId);
//		System.out.println(result.toString());
//	}
//
//	@Test
//	public void group() {
//		Msg msg = new Msg();
//		MsgContent msgContent = new MsgContent();
//		String msgStr2 = "Jaffa" + "医生，您好。经过对患者" + "要晚点"
//				+ "病情的初步诊断，发现了一些问题需要您亲自问诊解答，详情请查看：\n\n  <a>转移医生处理意见单</a>    \n\n 问诊结束后，点击右上角\"填写问诊记录\"，填写完成后即结束本次问诊。"
//				+ "若1天内未填写问诊记录，将自动结束本次问诊";
//		msgContent.type(MsgContent.TypeEnum.TXT).msg(msgStr2);
//		UserName userName = new UserName();
//		String groupId = "16454232637443";
//		userName.add(groupId);
//		Map<String, Object> ext = new HashMap<String, Object>();
//		ext.put("test_key", "hello world");
//		msg.from("scretary_13435891254").target(userName).targetType("chatgroups").msg(msgContent).ext(ext);
//		Object result = easemobSendMessage.sendMessage(msg);
//		System.out.println(result);
//	}
//
//	/**
//	 * { "action" : "post", "application" :
//	 * "33313130-263a-11e7-b145-5962e0bcac56", "uri" :
//	 * "http://a1.easemob.com/1140170421178546/mingyizaixian/chatgroups/16983832723457/users",
//	 * "entities" : [ ], "data" : { "newmembers" : [ "doctor_14715001547" ],
//	 * "groupid" : "16983832723457", "action" : "add_member" }, "timestamp" :
//	 * 1495596279149, "duration" : 0, "organization" : "1140170421178546",
//	 * "applicationName" : "mingyizaixian" }
//	 */
//	@Test
//	public void addUsers() {
//		String groupId = "16983832723457";
//		UserNames userNames = new UserNames();
//		UserName userList = new UserName();
//		userList.add("doctor_14715001547");
//		userNames.usernames(userList);
//		Object result = easemobChatGroup.addBatchUsersToChatGroup(groupId, userNames);
//		System.out.println(result.toString());
//	}
//
//	@Test
//	public void chatroom() {
//		Msg msg = new Msg();
//		MsgContent msgContent = new MsgContent();
//		msgContent.type(MsgContent.TypeEnum.TXT).msg("hello world11111111");
//		UserName userName = new UserName();
//		String chatroomId_ = "15173249204225";
//		userName.add(chatroomId_);
//		msg.from("scretary_12345678912").target(userName).targetType("chatrooms").msg(msgContent);
//		Object result = easemobSendMessage.sendMessage(msg);
//		System.out.println(result);
//	}
//
//	@Test
//	public void createChatRoom() {
//		Chatroom chatroom = new Chatroom();
//		UserName userName = new UserName();
//		userName.add("patient_15876587110");
//		userName.add("qwer");
//		userName.add("scretary_12345678912");
//
//		chatroom.name("测试聊天室").description("test chatroom").maxusers(200).owner("scretary_12345678912")
//				.members(userName);
//		Object result = easemobChatRoom.createChatRoom(chatroom);
//		System.out.println(result.toString());
//	}
//
//	@Test
//	public void deleteChatGroup() {
//		String groupId = "16983832723457";
//		Object result = easemobChatGroup.deleteChatGroup(groupId);
//		System.out.println(result.toString());
//	}
//
//	@Test
//	public void single() {
//		Msg msg = new Msg();
//		MsgContent msgContent = new MsgContent();
//		msgContent.type(MsgContent.TypeEnum.TXT).msg("hello world1234");
//		UserName userName = new UserName();
//		userName.add("doctor_15899998888");
//		Map<String, Object> ext = new HashMap<String, Object>();
//		ext.put("test_key", "hello world");
//		msg.from("scretary_12345678912").target(userName).targetType("users").msg(msgContent).ext(ext);
//		Object result = easemobSendMessage.sendMessage(msg);
//		// String msgContentStr = "hee0000";
//		// String secretaryLoginId = "scretary_12345678912";
//		// String doctorsLoginId = "doctor_15899998888";
//		// Map<String, Object> ext = new HashMap<String, Object>();
//		//// EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
//		// Msg msg = new Msg();
//		// MsgContent msgContent = new MsgContent();
//		//
//		// msgContent.type(MsgContent.TypeEnum.TXT).msg(msgContentStr);
//		// UserName userName = new UserName();
//		// userName.add(secretaryLoginId);
//		// msg.from(doctorsLoginId).target(userName).targetType("users").msg(msgContent).ext(ext);
//		// Object result = easemobSendMessage.sendMessage(msg);
//		System.out.println(result);
//	}
//
//	@Test
//	public void test() {
//		EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
//		Msg msg = new Msg();
//		MsgContent msgContent = new MsgContent();
//		msgContent.type(MsgContent.TypeEnum.CMD).msg("您有新的消息");
//		Map<String, Object> ext2 = new HashMap<String, Object>();
//		PatientReferral patientReferral =new PatientReferral();
//		patientReferral.setReferraltime(new Date());
//		ext2.put("messageType", "doctorReferral");
//		ext2.put("fromDoctor_headUrl", "http://120.197.89.249:9001/medicalmedia/download/doctors_15876587138_20170526162916_img1.jpg");
//		ext2.put("fromDoctorName", "Testdoct");
//		ext2.put("toDoctorName", "测试啊");
//		ext2.put("toDoctor_headurl", "http://120.197.89.249:9001/medicalmedia/download/doctors_15876587131_20170531111707_temp_photo.jpg");
//		ext2.put("toDoctor_title", "主任");
//		ext2.put("time", patientReferral.getReferraltime());
//
//		UserName userName = new UserName();
//		userName.add("doctor_15876587138");
//		msg.from("doctor_15876587131").target(userName).targetType("users").msg(msgContent);
//		Object result = easemobSendMessage.sendMessage(msg);
//		System.out.println(result);
//	}
	
//	@Test
//	public void deleteFriendSingle() {
////		EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
////		Object result_ =easemobIMUsers.deleteFriendSingle("patient_15876587110","doctor_15876587137");
////		System.out.println(result_.toString());
//		String ret = HttpToEasemobUtil.deleteFriendSingle("patient_15876587110","doctor_15876587137");
//		System.out.println(ret);
//		JSONObject jsonObject = JSONObject.parseObject(ret);
//		JSONArray jsonArray = jsonObject.getJSONArray("entities");
//		JSONObject entity = jsonArray.getJSONObject(0);
//		Boolean activated = entity.getBoolean("activated");
//		System.out.println("activated: "+activated);
//	}
	
	@Test
	public void changeGroupInfo() {
		ModifyGroup group = new ModifyGroup();
		String groupId = "21960863055873";
		group.groupname("边城浪子aaa");
		Object result = easemobChatGroup.modifyChatGroup(groupId, group);
		System.out.println("result================================: "+result);
	}
	
}




