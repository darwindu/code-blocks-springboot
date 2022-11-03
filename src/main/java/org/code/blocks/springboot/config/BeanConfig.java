package org.code.blocks.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * create bean by new Class()
 * @author darwindu
 */
@Configuration
@ImportResource(locations={"classpath:codeBolcksSpringbootApplicationContext.xml"})
public class BeanConfig {


}
