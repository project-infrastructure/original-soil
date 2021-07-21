package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.co.ICo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 许可类型列举
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public enum LicenseType implements ICo {
    Apache("Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0", "可以商用不需要交费。要带license"),
    ;
    private final String code;
    private final String url;
    private final String description;

    LicenseType(String code, String url, String description) {
        this.code = code;
        this.url = url;
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public String getUrl() {
        return this.url;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
