package top.xiaohao.security.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Redis工具类
 */
@Component
public class RedisCache {

    @Autowired
    private RedisTemplate<?, ?> redisTemplate;


}
