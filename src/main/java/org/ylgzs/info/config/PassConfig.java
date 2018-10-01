package org.ylgzs.info.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName PassConfig
 * @Description TODO
 * @Author alex
 * @Date 2018/10/1
 **/
@Getter
@ConfigurationProperties(prefix = "pass")
public class PassConfig {

    public static String salt;
}
