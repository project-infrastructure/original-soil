package com.laiyefei.project.original.soil.common.pojo.co;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co.ICo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 访问类型
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@ApiModel("访问类型")
public enum AccessType implements ICo {
    Public("1", "公开"),
    Private("2", "私有"),
    ;

    @ApiModelProperty("编码")
    private final String code;
    @ApiModelProperty("描述信息")
    private final String description;

    AccessType(String code, String description) {
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

    public static final AccessType BuildBy(final String code) {
        for (AccessType value : AccessType.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new RuntimeException("error: can not find the access type with -> ".concat(code));
    }
}
