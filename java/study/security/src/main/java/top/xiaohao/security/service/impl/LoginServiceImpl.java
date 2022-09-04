package top.xiaohao.security.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import top.xiaohao.security.entity.LoginUser;
import top.xiaohao.security.entity.ResponseResult;
import top.xiaohao.security.entity.User;
import top.xiaohao.security.service.LoginService;
import top.xiaohao.security.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public ResponseResult<?> login(User user) {
        //封装Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        //获取AuthenticationManager的authenticate方法进行用户认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //如果认证没有通过 抛出异常提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败qwq");
        }
        //否则使用userId生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtil.createToken(userId);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        //存入redis
        redisTemplate.opsForValue().set("login:" + userId, loginUser);
        return new ResponseResult<>(200, "登录成功", map);
    }

    @Override
    public ResponseResult<?> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        redisTemplate.delete("login:" + userId);
        return new ResponseResult<>(200, "注销成功");
    }
}
