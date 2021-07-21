package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.bo;

import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dao.IRoleDao;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dao.IUserDao;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dao.IUserRoleDao;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dto.UserDto;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po.Role;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po.User;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.aid.ContextHolder;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.bo.AbsBaseBo;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-09-29 18:09
 * @Desc : 用户角色业务对象扩展
 * @Version : v2.0.0.20200929
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class UserBExt extends AbsBaseBo<UserDto, User> {
    private final IUserRoleDao userRoleDao;
    private final IUserDao userDao;
    private final IRoleDao roleDao;
    private final User user;
    private final List<Role> roles;

    public UserBExt(User po) {
        super(po);
        userRoleDao = ContextHolder.GainBean(IUserRoleDao.class);
        Assert.notNull(userRoleDao, "error: sorry, userRoleDao is can not find in context holder.");
        userDao = ContextHolder.GainBean(IUserDao.class);
        Assert.notNull(userDao, "error: sorry, userDao is can not find in context holder.");
        roleDao = ContextHolder.GainBean(IRoleDao.class);
        Assert.notNull(roleDao, "error: sorry, roleDao is can not find in context holder.");
        user = po;
        roles = userRoleDao.findRolesByUserWith(po.getAccount());
    }

    public User getUser() {
        return user;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public static final UserBExt BuildBy(final User user) {
        return new UserBExt(user);
    }
}
