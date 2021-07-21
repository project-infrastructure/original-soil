package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.po;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.bo.IBo;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.dto.IDto;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.po.IPo;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 基础持久化对象
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@RequiredArgsConstructor
public abstract class AbsPo<BO extends IBo<? extends IDto<BO>, ? extends IPo<BO>>> implements IPo<BO>, Serializable {
    private final BO bo;

    @Override
    public BO gainBO() {
        return this.bo;
    }
}
