package com.laiyefei.project.original.soil.die.yard.foundation.pojo.co;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co.ICo;
import com.laiyefei.project.original.soil.standard.spread.foundation.pojo.co.IWhiteList;
import lombok.Getter;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-09-29 18:09
 * @Desc : 白名单
 * @Version : v1.0.0.20200929
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Getter
public enum WhiteList implements ICo, IWhiteList {
    Error(WhiteList.ERROR, "错误路由"),
    LoginRoute(WhiteList.LOGIN_ROUTE, "登录页面"),
    FailedAjax(WhiteList.FAILED_AJAX, "登录失败的ajax信息"),
    LoginCheckRoute(WhiteList.LOGIN_CHECK_ROUTE_TOKEN, "登录校验接口"),
    ;
    public static final String TOKEN_PATH = "/token";
    public static final String ERROR = "/error";
    public static final String LOGIN_ROUTE = "/login";
    public static final String LOGIN_ROUTE_PAGE_VIEW = "/login/index";
    public static final String LOGIN_CHECK_ROUTE_TOKEN = LOGIN_ROUTE + TOKEN_PATH;
    public static final String FAILED_AJAX = "/failed/ajax";
    private final String routeMatch;
    private final String description;
    private String classPath;
    private boolean resource = false;

    WhiteList(String routeMatch, String description, String classPath) {
        this.routeMatch = routeMatch;
        this.description = description;
        this.classPath = classPath;
        this.resource = true;
    }

    WhiteList(String routeMatch, String description) {
        this.routeMatch = routeMatch;
        this.description = description;
    }

    @Override
    public boolean isResource() {
        return resource;
    }

    @Override
    public String getCode() {
        return routeMatch;
    }

    @Override
    public String gainPath() {
        return routeMatch;
    }
}
