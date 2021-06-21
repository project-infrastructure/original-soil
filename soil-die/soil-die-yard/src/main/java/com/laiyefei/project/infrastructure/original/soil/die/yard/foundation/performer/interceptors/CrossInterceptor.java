package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.performer.interceptors;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.performer.IPerformer;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.co.CrossResponseHeader;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.prepper.IInterceptorEntry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-09-29 18:09
 * @Desc : CROS拦截
 * @Version : v1.0.0.20200929
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Component
public class CrossInterceptor extends HandlerInterceptorAdapter implements IPerformer, IInterceptorEntry {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //附加Cross响应头
        for (CrossResponseHeader value : CrossResponseHeader.values()) {
            response.setHeader(value.getCode(), value.isDynamic() ? value.getValue(request) : value.getValue());
        }
        //放行
        return true;
    }
}
