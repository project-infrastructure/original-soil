package com.laiyefei.project.original.soil.die.yard.foundation.pojo.fo;

import com.laiyefei.project.original.soil.dependencies.ssmp.foundation.pojo.po.AbsBasePo;
import com.laiyefei.project.original.soil.standard.spread.foundation.pojo.dto.AbsDto;
import com.laiyefei.project.original.soil.standard.spread.foundation.pojo.fo.IHolder;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 持有者
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IHolderHandler extends IHolder {

    @FunctionalInterface
    interface IHandler<T1 extends AbsBasePo<? extends AbsDto>, T2 extends AbsBasePo<? extends AbsDto>> {
        boolean done(T1 t1, T2 t2);
    }

}
