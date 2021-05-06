package com.laiyefei.project.original.soil.common.pojo.co;

import com.laiyefei.project.infrastructure.standard.java.foundation.pojo.co.ICo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 权限类型
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@ApiModel("权限类型")
public enum PermissionType implements ICo {
    Api("1", "接口类型"),
    ;

    @ApiModelProperty("编码")
    private final String code;
    @ApiModelProperty("描述信息")
    private final String description;

    PermissionType(String code, String description) {
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

    public static final PermissionType BuildBy(final String code) {
        for (PermissionType value : PermissionType.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new RuntimeException("error: can not find the permission type with -> ".concat(code));
    }
}
