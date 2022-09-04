package top.xiaohao.shop.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import top.xiaohao.shop.domain.entitiy.LoginUser;
import top.xiaohao.shop.domain.entitiy.User;
import top.xiaohao.shop.service.LoginService;
import top.xiaohao.shop.utils.JWTUtil;
import top.xiaohao.shop.utils.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Result<?> login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        log.info("认证对象{}", authenticate);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或者密码错误");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getUserId();
        String token = JWTUtil.createJWT(userId);
        redisTemplate.opsForValue().set("login:" + userId, loginUser);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return new Result<>(200, "登录成功", map);
    }

    @Override
    public Result<?> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getUserId();
        redisTemplate.delete("login:" + userId);
        return new Result<>(200, "退出成功");
    }
}
