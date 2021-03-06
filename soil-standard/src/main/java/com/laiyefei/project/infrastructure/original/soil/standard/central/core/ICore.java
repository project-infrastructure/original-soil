package com.laiyefei.project.infrastructure.original.soil.standard.central.core;


import com.laiyefei.project.infrastructure.original.soil.standard.central.service.IService;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.dto.IDto;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.po.IPo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 核心对象接口约束
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface ICore<DTO extends IDto, PO extends IPo<DTO>> extends IService<DTO, PO> {
}
