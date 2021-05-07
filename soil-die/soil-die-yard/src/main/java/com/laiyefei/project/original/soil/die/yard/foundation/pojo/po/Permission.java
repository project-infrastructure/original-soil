package com.laiyefei.project.original.soil.die.yard.foundation.pojo.po;

import com.laiyefei.project.original.soil.common.pojo.co.AccessType;
import com.laiyefei.project.original.soil.common.pojo.co.PermissionType;
import com.laiyefei.project.original.soil.dependencies.ssmp.foundation.pojo.po.AbsBasePo;
import com.laiyefei.project.original.soil.die.yard.foundation.pojo.dto.PermissionDto;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 基本权限信息持久化对象
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class Permission extends AbsBasePo<PermissionDto> {
    protected String name;
    protected String path;
    protected String method;
    protected String accessType;
    protected String type;

    @Override
    public PermissionDto buildDto() {
        return new PermissionDto() {

            @Override
            public String getName() {
                return Permission.this.name;
            }

            @Override
            public String getPath() {
                return Permission.this.path;
            }

            @Override
            public AccessType getAccessType() {
                return AccessType.BuildBy(Permission.this.accessType);
            }

            @Override
            public String getMethod() {
                return Permission.this.method;
            }

            @Override
            public PermissionType getPermissionType() {
                return PermissionType.BuildBy(Permission.this.type);
            }
        };
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
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

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static final Permission BuildBy(String name,
                                           String path,
                                           String method,
                                           String accessType,
                                           String type) {
        final Permission permission = new Permission();
        permission.setName(name);
        permission.setPath(path);
        permission.setMethod(method);
        permission.setAccessType(accessType);
        permission.setType(type);
        return permission;
    }
}
