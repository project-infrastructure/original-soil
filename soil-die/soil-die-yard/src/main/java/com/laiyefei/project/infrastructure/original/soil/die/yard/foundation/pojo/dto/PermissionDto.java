package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dto;

import com.laiyefei.project.infrastructure.original.soil.common.pojo.co.AccessType;
import com.laiyefei.project.infrastructure.original.soil.common.pojo.co.PermissionType;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.dto.AbsDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 基本权限信息持久化对象
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@ApiModel("权限传输对象")
public class PermissionDto extends AbsDto {
    @ApiModelProperty("名称")
    protected String name;
    @ApiModelProperty("路径")
    protected String path;
    @ApiModelProperty("请求方法")
    protected String method;
    @ApiModelProperty("访问类型")
    protected AccessType accessType;
    @ApiModelProperty("权限类型")
    protected PermissionType permissionType;

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public PermissionType getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
    }
}
