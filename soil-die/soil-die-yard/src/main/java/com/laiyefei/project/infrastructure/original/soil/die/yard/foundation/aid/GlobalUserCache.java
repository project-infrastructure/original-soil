package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.aid;

import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po.User;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;
import org.springframework.util.Assert;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 全局用户缓存
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public enum GlobalUserCache implements IAid {
    INSTANCE;
    //region User
    private final ThreadLocal<User> userCached = new ThreadLocal<User>();

    public <T extends User> void holdUser(final T t) {
        Assert.isNull(userCached.get(), "error: sorry, it holded.");
        userCached.set(t);
    }

    public <T extends User> T gainUser() {
        T t = (T) userCached.get();
        Assert.notNull(t, "error: no hold user before now.");
        return t;
    }

    public void releaseUser() {
        userCached.remove();
    }
    //endregion
}
