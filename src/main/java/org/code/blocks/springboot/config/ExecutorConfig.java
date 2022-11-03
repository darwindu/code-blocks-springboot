package org.code.blocks.springboot.config;

import java.util.concurrent.Executor;

import lombok.extern.slf4j.Slf4j;
import org.code.blocks.springboot.util.ThreadPoolUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author darwindu
 * @date 2020/4/28
 **/
@Configuration
@EnableAsync
@Slf4j
public class ExecutorConfig {

    @Value("${common.corePoolSize}")
    private int corePoolSize;
    @Value("${common.maxPoolSize}")
    private int maxPoolSize;
    @Value("${common.queueCapacity}")
    private int queueCapacity;
    @Value("${common.keepAliveSeconds}")
    private int keepAliveSeconds;

    @Bean
    public Executor asyncServiceExecutor() {

        log.info("start asyncServiceExecutor");
        return ThreadPoolUtils.getThreadPool("async-service-", corePoolSize, maxPoolSize, queueCapacity, keepAliveSeconds);
    }
}

