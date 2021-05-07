package com.laiyefei.project.original.soil.die.yard.foundation.pojo.dao;

import com.laiyefei.project.original.soil.dependencies.ssmp.foundation.pojo.dao.IBaseDao;
import com.laiyefei.project.original.soil.die.yard.foundation.pojo.po.Role;
import com.laiyefei.project.original.soil.die.yard.foundation.pojo.po.User;
import com.laiyefei.project.original.soil.die.yard.foundation.pojo.po.UserRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 用户角色操作对象
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IUserRoleDao extends IBaseDao<UserRole> {

    @Select("select * from role where id in (select fk_role_id from user_role where fk_user_id in (select id from user where account = #{account}))")
    List<Role> findRolesByUserWith(final String account);

    @Select("select * from user where id in (select fk_user_id from user_role where fk_role_id in (select id from role where code = #{account}))")
    List<User> findUsersByRoleWith(final String code);
}
