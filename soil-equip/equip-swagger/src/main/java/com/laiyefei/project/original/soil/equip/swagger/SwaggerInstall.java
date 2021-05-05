package com.laiyefei.project.original.soil.equip.swagger;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.ao.Ao;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.ao.Module;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co.IModule;
import com.laiyefei.project.original.soil.equip.swagger.action.AutoSwaggerScanner;
import com.laiyefei.project.original.soil.equip.swagger.co.SwaggerModuleInfo;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : swagger 安装
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Ao
@Module(SwaggerModuleInfo.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(AutoSwaggerScanner.class)
public @interface SwaggerInstall {
    @AliasFor(annotation = Module.class)
    Class<? extends IModule<?>> value();
}
