package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.dto;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.dto.IDto;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.po.IPo;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.bo.AbsBaseBo;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 基础传输对象
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@RequiredArgsConstructor
public abstract class AbsDto<BO extends AbsBaseBo<? extends IDto<? extends BO>, ? extends IPo<? extends BO>>> implements IDto<BO>, Serializable {
    private final BO bo;

    @Override
    public BO gainBO() {
        return this.bo;
    }
}
