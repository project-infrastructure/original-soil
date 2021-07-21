package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.prepper;

import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.pojo.po.AbsBasePo;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.prepper.AbsMetaObjectHandler;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.aid.GlobalUserCache;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 对象处理默认行为
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Configuration
public class YardSSMPMoHandler extends AbsMetaObjectHandler {

    @Override
    protected Map<String, Object> installInsert() {
        Assert.notNull(GlobalUserCache.INSTANCE.gainUser(), "error: user from instance is null.");

        final Map map = new HashMap();
        map.put(AbsBasePo.CREATOR_ID, GlobalUserCache.INSTANCE.gainUser().getId());
        map.put(AbsBasePo.UPDATOR_ID, GlobalUserCache.INSTANCE.gainUser().getId());
        return map;
    }

    @Override
    protected Map<String, Object> installUpdate() {
        final Map map = new HashMap();
        map.put(AbsBasePo.UPDATOR_ID, GlobalUserCache.INSTANCE.gainUser().getId());
        return map;
    }
}
