package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.co;


import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.fo.IHolderHandler;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co.ICo;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.pojo.po.AbsBasePo;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.dto.AbsDto;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 带初始化行为的常量对象接口约束
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IInitCo<PO extends AbsBasePo<? extends AbsDto>> extends ICo {
    void doInit(IHolderHandler.IHandler<PO, PO> handler);
}
