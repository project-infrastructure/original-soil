package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.co;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.co.ICo;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : cross 响应头
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public enum CrossResponseHeader implements ICo {
    AllowOrigin("Access-Control-Allow-Origin", request -> {
        return request.getScheme().concat("://").concat(request.getServerName()).concat(":").concat(String.valueOf(request.getServerPort()));
    }, "允许的域名", true),
    AllMethods("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS", "允许的方法"),
    AllowHeaders("Access-Control-Allow-Headers", "Content-Type,", "自定义请求头"),
    AllowCredentials("Access-Control-Allow-Credentials", "true", "允许证书"),
    ExposeHeaders("Access-Control-Expose-Headers", "customResponse,", "自定义响应头"),
    ;
    private final String code;
    private final String description;
    private boolean dynamic = false;
    private String value;
    private Function<HttpServletRequest, String> function;

    CrossResponseHeader(String code, String value, String description) {
        this.code = code;
        this.value = value;
        this.description = description;
    }

    CrossResponseHeader(String code, Function<HttpServletRequest, String> function, String description, boolean dynamic) {
        this.code = code;
        this.description = description;
        this.dynamic = dynamic;
        this.function = function;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public String getValue(HttpServletRequest request) {
        return this.function.apply(request);
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public boolean isDynamic() {
        return dynamic;
    }
}
