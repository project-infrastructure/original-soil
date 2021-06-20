package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.performer;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.performer.IPerformer;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.co.YardModuleInfo;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.performer.AbsDataHolder;
import org.springframework.stereotype.Component;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 数据注册
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Component
public class YardHolder extends AbsDataHolder implements IPerformer {
    @Override
    public String gainBasePackage() {
        return YardModuleInfo.BASE_PACKAGE;
    }
    
    @Override
    public String gainArtifactId() {
        return YardModuleInfo.Artifact.getCode();
    }
}
