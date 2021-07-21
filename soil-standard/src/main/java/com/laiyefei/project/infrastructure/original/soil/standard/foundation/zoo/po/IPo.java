package com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.po;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.IZoo;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.bo.IBo;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.dto.IDto;


/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 持久化对象接口约束
 * @Version : v2.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IPo<BO extends IBo<? extends IDto<BO>, ? extends IPo<BO>>> extends IZoo {
    /**
     * 获取支持的业务对象
     *
     * @return
     */
    BO gainBO();
}
