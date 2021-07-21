package com.laiyefei.project.infrastructure.original.soil.die.yard.central.core;

import com.laiyefei.project.infrastructure.original.soil.die.yard.central.service.IRoleService;
import com.laiyefei.project.infrastructure.original.soil.standard.central.core.ICore;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.central.core.AbsBaseCoreImpl;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dao.IRoleDao;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dto.RoleDto;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po.Role;
import org.springframework.stereotype.Service;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 用户信息核心实现
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Service
public class RoleCoreImpl extends AbsBaseCoreImpl<RoleDto, IRoleDao, Role> implements IRoleService, ICore {

    @Override
    public Role acceptDto(final RoleDto dto) {
        final Role role = new Role();
        role.setCode(dto.getCode());
        role.setName(dto.getName());
        return role;
    }
}
