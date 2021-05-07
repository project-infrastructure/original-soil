package com.laiyefei.project.original.soil.die.yard.foundation.pojo.dao;

import com.laiyefei.project.original.soil.dependencies.ssmp.foundation.pojo.dao.IBaseDao;
import com.laiyefei.project.original.soil.die.yard.foundation.pojo.po.Permission;
import com.laiyefei.project.original.soil.die.yard.foundation.pojo.po.RolePermission;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 用户角色操作对象
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IRolePermissionDao extends IBaseDao<RolePermission> {

    @Select("select * from permission where id in (select fk_permission_id from role_permission where fk_role_id in (select id from role where code = #{code}))")
    Set<Permission> findPermissionsByRoleWith(final String code);

}
