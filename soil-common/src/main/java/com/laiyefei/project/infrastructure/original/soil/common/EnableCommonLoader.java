package com.laiyefei.project.infrastructure.original.soil.common;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.ao.Ao;
import com.laiyefei.project.infrastructure.original.soil.common.action.AutoCommonScanner;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-09-29 18:09
 * @Desc : 允许加载common
 * @Version : v1.0.0.20200929
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Ao
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(AutoCommonScanner.class)
public @interface EnableCommonLoader {
}
