package com.cyqqq.services.config;

import com.cyqqq.util.RequestUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description
 * 日志拦截器
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/12/18 9:26
 * @Version :
 */
@Order(1)
@Component
public class LogInterceptorAdapter extends HandlerInterceptorAdapter {
    private static final Log log = LogFactory.getLog(LogInterceptorAdapter.class);

    private static final String REQUEST_START_TIME = "REQUEST_START_TIME";

    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#
     * preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(REQUEST_START_TIME, new Date());
        return true;
    }


    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#
     * afterCompletion(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler,
                                Exception ex) throws IOException {

        Date start = (Date) request.getAttribute(REQUEST_START_TIME);
        Date end = new Date();

        log.info("本次请求耗时：" + (end.getTime() - start.getTime()) + "毫秒；" + getRequestInfo(request).toString());

    }


    /**
     * 主要功能:获取请求详细信息
     * 注意事项:无
     *
     * @param request 请求
     * @return 请求信息
     */
    private StringBuilder getRequestInfo(HttpServletRequest request) throws IOException {
        StringBuilder reqInfo = new StringBuilder();
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String urlPath = urlPathHelper.getLookupPathForRequest(request);
        reqInfo.append(" 请求路径=" + urlPath);
        reqInfo.append(" 来源IP=" + RequestUtil.getIpAddrByRequest(request));

        Map<String, Object> parameters = RequestUtil.getParameters(request);
        if (parameters != null && parameters.size() > 0){
            reqInfo.append(" 请求参数=" + parameters.toString());
        } else {
            if (request instanceof HttpInputMessage){
                ((HttpInputMessage) request).getBody();
            }
            String req = "";
            InputStream is = null;
            try {
                is = request.getInputStream();
                req = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining(System.lineSeparator()));

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                is.close();
            }
            if (!"".equals(req)){
                reqInfo.append(" 请求参数=" + req);
            }

        }
        return reqInfo;
    }
}
