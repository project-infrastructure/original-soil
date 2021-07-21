package com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.action;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.action.IAction;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.pojo.co.SSMPModuleInfo;
import com.laiyefei.project.infrastructure.original.soil.equip.swagger.SwaggerInstall;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 自动包扫
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@SwaggerInstall(SSMPModuleInfo.class)
@Configuration
@MapperScan(SSMPModuleInfo.DAO_PACKAGE)
@ComponentScan(SSMPModuleInfo.BASE_PACKAGE + ".*")
@Lazy
public class AutoSSMPInitializer implements IAction {
}
