package com.laiyefei.project.original.soil.common.pojo.ao;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.ao.Ao;
import com.laiyefei.project.original.soil.common.pojo.co.AccessType;
import com.laiyefei.project.original.soil.common.pojo.co.PermissionType;

import java.lang.annotation.*;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-09-29 18:09
 * @Desc : 权限注解
 * @Version : v1.0.0.20200929
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Ao
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Power {
    String PERMISSION_HEADER = "";

    /**
     * 说明
     *
     * @return
     */
    String name();

    /**
     * 访问类型
     *
     * @return
     */
    AccessType accessType() default AccessType.Public;

    /**
     * 权限类型
     *
     * @return
     */
    PermissionType permissionType() default PermissionType.Api;

}
