package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po;

import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.pojo.po.AbsBasePo;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.dto.AbsDto;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 角色权限对象
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class RolePermission extends AbsBasePo {

    protected String fkRoleId;
    protected String fkPermissionId;

    @Override
    public AbsDto buildDto() {
        throw new RuntimeException("error: no impl.");
    }

    public String getFkRoleId() {
        return fkRoleId;
    }

    public void setFkRoleId(String fkRoleId) {
        this.fkRoleId = fkRoleId;
    }

    public String getFkPermissionId() {
        return fkPermissionId;
    }

    public void setFkPermissionId(String fkPermissionId) {
        this.fkPermissionId = fkPermissionId;
    }
}
