package com.cyqqq.services.config;

import com.cyqqq.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/12/12 17:30
 * @Version :
 */
@Order(2)
@Component
public class AuthorizationInterceptorAdapter extends HandlerInterceptorAdapter {

    private  final Logger log =  LoggerFactory.getLogger(AuthorizationInterceptorAdapter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        IgnoreAuth ignoreAuth = null;

        if (handler instanceof HandlerMethod){
            ignoreAuth = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        }

        if (ignoreAuth != null){
            // TODO 验证TOKEN是否过期
            return true;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}
