package top.xiaohao.security.filter;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.xiaohao.security.entity.LoginUser;
import top.xiaohao.security.utils.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * token认证过滤器
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (Objects.isNull(token)) {
            filterChain.doFilter(request, response);
            return; //这里return是不让下面解析的过滤器和代码运行
        }
        Claims claims = JwtUtil.parseToken(token);
        String userId = claims.getSubject();
        log.info("当前用户ID:{}",userId);
        String redisKey = "login:" + userId;
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(redisKey);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("用户不存在qwq");
        }
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities()));
        filterChain.doFilter(request, response);
    }
}
