package com.laiyefei.project.infrastructure.original.soil.equip.swagger.performer;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.performer.IPerformer;
import com.laiyefei.project.infrastructure.original.soil.equip.swagger.co.SwaggerModuleInfo;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.performer.AbsDataHolder;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.IResourceWhiteList;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.IWhiteList;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.ClassUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : swagger 配置
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Component
public class SwaggerHolder extends AbsDataHolder implements IPerformer {

    private final SwaggerModuleInfo swaggerModuleInfo;

    public SwaggerHolder(SwaggerModuleInfo swaggerModuleInfo) {
        this.swaggerModuleInfo = swaggerModuleInfo;
    }

    @Override
    public void installData() {
        this.whiteLists = Optional.of(ClassUtil.DigEnumBy(this.swaggerModuleInfo.BASE_PACKAGE, IWhiteList.class)).orElse(new ArrayList<>());
        this.resourceWhiteLists = Optional.of(ClassUtil.DigEnumBy(this.swaggerModuleInfo.BASE_PACKAGE, IResourceWhiteList.class))
                .orElse(new ArrayList<>()).stream().filter(e -> e.isResource()).collect(Collectors.toList());
    }

    @Override
    public String gainBasePackage() {
        return this.swaggerModuleInfo.BASE_PACKAGE;
    }

    @Override
    public String gainArtifactId() {
        return this.swaggerModuleInfo.getArtifactIdCode();
    }
}
