package com.laiyefei.project.original.soil.standard.spread.foundation.pojo.co;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co.ICo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-01 09:05
 * @Desc : 特殊的屬性配置
 * @Version : v1.0.0.20200301
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public enum Feature implements ICo {
    TempDirPrefix(".laiyefei$$$temp", "项目临时文件夹前缀");

    private final String code;
    private final String description;

    Feature(String code, String description) {
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
