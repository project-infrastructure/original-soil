package com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.ao;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co.IModule;

import java.lang.annotation.*;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-10-04 18:09
 * @Desc : 模块描述
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Ao
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Module {
    Class<? extends IModule<?>> value();
}
