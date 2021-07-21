package com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.bo;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.IZoo;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.dto.IDto;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.po.IPo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 业务对象接口约束
 * @Version : v2.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IBo<DTO extends IDto<? extends IBo<DTO, PO>>, PO extends IPo<? extends IBo<DTO, PO>>> extends IZoo {

    /**
     * 获取DTO对象
     *
     * @return
     */
    DTO gainDTO();

    /**
     * 获取PO对象
     *
     * @return
     */
    PO gainPO();
}
