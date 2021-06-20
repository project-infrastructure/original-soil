package com.laiyefei.project.infrastructure.original.soil.equip.swagger.co;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co.ICo;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.IResourceWhiteList;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.IWhiteList;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 白名单枚举
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public enum WhiteList implements ICo, IWhiteList, IResourceWhiteList {
    V2("/v2/**", "api接口"),
    API("/api/**", "api接口"),
    STATIC("/static/**", "静态资源文件", "classpath:/static/"),
    TEMPLATES("/templates/**", "模板资源", "classpath:/templates/"),
    HTML("/swagger-ui.html", "html资源", "classpath:/META-INF/resources/"),
    RESOURCES("/swagger-resources/**", "swagger资源", "classpath:/META-INF/resources/"),
    WEBJARS("/webjars/**", "web jar 资源", "classpath:/META-INF/resources/webjars/"),
    ;
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

    public String getRouteMatch() {
        return routeMatch;
    }

    public String getClassPath() {
        return classPath;
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
    public String getDescription() {
        return description;
    }

    @Override
    public String gainPath() {
        return routeMatch;
    }
}
