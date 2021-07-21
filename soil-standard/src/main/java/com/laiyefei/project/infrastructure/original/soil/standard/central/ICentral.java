package com.laiyefei.project.infrastructure.original.soil.standard.central;


import com.laiyefei.project.infrastructure.original.soil.standard.IStandard;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.bo.IBo;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.dto.IDto;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.po.IPo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-09-26 18:09
 * @Desc : 核心对象接口约束
 * @Version : v2.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface ICentral<BO extends IBo<? extends IDto<?>, ? extends IPo<?>>> extends IStandard {
    /**
     * 获取业务对象
     *
     * @return
     */
    BO gainBO();
}
