package org.code.blocks.springboot.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.code.blocks.common.util.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author darwindu
 * @date 2022/10/19
 **/
@Configuration
@Slf4j
public class CrosConfig {

    @Value("${code.blocks.cros.config}")
    private String crosConfig;

    @Bean(name = "corsFilter")
    public CorsFilter corsFilter() {
        Map<String, List<String>> corsMap = this.handleCorsConfig(crosConfig);
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        for (String path : corsMap.keySet()) {
            List<String> urls = corsMap.get(path);
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowCredentials(Boolean.TRUE);
            //corsConfiguration.setAllowedOrigins(urls); // springboot升级推荐使用AllowedOriginPatterns，不要开启注解CrossOrigin.originPatterns，否则启动报错
            log.info("#########path: {}", JsonUtils.objToJson(path));
            log.info("#########corsMap.get: {}", JsonUtils.objToJson(urls));
            corsConfiguration.setAllowedOriginPatterns(urls);

            corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
            corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
            configSource.registerCorsConfiguration(path, corsConfiguration);
        }
        return new CorsFilter(configSource);
    }

    private Map<String, List<String>> handleCorsConfig(String corsConfig) {
        Map<String, List<String>> corsMap = new HashMap<>();
        String[] tempStrs = corsConfig.split(";");
        for (int i = 0; i < tempStrs.length; i++) {
            String tempStr = tempStrs[i];
            List<String> urls = Arrays.asList(tempStr.substring(0, tempStr.indexOf("@")).split(","));
            String path = tempStr.substring(tempStr.indexOf("@") + 1, tempStr.length());
            corsMap.put(path, urls);
        }
        return corsMap;
    }
}
