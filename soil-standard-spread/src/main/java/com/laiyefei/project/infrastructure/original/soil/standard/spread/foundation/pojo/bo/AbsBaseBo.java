package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.bo;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.bo.IBo;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.dto.IDto;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.po.IPo;
import lombok.RequiredArgsConstructor;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-09-29 18:09
 * @Desc : 基础业务对象，充血模型
 * @Version : v2.0.0.20200929
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@RequiredArgsConstructor
public abstract class AbsBaseBo<DTO extends IDto<? extends IBo<DTO, PO>>,
        PO extends IPo<? extends IBo<DTO, PO>>> implements IBo<DTO, PO> {
    protected final DTO dto;
    protected final PO po;

    @Override
    public DTO gainDTO() {
        return this.dto;
    }

    @Override
    public PO gainPO() {
        return this.po;
    }
}
