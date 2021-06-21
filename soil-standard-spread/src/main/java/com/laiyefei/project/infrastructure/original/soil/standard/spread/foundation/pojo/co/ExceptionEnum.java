package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co.ICo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-04-07 12:52
 * @Desc : exception enum
 * @Version : v1.0.0.20200407
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public enum ExceptionEnum implements ICo {
    Other("L-00", "未知异常"),
    Controller("L-01", "控制层异常"),
    Service("L-02", "接口层异常"),
    Dao("L-03", "数据交互层异常"),

    UnAccess("error: UnAccess", "禁止访问"),
    VerifyFailed("error: VerifyFailed", "校验异常"),
    NoUser("error: NoUser", "找不到用户"),
    UnLogin("error: UnLogin", "未登录"),
    ;

    private final String code;
    private final String description;

    ExceptionEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
