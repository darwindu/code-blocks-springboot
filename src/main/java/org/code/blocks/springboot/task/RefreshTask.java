package org.code.blocks.springboot.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author darwindu
 * @date 2019/5/8
 **/
@Component
@Slf4j
public class RefreshTask {

    /**
     * 每3秒执行一次
     */
    @Scheduled(cron = "${task.cron.test}")
    public void execute() {

        log.info("###task start: 每3秒一次");
        log.info("###task end");
    }
}
