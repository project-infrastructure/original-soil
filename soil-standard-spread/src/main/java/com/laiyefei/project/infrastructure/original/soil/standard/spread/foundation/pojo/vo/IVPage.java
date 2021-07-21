package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.vo;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.vo.IVo;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.dto.AbsDto;

import java.util.List;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 分页对象
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IVPage extends IVo {
    List<? extends AbsDto> getRecords();

    long getTotal();
}
