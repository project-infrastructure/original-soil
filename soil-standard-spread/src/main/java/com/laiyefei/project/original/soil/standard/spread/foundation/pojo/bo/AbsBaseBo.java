package com.laiyefei.project.original.soil.standard.spread.foundation.pojo.bo;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.bo.IBo;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.dto.IDto;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.po.IPo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-09-29 18:09
 * @Desc : 基础业务对象，充血模型
 * @Version : v1.0.0.20200929
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class AbsBaseBo<PO extends IPo<? extends IDto>> implements IBo<PO> {

    protected final PO po;

    public AbsBaseBo(PO po) {
        this.po = po;
    }

    @Override
    public PO gainPO() {
        return this.po;
    }
}
