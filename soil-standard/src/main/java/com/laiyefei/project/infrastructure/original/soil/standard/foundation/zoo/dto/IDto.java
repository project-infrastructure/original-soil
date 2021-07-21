package com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.dto;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.IZoo;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.bo.IBo;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.po.IPo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 传输对象接口约束
 * @Version : v2.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IDto<BO extends IBo<? extends IDto<? extends BO>, ? extends IPo<? extends BO>>> extends IZoo {
    /**
     * 获取支持的业务对象
     *
     * @return
     */
    BO gainBO();
}
