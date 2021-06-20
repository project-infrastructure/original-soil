package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dao;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.pojo.dao.IBaseDao;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po.User;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 用户操作对象
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IUserDao extends IBaseDao<User> {
    default User selectBy(final String account) {
        final User user = new User();
        user.setAccount(account);
        return this.selectOne(Wrappers.query(user));
    }
}
