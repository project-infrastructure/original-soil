package com.laiyefei.project.infrastructure.original.soil.die.yard.central.core;

import com.laiyefei.project.infrastructure.original.soil.die.yard.central.service.IPermissionService;
import com.laiyefei.project.infrastructure.original.soil.standard.central.core.ICore;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.central.core.AbsBaseCoreImpl;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dao.IPermissionDao;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dto.PermissionDto;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po.Permission;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 权限信息核心实现
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Service
public class PermissionCoreImpl extends AbsBaseCoreImpl<PermissionDto, IPermissionDao, Permission> implements IPermissionService, ICore {

    @Override
    public Permission acceptDto(final PermissionDto dto) {
        Assert.notNull(dto.getAccessType(), "error: access type is null.");
        Assert.notNull(dto.getPermissionType(), "error: permssion type is null.");

        final Permission permission = new Permission();
        permission.setName(dto.getName());
        permission.setPath(dto.getPath());
        permission.setAccessType(dto.getAccessType().getCode());
        permission.setMethod(dto.getMethod());
        permission.setType(dto.getPermissionType().getCode());
        return permission;
    }
}
