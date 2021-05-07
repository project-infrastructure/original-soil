package com.laiyefei.project.original.soil.die.yard.foundation.pojo.dao;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.laiyefei.project.original.soil.dependencies.ssmp.foundation.pojo.dao.IBaseDao;
import com.laiyefei.project.original.soil.die.yard.foundation.pojo.po.Role;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 用户操作对象
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IRoleDao extends IBaseDao<Role> {
    default Role selectBy(final String code) {
        final Role role = new Role();
        role.setCode(code);
        return this.selectOne(Wrappers.query(role));
    }
}
