package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.performer.interceptors;

import com.laiyefei.project.infrastructure.original.soil.die.yard.central.service.IUserService;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.performer.IPerformer;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.aid.GlobalUserCache;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.performer.TokenDataHolder;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.eo.PVException;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.eo.RestException;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po.User;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.performer.TokenConfiger;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.ExceptionEnum;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.prepper.IInterceptorEO;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.ClassUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.JudgeUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-09-29 18:09
 * @Desc : 用户拦截
 * @Version : v1.0.0.20200929
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Component
@Order(2)
public class UInterceptor extends HandlerInterceptorAdapter implements IPerformer, IInterceptorEO {

    private final TokenConfiger tokenConfiger;
    private final TokenDataHolder tokenDataHolder;
    private final IUserService userService;

    @Autowired
    public UInterceptor(TokenConfiger tokenConfiger,
                        TokenDataHolder tokenDataHolder,
                        IUserService userService) {
        this.tokenConfiger = tokenConfiger;
        this.tokenDataHolder = tokenDataHolder;
        this.userService = userService;
    }

    @PostConstruct
    public void afterCheck() {
        Assert.notNull(this.tokenConfiger, "error: can not find the tokenComps from contextHolder.");
        Assert.notNull(this.tokenConfiger.gainTokenHeader(), "error: can not find the token config about token header.");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //与自己无关的逻辑不干预
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        final Method method = ((HandlerMethod) handler).getMethod();
        final Class<?> clazz = method.getDeclaringClass();
        //策略，没有注解，拦截掉
        if (!ClassUtil.isAnnotationPresent(method, RequestMapping.class) &&
                !ClassUtil.isAnnotationPresent(clazz, RequestMapping.class)) {
            //沒有注解不理，放行
            return true;
        }
        Assert.isTrue(ClassUtil.isAnnotationPresent(method, RequestMapping.class), "error: can not find the request mapping to route.");

        //查找header头 access_token
        String accessToken = JudgeUtil.NVL(request.getParameter(tokenConfiger.gainTokenHeader()), request.getHeader(tokenConfiger.gainTokenHeader()));
        if (JudgeUtil.IsNull(accessToken)) {
            Cookie[] cookies = JudgeUtil.NVL(request.getCookies(), new Cookie[0]);
            for (Cookie cookie : cookies) {
                if (tokenConfiger.gainTokenHeader().equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                    break;
                }
            }
        }
        if (StringUtil.isEmpty(accessToken)) {
            //没有token跳转登录
            if (ClassUtil.isAnnotationPresent(clazz, RestController.class) ||
                    ClassUtil.isAnnotationPresent(method, ResponseBody.class)) {
                throw new RestException.UnLogin();
            } else if (ClassUtil.isAnnotationPresent(clazz, Controller.class)) {
                throw new PVException.UnLogin();
            } else {
                throw new RuntimeException("error: no allow token and the access token and un know exception.");
            }
        }
        String userId;
        try {
            userId = tokenDataHolder.unLashToken(accessToken);
        } catch (Exception e) {
            throw new PVException.VerifyFailed(e);
        }
        Assert.isTrue(StringUtil.IsNotTrimEmpty(userId), "error: sorry, the userId from token is empty.");
        final User user = this.userService.getUserById(userId);
        if (JudgeUtil.IsNull(user)) {
            throw new PVException.NoUser(ExceptionEnum.UnAccess);
        }
        // token 处理
        GlobalUserCache.INSTANCE.holdUser(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //release user cache
        GlobalUserCache.INSTANCE.releaseUser();
    }
}
