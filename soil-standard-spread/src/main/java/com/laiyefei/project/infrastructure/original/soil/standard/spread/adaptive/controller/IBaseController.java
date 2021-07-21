package com.laiyefei.project.infrastructure.original.soil.standard.spread.adaptive.controller;


import com.laiyefei.project.infrastructure.original.soil.standard.adaptive.controller.IController;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.dto.AbsDto;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.fo.IHolder;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.vo.IVPage;

import java.util.List;
import java.util.function.Supplier;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 防腐层控制层接口约束
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IBaseController<DTO extends AbsDto> extends IController {

    IHolder.IExecuteOk addFunction(DTO dto);

    IHolder.IExecuteOk deleteFunction(String id);

    Supplier<DTO> getFunction(String id);

    Supplier<List<DTO>> listFunction(DTO condition);

    Supplier<? extends IVPage> pageFunction(Integer index, Integer size, DTO condition);

    IHolder.IExecuteOk updateFunction(DTO dto);
}
