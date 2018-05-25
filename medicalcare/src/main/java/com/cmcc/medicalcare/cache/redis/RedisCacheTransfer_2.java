package com.cmcc.medicalcare.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhouzhou
 * @version 2017年12月20日 下午3:01:44 TODO
 */
@Component
public class RedisCacheTransfer_2 {

	@Autowired
	public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory_2) {
		RedisCache_2.setJedisConnectionFactory(jedisConnectionFactory_2);
	}

}