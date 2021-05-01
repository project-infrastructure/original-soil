package com.laiyefei.project.infrastructure.original.soil.standard.central.service;


import com.laiyefei.project.infrastructure.original.soil.standard.central.ICentral;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.dto.IDto;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.po.IPo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 核心服务对象接口约束
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IService<DTO extends IDto, PO extends IPo<DTO>> extends ICentral {
}
