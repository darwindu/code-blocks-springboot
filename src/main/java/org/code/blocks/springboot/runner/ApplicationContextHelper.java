package org.code.blocks.springboot.runner;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author darwindu
 * @date 2020/7/9
 **/
@Component
@Slf4j
public class ApplicationContextHelper /*implements ApplicationContextAware*/ {

    /**
     * 检查数据启动参数
     * @param applicationContext
     * @throws BeansException
     */
    /*@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        try {
            DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");
            dataSource.getConnection().close();
        } catch (Exception e) {
            log.error("### spring running, jdbc exception:", e);
            // 当检测数据库连接失败时, 停止项目启动
            System.exit(-1);
        }
    }*/
}
