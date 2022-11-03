package org.code.blocks.springboot.config;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author darwindu
 * @date 2019/5/7
 **/
@Configuration
@EnableScheduling
@EnableAsync
public class SchedulingConfiguration {

    @Value("${task.refresh.corePoolSize}")
    private int corePoolSize;
    @Value("${task.refresh.maxPoolSize}")
    private int maxPoolSize;
    @Value("${task.refresh.queueCapacity}")
    private int queueCapacity;



    @Bean
    public Executor schedulingTaskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.initialize();
        return executor;
    }

}
