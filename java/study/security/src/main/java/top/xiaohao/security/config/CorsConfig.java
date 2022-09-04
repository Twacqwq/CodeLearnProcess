package top.xiaohao.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许跨域的接口路径
        registry.addMapping("/**")
                //是否开启Cookie
                .allowCredentials(true)
                //允许跨域的域名
                .allowedOriginPatterns("*")
                //允许跨域的请求头
                .allowedHeaders("*")
                //允许的方法
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                //跨域允许时间
                .maxAge(3600);
    }
}
