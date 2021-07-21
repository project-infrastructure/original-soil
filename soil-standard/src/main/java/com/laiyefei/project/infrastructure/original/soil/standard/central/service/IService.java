package com.laiyefei.project.infrastructure.original.soil.standard.central.service;


import com.laiyefei.project.infrastructure.original.soil.standard.central.ICentral;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.bo.IBo;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.dto.IDto;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.po.IPo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 核心服务对象接口约束
 * @Version : v2.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IService<BO extends IBo<? extends IDto<? extends IPo<?>>, ? extends IPo<? extends IDto<?>>>> extends ICentral<BO> {
}
