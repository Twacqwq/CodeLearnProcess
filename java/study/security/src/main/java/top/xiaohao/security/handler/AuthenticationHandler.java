package top.xiaohao.security.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.xiaohao.security.entity.ResponseResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证异常处理器
 */
@Component
public class AuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ResponseResult<?> responseResult = new ResponseResult<>(HttpStatus.UNAUTHORIZED.value(), "尚未登录!!", null);
        String json = JSON.toJSONString(responseResult);
        System.out.println(responseResult);
        System.out.println(json);
        response.getWriter().print(json);
    }
}
