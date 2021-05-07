package com.laiyefei.project.original.soil.die.yard;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.ao.Ao;
import com.laiyefei.project.original.soil.die.yard.foundation.action.AutoYardScanner;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : RBAC框架安装
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Ao
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(AutoYardScanner.class)
public @interface YardInstall {
}
