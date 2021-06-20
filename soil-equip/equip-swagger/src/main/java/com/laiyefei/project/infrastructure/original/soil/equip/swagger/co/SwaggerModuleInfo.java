package com.laiyefei.project.infrastructure.original.soil.equip.swagger.co;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co.IModule;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co.ModuleInfo;
import org.springframework.stereotype.Component;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 模块信息
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Component
public final class SwaggerModuleInfo implements IModule<ModuleInfo> {
    public static final String BASE_PACKAGE = "com.laiyefei.project.original.soil.equip.swagger";

    @Override
    public String getBasePackageCode() {
        return BASE_PACKAGE;
    }

    @Override
    public String getArtifactIdCode() {
        return "equip-swagger";
    }
}
