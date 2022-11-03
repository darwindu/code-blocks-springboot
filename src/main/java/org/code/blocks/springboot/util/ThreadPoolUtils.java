package org.code.blocks.springboot.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author darwindu
 * @date 2020/4/28
 **/
public class ThreadPoolUtils {


    public static ThreadPoolTaskExecutor getThreadPool(String threadName, int corePoolSize, int maxPoolSize, int queueCapacity, int keepAliveSeconds) {

        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setThreadNamePrefix(threadName);
        threadPool.setCorePoolSize(corePoolSize);
        threadPool.setMaxPoolSize(maxPoolSize);
        threadPool.setQueueCapacity(queueCapacity);
        threadPool.setKeepAliveSeconds(keepAliveSeconds);
        threadPool.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.AbortPolicy());
        threadPool.initialize();
        return threadPool;
    }
}
