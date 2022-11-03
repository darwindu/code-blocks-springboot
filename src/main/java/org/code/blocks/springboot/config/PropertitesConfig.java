package org.code.blocks.springboot.config;

import lombok.Data;
import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author darwindu
 * @date 2019/5/7
 **/
@Component
@Data
public class PropertitesConfig {

    @Value("${http.request-time-out}")
    private int httpRequestTimeOut;

    @Value("${http.max-active}")
    private int httpMaxActive;

    @Value("${http.validate-after-inactivity}")
    private int httpValidateAfterInactivity;

    @Value("${http.proxy-host}")
    private String httpProxyHost;

    @Value("${http.proxy-port}")
    private int httpProxyPort;

}
