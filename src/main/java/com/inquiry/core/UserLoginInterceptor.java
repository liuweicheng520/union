package com.inquiry.core;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Description: 登陆拦截器
 * @Auther: liuweicheng
 * @Date: 2019-12-09 14:13
 */
@SuppressWarnings("all")
@Component
public class UserLoginInterceptor implements HandlerInterceptor {


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        System.out.println("拦截器作用");
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if (null != user) {//已登录
            return true;
        } else {//未登录
            //直接重定向到登录页面
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
    }
}
