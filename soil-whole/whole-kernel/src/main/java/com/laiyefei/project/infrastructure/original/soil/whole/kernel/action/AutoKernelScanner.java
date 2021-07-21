package com.laiyefei.project.infrastructure.original.soil.whole.kernel.action;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.action.IAction;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-10-02 18:09
 * @Desc : 自动包扫者
 * @Version : v2.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Configuration
@ComponentScan("com.laiyefei.project.original.soil.whole.kernel.*")
public class AutoKernelScanner implements IAction {
}
