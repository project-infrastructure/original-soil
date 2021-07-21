package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.co;

import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.fo.IHolderHandler;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po.Role;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.JudgeUtil;
import org.springframework.util.Assert;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-01 09:05
 * @Desc : 默认权限
 * @Version : v2.0.0.20200301
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public enum DefaultRole implements IInitCo<Role> {
    ROOT(Role.BuildBy("root", "超级管理员角色")),
    NORMAL(Role.BuildBy("normal", "普通角色")),
    VISITOR(Role.BuildBy("visitor", "访客角色")),
    ;

    private final Role role;
    private final String code;
    private final String description;

    DefaultRole(final Role role) {
        this.role = JudgeUtil.NVL(Role.Find(role.getCode()), role);
        this.code = role.getCode();
        this.description = role.getName();
    }

    @Override
    public void doInit(IHolderHandler.IHandler<Role, Role> handler) {
        Assert.isTrue(handler.done(role, role), "error: sorry, the role init action is failed.");
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public Role getRole() {
        return this.role;
    }

    public Role justRole() {
        return Role.Just(this.code);
    }

    public static final boolean IsRoot(final DefaultRole defaultRole) {
        return DefaultRole.ROOT.equals(defaultRole);
    }
}
