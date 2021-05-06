package com.laiyefei.project.original.soil.dependencies.ssmp.foundation.pojo.bo;

import com.laiyefei.project.original.soil.dependencies.ssmp.foundation.pojo.po.AbsBasePo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 基本业务对象
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class AbsBaseBo extends AbsBasePo {

    private final AbsBasePo basePo;

    protected AbsBaseBo(AbsBasePo basePo) {
        this.basePo = basePo;
    }
}
