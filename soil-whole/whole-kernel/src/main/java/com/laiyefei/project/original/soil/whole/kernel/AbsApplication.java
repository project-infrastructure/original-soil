package com.laiyefei.project.original.soil.whole.kernel;

import com.laiyefei.project.infrastructure.original.soil.standard.IStandardJava;
import com.laiyefei.project.original.soil.standard.spread.foundation.tools.util.IPUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-09-28 18:09
 * @Desc : the abstract application
 * @Version : v1.0.0.20200928
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class AbsApplication implements IStandardJava, CommandLineRunner, EnvironmentAware {

    protected Environment environment;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("-------<<<<<<<<<< " + environment.getProperty("spring.application.name") + "成功启动 --------------<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println("=> 便捷访问：http://".concat(IPUtil.getHostIpPort(environment)));
        System.out.println();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
