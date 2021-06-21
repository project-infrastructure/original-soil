package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.co;

import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.fo.IHolderHandler;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po.User;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.JudgeUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.ObjectUtil;
import org.springframework.util.Assert;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-01 09:05
 * @Desc : default user system give
 * @Version : v1.0.0.20200301
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public enum DefaultUser implements IInitCo<User> {
    ADMIN(User.BuildBy("admin", "超级管理员用户", "admin111"), DefaultRole.ROOT),
    LAIYEFEI(User.BuildBy("laiyefei", "laiyefei", "laiyefei"), DefaultRole.ROOT),
    CATHERINE(User.BuildBy("catherine", "catherine", "catherine"), DefaultRole.NORMAL),
    GUEST(User.BuildBy("guest", "宾客用户", "guest"), DefaultRole.VISITOR),
    ;

    private final User user;
    private final DefaultRole defaultRole;
    private final String code;
    private final String description;

    DefaultUser(final User user, final DefaultRole defaultRole) {
        this.user = JudgeUtil.NVL(User.Find(user.getAccount()), User.AdornWholeBy(user));
        this.defaultRole = defaultRole;
        this.code = this.user.getAccount();
        this.description = this.user.getName();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public User justUser() {
        return User.Just(this.code);
    }

    public DefaultRole getDefaultRole() {
        return this.defaultRole;
    }

    @Override
    public void doInit(IHolderHandler.IHandler<User, User> handler) {
        Assert.isTrue(handler.done(User.UnpackConditionBy(this.user), this.user), "error: the doInit is exec failed.");
    }

    public static final DefaultUser BuildBy(final User user) {
        Assert.notNull(user, "error: sorry, the user is null.");
        for (DefaultUser value : DefaultUser.values()) {
            Assert.notNull(value.getUser(), "error: sorry, the user in enum is null.");
            if (value.getUser().equals(user)) {
                return value;
            }
        }
        return ObjectUtil.GetNULL();
    }

    public static final boolean IsRoot(final User user) {
        DefaultUser defaultUser = BuildBy(user);
        if (JudgeUtil.IsNull(defaultUser)) {
            return false;
        }
        return JudgeUtil.NVL(DefaultRole.IsRoot(defaultUser.getDefaultRole()), false);
    }
}
