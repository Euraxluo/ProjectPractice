package cn.euraxluo.passbook.passbook.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.service
 * RedisTemplateTest
 * 2020/2/25 17:27
 * author:Euraxluo
 * TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testRedisTemplate(){
        //redis flush all
        redisTemplate.execute((RedisCallback<Object>)connect ->{
            connect.flushAll();
            return null;
        });
        assert redisTemplate.opsForValue().get("name") == null;
        redisTemplate.opsForValue().set("name","passbook");
        assert redisTemplate.opsForValue().get("name") != null;
        System.err.println(redisTemplate.opsForValue().get("name"));
    }
}
