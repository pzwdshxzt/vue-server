package com.cyqqq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author Huangjinxing
 */
@SpringBootApplication
@MapperScan("com.cyqqq.services.mapper")
public class ServerApplication{

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ServerApplication.class, args);
        SpringUtil.setApplicationContext(context);
    }

}
