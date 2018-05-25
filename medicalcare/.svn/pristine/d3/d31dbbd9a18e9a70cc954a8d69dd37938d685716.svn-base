package com.cmcc.medicalcare.cache.redis;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cmcc.medicalcare.model.SystemUser;
import com.cmcc.medicalcare.service.ISystemUserService;

/**
 * @author zhouzhou
 * @version 2017年12月20日 下午3:56:15 TODO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext*.xml" })
public class MyBatisCacheSecondTest {

	private static final Logger logger = LoggerFactory.getLogger(MyBatisCacheSecondTest.class);

	@Autowired
	private ISystemUserService systemUserService;

//	@Autowired
//	private JedisConnectionFactory jedisConnectionFactory;

	@Autowired
	public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
		RedisCache.setJedisConnectionFactory(jedisConnectionFactory);
	}
	
	/*
	 * 二级缓存测试
	 */
	// @Test
	public void testCache2() {

//		RedisCache.setJedisConnectionFactory(jedisConnectionFactory);

		SystemUser systemUser = new SystemUser();
		systemUser.setUserName("luoyizhou33333333333");
		systemUser.setUserPassword("123456");
		systemUser.setUserRole(1);
		systemUser.setCreateTime(new Date());
		systemUser.setIsEnable(1);
		systemUserService.insert(systemUser);
		System.out.println("systemUser.getId():" + systemUser.getId());
		// testCache3(systemUser.getId());
	}

//	 @Test
	public void testCacheUpdate() {

//		RedisCache.setJedisConnectionFactory(jedisConnectionFactory);

		SystemUser systemUser = systemUserService.findById("selectByPrimaryKey",67);
		
		systemUser.setUserName("罗亦洲");
		systemUser.setUserPassword("周周周罗亦");
		systemUser.setUserRole(1);
		systemUser.setCreateTime(new Date());
		systemUser.setIsEnable(1);
		systemUserService.update("updateByPrimaryKeySelective", systemUser);
		System.out.println("systemUser.getId():" + systemUser.getId());
		// testCache3(systemUser.getId());
//		testCacheSelect();
	}

	 @Test
	public void testCacheSelect() {

//		RedisCache.setJedisConnectionFactory(jedisConnectionFactory);
		List<SystemUser> systemUser = systemUserService.getList("selectByUserName","123");
		
		systemUser = systemUserService.getList("selectByUserName","罗亦洲");

		// SystemUser systemUser =
		// systemUserService.findById("selectByPrimaryKey",67);
		System.out.println("systemUser:" + systemUser);
		System.out.println("systemUser.get(0).getUserPassword():" + systemUser.get(0).getUserPassword());
	}

}
