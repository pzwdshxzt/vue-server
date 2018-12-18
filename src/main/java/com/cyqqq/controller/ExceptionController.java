package com.cyqqq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;

/**
 * Description
 *
 * 异常捕获
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/12/13 11:38
 * @Version :
 */
@ControllerAdvice(assignableTypes = {CoreController.class,UserController.class})
public class ExceptionController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 拦截异常
     *
     * @param e
     * @param m
     * @return
     */
    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handle(Exception e, HandlerMethod m) {
        log.info("CustomInterceptAdvice handle exception {}, method: {}", e.getMessage(), m.getMethod().getName());
        return e.getMessage();
    }
}
