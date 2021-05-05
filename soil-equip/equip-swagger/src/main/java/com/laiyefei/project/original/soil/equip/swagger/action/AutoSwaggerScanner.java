package com.laiyefei.project.original.soil.equip.swagger.action;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.action.IAction;
import com.laiyefei.project.original.soil.equip.swagger.co.SwaggerModuleInfo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : swagger 包扫者
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Configuration
@ComponentScan(SwaggerModuleInfo.BASE_PACKAGE + ".*")
public class AutoSwaggerScanner implements IAction {
}
