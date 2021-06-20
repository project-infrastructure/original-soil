package com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.prepper;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.performer.IPerformer;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.pojo.po.AbsBasePo;
import org.apache.ibatis.reflection.MetaObject;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 对象处理默认行为
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class AbsMetaObjectHandler implements IPerformer, MetaObjectHandler {

    protected abstract Map<String, Object> installInsert();

    protected abstract Map<String, Object> installUpdate();

    @Override
    public void insertFill(MetaObject metaObject) {
        Map<String, Object> items = installInsert();
        if (null == items || items.size() <= 0) {
            return;
        }
        items.put(AbsBasePo.CREATE_TIME, new Timestamp(new Date().getTime()));
        items.put(AbsBasePo.UPDATE_TIME, new Timestamp(new Date().getTime()));
        for (Map.Entry<String, Object> stringTEntry : items.entrySet()) {
            metaObject.setValue(AbsBasePo.getFieldCodeBy(stringTEntry.getKey()), stringTEntry.getValue());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Map<String, Object> items = installUpdate();
        if (null == items || items.size() <= 0) {
            return;
        }
        items.put(AbsBasePo.UPDATE_TIME, new Timestamp(new Date().getTime()));
        for (Map.Entry<String, Object> stringTEntry : items.entrySet()) {
            metaObject.setValue(AbsBasePo.getFieldCodeBy(stringTEntry.getKey()), stringTEntry.getValue());
        }
    }
}
