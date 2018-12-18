package com.cyqqq.services.config;

import com.cyqqq.annotation.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Description
 * 用户自定义解析器
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/12/14 10:38
 * @Version :
 */
public class UserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(JSONObject.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        JSONObject jsonObject = methodParameter.getParameterAnnotation(JSONObject.class);
        Class clazz = methodParameter.getParameterType();
        String jsonData = nativeWebRequest.getParameter("");
        if (jsonData == null) {
            return clazz.newInstance();
        }
        Object object = com.alibaba.fastjson.JSONObject.parseObject(jsonData, clazz);
        return object;
    }
}
