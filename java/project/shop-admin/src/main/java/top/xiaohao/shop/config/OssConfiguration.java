package top.xiaohao.shop.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Configuration
@Component
@PropertySource(value = "/application-oss.yml", encoding = "UTF-8", factory = YamlPropertyLoaderFactory.class)
public class OssConfiguration {

    @Value("${oss.endpoint}")
    private String endPoint;

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${oss.fileHost}")
    private String fileHost;

    @Value("${oss.bucketName}")
    private String bucketName;

    @Value("${oss.bucket}")
    private String bucket;
}
