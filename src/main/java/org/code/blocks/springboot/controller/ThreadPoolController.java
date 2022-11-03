package org.code.blocks.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.code.blocks.springboot.service.ThreadPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author darwindu
 * @date 2020/4/28
 **/
@RestController
@Slf4j
public class ThreadPoolController {

    @Autowired
    private ThreadPoolService threadPoolService;

    @RequestMapping("/executor")
    public String submit(){
        log.info("### start submit");
        threadPoolService.executeAsync();
        log.info("### end submit");
        return "success";
    }

}
