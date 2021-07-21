package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po;

import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dao.IRoleDao;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dto.RoleDto;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.aid.ContextHolder;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.pojo.po.AbsBasePo;
import org.springframework.util.Assert;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 基本角色信息持久化对象
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class Role extends AbsBasePo<RoleDto> {
    protected String code;
    protected String name;

    @Override
    public RoleDto buildDto() {
        return new RoleDto() {
            @Override
            public String getId() {
                return Role.this.id;
            }

            @Override
            public String getCode() {
                return Role.this.code;
            }

            @Override
            public String getName() {
                return Role.this.name;
            }
        };
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static final Role Just(String code) {
        return ContextHolder.DoDaoAction(IRoleDao.class, dao -> {
            final Role role = dao.selectBy(code);
            Assert.notNull(role, "error: sorry, the user account is ".concat(code).concat(" not exist."));
            return role;
        });
    }

    public static final Role Find(String code) {
        return ContextHolder.DoDaoAction(IRoleDao.class, dao -> {
            return dao.selectBy(code);
        });
    }

    public static final Role BuildBy(final String code, final String name) {
        final Role role = new Role();
        role.setCode(code);
        role.setName(name);
        return role;
    }
}
