package com.laiyefei.project.original.soil.die.yard.foundation.pojo.co;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co.ICo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-01 09:05
 * @Desc : 模块信息
 * @Version : v1.0.0.20200301
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public enum YardModuleInfo implements ICo {
    Artifact("soil-yard", "yard 模块坐标"),
    ;
    public static final String BASE_PACKAGE = "com.laiyefei.project.original.soil.die.yard";
    private final String code;
    private final String description;

    YardModuleInfo(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}