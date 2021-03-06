/**   
* @Title: MyTest.java 
* @Package com.easemob 
* @Description: TODO
* @author adminstrator   
* @date 2017年5月4日 上午9:54:02 
* @version V1.0   
*/
package com.easemob;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmcc.medicalcare.config.Constant;
import com.easemob.server.api.impl.EasemobChatGroup;
import com.easemob.server.api.impl.EasemobChatRoom;
import com.easemob.server.api.impl.EasemobIMUsers;
import com.easemob.server.api.impl.EasemobSendMessage;

import io.swagger.client.model.Chatroom;
import io.swagger.client.model.Group;
import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.Nickname;
import io.swagger.client.model.UserName;
import io.swagger.client.model.UserNames;

/**
 * @ClassName: MyTest
 * @Description: TODO
 * @author adminstrator
 * @date 2017年5月4日 上午9:54:02
 * 
 */
public class MyTest {

	private EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
	private EasemobChatRoom easemobChatRoom = new EasemobChatRoom();
	private EasemobSendMessage easemobSendMessage = new EasemobSendMessage();

	@Test
	public void createGroup() {
		Group group = new Group();
		UserName userName = new UserName();
		userName.add("doctor_14715001547");
		userName.add("scretary_12345678912");
		group.groupname("测试组").desc("123")._public(true).maxusers(3).approval(false).owner("scretary_12345678912").members(userName);
		Object result = easemobChatGroup.createChatGroup(group);
		System.out.println(result.toString());
	}

	@Test
	public void group() {
		Msg msg = new Msg();
		MsgContent msgContent = new MsgContent();
		msgContent.type(MsgContent.TypeEnum.TXT).msg("1113333111");
		UserName userName = new UserName();
		String groupId = "15546641874945";
		userName.add(groupId);
		Map<String,Object> ext = new HashMap<String,Object>();
        ext.put("test_key","hello world");
		msg.from("patient_15876587133").target(userName).targetType("chatgroups").msg(msgContent).ext(ext);
		Object result = easemobSendMessage.sendMessage(msg);
		System.out.println(result);
	}
	
	@Test
    public void addUsers() {
        String groupId = "15190939729921";
        UserNames userNames = new UserNames();
        UserName userList = new UserName();
        userList.add("qwer");
        userNames.usernames(userList);
        Object result = easemobChatGroup.addBatchUsersToChatGroup(groupId, userNames);
        System.out.println(result.toString());
    }

	@Test
	public void chatroom() {
		Msg msg = new Msg();
		MsgContent msgContent = new MsgContent();
		msgContent.type(MsgContent.TypeEnum.TXT).msg("hello world11111111");
		UserName userName = new UserName();
		String chatroomId_ = "15173249204225";
		userName.add(chatroomId_);
		msg.from("scretary_12345678912").target(userName).targetType("chatrooms").msg(msgContent);
		Object result = easemobSendMessage.sendMessage(msg);
		System.out.println(result);
	}
	
	@Test
    public void createChatRoom() {
        Chatroom chatroom = new Chatroom();
        UserName userName = new UserName();
        userName.add("patient_15876587110");
        userName.add("qwer");
        userName.add("scretary_12345678912");

        chatroom.name("测试聊天室").description("test chatroom").maxusers(200).owner("scretary_12345678912").members(userName);
        Object result = easemobChatRoom.createChatRoom(chatroom);
        System.out.println(result.toString());
    }

	@Test
	public void single() {
		Msg msg = new Msg();
		MsgContent msgContent = new MsgContent();
		msgContent.type(MsgContent.TypeEnum.TXT).msg("hello world");
		UserName userName = new UserName();
		userName.add("doctor_14715001547");
		Map<String,Object> ext = new HashMap<String,Object>();
        ext.put("test_key","hello world");
		msg.from("scretary_12345678912").target(userName).targetType("users").msg(msgContent).ext(ext);
		Object result = easemobSendMessage.sendMessage(msg);
		System.out.println(result);
	}
	// 测试透传消息
	@Test
	public void test(){
		EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
		Msg msg = new Msg();
		MsgContent msgContent = new MsgContent();
		msgContent.type(MsgContent.TypeEnum.CMD).msg("您有新的意见单");
		UserName userName = new UserName();
		userName.add("15279278063618");
		msg.from("scretary_12345678912").target(userName).targetType("chatgroups").msg(msgContent);
		Object result = easemobSendMessage.sendMessage(msg);
		System.out.println(result);
	}
}
