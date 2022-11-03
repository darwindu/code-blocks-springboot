package org.code.blocks.springboot.controller;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.code.blocks.common.protocol.response.ResponseData;
import org.code.blocks.springboot.protocol.request.CommitFlowFqRequest;
import org.code.blocks.springboot.protocol.request.FlowIdsRequest;
import org.code.blocks.springboot.protocol.validate.Third;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author darwindu
 * @date 2020/11/20
 **/
@Controller
@Slf4j
@RequestMapping(value = "hello")
//@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*") // 跨域注解使用方式
public class HelloWorldController {


    @RequestMapping("yes")
    @ResponseBody
    public ResponseData<Boolean> yes() {
        return ResponseData.success(true);
    }

    @RequestMapping("hi")
    @ResponseBody
    public ResponseData<Boolean> hi(@RequestBody @Valid FlowIdsRequest vo) {
        return ResponseData.success(true);
    }

    @RequestMapping("say")
    @ResponseBody
    public ResponseData<Boolean> say(@RequestBody @Validated({Third.class}) CommitFlowFqRequest vo, @RequestHeader("userId") String userId) {
        return ResponseData.success(true);
    }
}
