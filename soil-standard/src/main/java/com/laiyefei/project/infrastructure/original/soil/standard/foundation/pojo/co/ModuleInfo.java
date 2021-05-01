package com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-10-04 18:09
 * @Desc : 模块信息
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public final class ModuleInfo implements IModule<ModuleInfo> {

    @Override
    public String getBasePackageCode() {
        return "com.laiyefei.project.infrastructure.standard.java";
    }

    @Override
    public String getArtifactIdCode() {
        return "standard-java";
    }
}
