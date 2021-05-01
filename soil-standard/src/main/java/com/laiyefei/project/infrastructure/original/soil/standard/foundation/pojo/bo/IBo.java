package com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.bo;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.IPojo;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.dto.IDto;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.po.IPo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 业务对象接口约束
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IBo<PO extends IPo<? extends IDto>> extends IPojo {

    /**
     * 获取PO对象
     *
     * @return
     */
    PO gainPO();
}
