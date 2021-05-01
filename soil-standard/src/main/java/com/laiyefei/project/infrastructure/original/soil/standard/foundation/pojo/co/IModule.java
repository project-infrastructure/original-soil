package com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-10-04 18:09
 * @Desc : 模块描述
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IModule<T extends IModule> extends ICo {
    String GROUP_ID = "com.laiyefei.project";
    String VERSION = "v1.0.0-SNAPSHOT";

    @Override
    default String getCode() {
        return this.getClass().getName();
    }

    @Override
    default String getDescription() {
        return "模块描述信息";
    }

    default ICo getBasePackage() {
        return ICo.BuildBy(this.getBasePackageCode(), "基本包路径");
    }

    default ICo getGroupId() {
        return ICo.BuildBy(this.getGroupIdCode(), "项目坐标 - groupId");
    }

    default ICo getArtifactId() {
        return ICo.BuildBy(this.getArtifactIdCode(), "项目坐标 - artifactId");
    }

    default ICo getVersion() {
        return ICo.BuildBy(this.getVersionCode(), "版本号");
    }

    String getBasePackageCode();

    default String getGroupIdCode() {
        return IModule.GROUP_ID;
    }

    String getArtifactIdCode();

    default String getVersionCode() {
        return VERSION;
    }
}
