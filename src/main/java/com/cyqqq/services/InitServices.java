package com.cyqqq.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Description
 * 启动初始化
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/12/14 10:26
 * @Version :
 */
@Component
public class InitServices implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(InitServices.class);

    public static final Map<String,Object> maps = new HashMap<>();

    @Override
    public void run(String... args) {
        log.info("项目初始化");

    }
}
