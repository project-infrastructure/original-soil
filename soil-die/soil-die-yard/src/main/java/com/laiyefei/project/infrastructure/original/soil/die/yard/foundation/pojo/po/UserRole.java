package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.pojo.po.AbsBasePo;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.dto.AbsDto;
import org.springframework.util.Assert;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 用户角色关联表
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class UserRole extends AbsBasePo {

    @TableField("fk_user_id")
    protected String fkUserId;
    @TableField("fk_role_id")
    protected String fkRoleId;

    @Override
    public AbsDto buildDto() {
        throw new RuntimeException("error: no impl.");
    }

    public String getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(String fkUserId) {
        this.fkUserId = fkUserId;
    }

    public String getFkRoleId() {
        return fkRoleId;
    }

    public void setFkRoleId(String fkRoleId) {
        this.fkRoleId = fkRoleId;
    }

    public static final UserRole BuildBy(final User user, final Role role) {
        Assert.notNull(user, "error: the user is can not be null.");
        Assert.notNull(role, "error: the role is can not be null.");
        final UserRole userRole = new UserRole();
        userRole.setFkUserId(user.getId());
        userRole.setFkRoleId(role.getId());
        return userRole;
    }
}
