package top.xiaohao.security.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import top.xiaohao.security.entity.User;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    @Test
    public void getKey() {
        System.out.println(redisTemplate.opsForValue().get("user3"));
    }
}
