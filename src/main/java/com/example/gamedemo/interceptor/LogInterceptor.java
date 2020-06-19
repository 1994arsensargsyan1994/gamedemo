package com.example.gamedemo.interceptor;

import com.example.gamedemo.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class LogInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> logs = new HashMap<>();
        logs.put("request_time", System.currentTimeMillis());
        logs.put("request_URI", request.getRequestURI());
        request.setAttribute("LOG", logs);
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Map<String, Object> logs = (Map<String, Object>) request.getAttribute("LOG");
        long responseTime = System.currentTimeMillis();
        logs.put("response_time", responseTime);
        logs.put("response_duration", responseTime - (Long) logs.get("request_time"));

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Map<String, Object> logs = (Map<String, Object>) request.getAttribute("LOG");
        long respTime = System.currentTimeMillis();
        logs.put("response_time_after", respTime);
        logs.put("response_time_after_duration", respTime - (Long) logs.get("request_time"));

        log.info(JsonUtil.serialise(logs));
    }
}
