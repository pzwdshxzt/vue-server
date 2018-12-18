package com.cyqqq.services.config;

import com.alibaba.fastjson.JSONObject;
import com.cyqqq.annotation.ResponseEncode;
import com.cyqqq.constant.Constants;
import com.cyqqq.model.enums.SecurityMethod;
import com.cyqqq.util.AesEncryptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Description
 * 加密
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/12/4 17:11
 * @Version :
 */
@Component
@ControllerAdvice(basePackages = "com.cyqqq.controller")
public class EncodeResponseBodyAdvice implements ResponseBodyAdvice {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return methodParameter.getMethodAnnotation(ResponseEncode.class) != null;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ResponseEncode responseEncode = methodParameter.getMethodAnnotation(ResponseEncode.class);

        JSONObject jsonObject = new JSONObject();
        if (responseEncode.method() == SecurityMethod.NULL || responseEncode.method() == SecurityMethod.AES) {
            try {
                log.info("返回参数: {}", o.toString());
                String encode = AesEncryptUtils.encrypt(o.toString());
                jsonObject.put(Constants.DATA, encode);
                log.info("返回加密参数: {}", encode);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObject;
        }
        return o;
    }
}
