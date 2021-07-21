package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.performer.interceptors;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.performer.IPerformer;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.prepper.IInterceptorEntry;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-09-29 18:09
 * @Desc : 探测拦截，试探性拦截
 * @Version : v2.0.0.20200929
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Component
@Order(1)
public class TKInterceptor extends HandlerInterceptorAdapter implements IPerformer, IInterceptorEntry {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //探测请求拦截
        if (request.getMethod().equals(RequestMethod.OPTIONS)) {
            //探测成功
            response.setStatus(HttpStatus.OK.value());
            //拦截
            return false;
        }
        //放行
        return true;
    }
}
