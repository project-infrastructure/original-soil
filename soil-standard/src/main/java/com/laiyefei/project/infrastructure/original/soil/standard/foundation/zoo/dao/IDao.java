package com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.dao;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.IZoo;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.dto.IDto;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.po.IPo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 持久层对象接口约束
 * @Version : v2.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IDao<PO extends IPo<? extends IDto<PO>>> extends IZoo {

}
