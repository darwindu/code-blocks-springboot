package org.code.blocks.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author darwindu
 * @date 2019/5/8
 **/
@Configuration
public class InitConfig {

    public static PropertitesConfig propertitesConfig;

    @Autowired
    public void setPropertitesConfig(PropertitesConfig propertitesConfig) {

        // 获取配置文件，这里也可以通过实现ApplicationContextAware接口，获取applicationContext读取propertitesConfig的bean来读取字段。
        // 例如：HttpClientUtils.java类
        // PropertitesConfig propertitesConfig = (PropertitesConfig)SpringUtils.getBean("propertitesConfig");
        this.propertitesConfig = propertitesConfig;
    }

}
