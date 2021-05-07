package com.laiyefei.project.original.soil.whole.shell.action;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.action.IAction;
import com.laiyefei.project.original.soil.whole.kernel.pojo.co.KernelModuleInfo;
import com.laiyefei.project.original.soil.whole.shell.aid.KernelProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-10-02 18:09
 * @Desc : 自动初始化
 * @Version : v1.0.0.20201002
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Configuration
@EnableConfigurationProperties(KernelProperties.class)
@ComponentScan(basePackages = {KernelModuleInfo.BASE_PACKAGE})
@MapperScan(basePackages = {KernelModuleInfo.DAO_PACKAGE})
@Order(1)
public class AutoInitializer implements IAction {
}
