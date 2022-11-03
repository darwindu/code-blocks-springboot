package org.code.blocks.springboot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.code.blocks.springboot.service.ThreadPoolService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author darwindu
 * @date 2020/4/28
 **/
@Service
@Slf4j
public class ThreadPoolServiceImpl implements ThreadPoolService {

    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {

        log.info("start executeAsync");
        try{
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("end executeAsync");
    }

}
