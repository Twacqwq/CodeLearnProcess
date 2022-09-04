package top.xiaohao.shop.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import top.xiaohao.shop.domain.entitiy.LoginUser;
import top.xiaohao.shop.domain.enums.AppHttpCodeEnum;
import top.xiaohao.shop.utils.JWTUtil;
import top.xiaohao.shop.utils.Result;
import top.xiaohao.shop.utils.WebUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * token认证过滤器
 * @author Twac
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //不存在token直接放行
            filterChain.doFilter(request, response);
            return;
        }
        String userId;
        try {
            Claims claims = JWTUtil.parseToken(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            //token过期或非法
            Result<?> result = new Result<>(401, AppHttpCodeEnum.NEED_LOGIN.toString());
            WebUtil.renderString(response, "401");
//            WebUtil.renderResult(401, AppHttpCodeEnum.NEED_LOGIN.toString());
            return;
        }
        String redisKey = "login:" + userId;
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(redisKey);
        if (Objects.isNull(loginUser)) {
            Result<?> result = new Result<>(401, AppHttpCodeEnum.NEED_LOGIN.toString());
            WebUtil.renderString(response, String.valueOf(result));
        }
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken); //存入Security作用域中
        filterChain.doFilter(request, response);
    }
}
