package com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.pojo.co;

import org.springframework.stereotype.Component;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : module info
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Component
public final class SSMPModuleInfo implements IModule<SSMPModuleInfo> {
    public static final String BASE_PACKAGE = "com.laiyefei.project.original.soil.dependencies.ssmp";
    public static final String DAO_PACKAGE = "**.pojo.dao";

    @Override
    public String getBasePackageCode() {
        return SSMPModuleInfo.BASE_PACKAGE;
    }

    @Override
    public String getArtifactIdCode() {
        return "dependencies-ssmp";
    }
}
