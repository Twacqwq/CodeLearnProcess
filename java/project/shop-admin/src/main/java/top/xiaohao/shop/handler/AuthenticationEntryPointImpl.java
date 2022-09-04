package top.xiaohao.shop.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.xiaohao.shop.domain.enums.AppHttpCodeEnum;
import top.xiaohao.shop.utils.Result;
import top.xiaohao.shop.utils.WebUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证处理器
 * @author Twac
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result<?> result;
        if (authException instanceof BadCredentialsException || authException instanceof InternalAuthenticationServiceException) {
            result = new Result<>().error(AppHttpCodeEnum.LOGIN_ERROR.getCode(), AppHttpCodeEnum.LOGIN_ERROR.getMsg());
            WebUtil.renderString(response, JSON.toJSONString(result));
        } else if (authException instanceof InsufficientAuthenticationException) {
            result = new Result<>().error(AppHttpCodeEnum.NEED_LOGIN.getCode(), AppHttpCodeEnum.NEED_LOGIN.getMsg());
            WebUtil.renderString(response, JSON.toJSONString(result));
        } else {
            authException.printStackTrace();
        }
    }
}
