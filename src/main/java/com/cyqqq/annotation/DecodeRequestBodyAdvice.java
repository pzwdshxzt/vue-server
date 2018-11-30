package com.cyqqq.annotation;

import com.alibaba.druid.util.StringUtils;
import com.cyqqq.model.enums.SecurityMethod;
import com.cyqqq.util.AesEncryptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.websocket.DecodeException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

/**
 * Description
 * 请求解密
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/11/30 11:39
 * @Version :
 */
@Component
@RestControllerAdvice(basePackages = "com.cyqqq.controller")
public class DecodeRequestBodyAdvice implements RequestBodyAdvice {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.getMethodAnnotation(RequestDecode.class) != null
                && methodParameter.getParameterAnnotation(RequestBody.class) != null;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage request, MethodParameter parameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        /** controller方法不要求加解密 */
        RequestDecode requestDecode = parameter.getMethodAnnotation(RequestDecode.class);
        if (requestDecode == null) {
            return request;
        }
        String encodeMethod = request.getHeaders().get("encode").get(0);
        if (StringUtils.isEmpty(encodeMethod)) {
            return request;
        }
        SecurityMethod encodeMethodEnum = SecurityMethod.getByCode(encodeMethod);
        switch (encodeMethodEnum){
            case NULL:
                break;
            case AES:
                InputStream is = request.getBody();
                String req = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining(System.lineSeparator()));
                try {
                    String reqstr = AesEncryptUtils.decrypt(req);
                    log.info("解密完成: {}", reqstr);
                    return new HttpInputMessage() {
                        @Override
                        public InputStream getBody() throws IOException {
                            return new ByteArrayInputStream(reqstr.getBytes("UTF-8"));
                        }

                        @Override
                        public HttpHeaders getHeaders() {
                            return request.getHeaders();
                        }
                    };

                } catch (Exception e) {
                    e.printStackTrace();
                    log.warn("解密失败 待解密密文: {}", req, e);
                    try {
                        throw e;
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                break;
            default:
                return request;
        }

        return request;
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }
}
