package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.action;

import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.co.YardModuleInfo;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.action.IAction;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-01 09:05
 * @Desc : 自动包扫者
 * @Version : v1.0.0.20200301
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Configuration
@ComponentScan(YardModuleInfo.BASE_PACKAGE + ".*")
public class AutoYardScanner implements IAction {
}
