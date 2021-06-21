package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.performer.interceptors;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.performer.IPerformer;
import com.laiyefei.project.infrastructure.original.soil.common.pojo.ao.Power;
import com.laiyefei.project.infrastructure.original.soil.common.pojo.co.AccessType;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.aid.GlobalUserCache;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.co.DefaultUser;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dao.IRolePermissionDao;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dao.IUserRoleDao;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po.User;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.prepper.IInterceptorEntry;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-09-29 18:09
 * @Desc : 权限拦截
 * @Version : v1.0.0.20200929
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Component
@Order(3)
public class PInterceptor extends HandlerInterceptorAdapter implements IPerformer, IInterceptorEntry {

    @Resource
    private IUserRoleDao userRoleDao;
    @Resource
    private IRolePermissionDao rolePermissionDao;

    @PostConstruct
    public void afterCheck() {
        Assert.notNull(this.userRoleDao, "error: sorry, the userRoleDao is null.");
        Assert.notNull(this.rolePermissionDao, "error: sorry, the rolePermissionDao is null.");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //与自己无关的逻辑不干预
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        final Method method = ((HandlerMethod) handler).getMethod();
        //策略，没有注解，默认公开
        if (!method.isAnnotationPresent(Power.class)) {
            return true;
        }
        final Power power = method.getAnnotation(Power.class);
        if (AccessType.Public.equals(power.accessType())) {
            return true;
        }
        //root 放行
        final User user = GlobalUserCache.INSTANCE.gainUser();
        if (DefaultUser.IsRoot(user)) {
            return true;
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

}
