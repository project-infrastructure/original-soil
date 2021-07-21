package com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.dto;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.IZoo;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.po.IPo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 传输对象接口约束
 * @Version : v2.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IDto<PO extends IPo<? extends IDto<PO>>> extends IZoo {

    /**
     * 建造持久对象
     *
     * @return
     */
    PO buildPo();


    /**
     * po 转换成 dto
     *
     * @param po
     * @param <PO>
     * @return
     */
    static <PO extends IPo<? extends IDto<PO>>> IDto<PO> SwitchAs(final PO po) {
        return po.buildDto();
    }

    /**
     * po 转换成 dto
     *
     * @param pos
     * @param <PO>
     * @return
     */
    static <PO extends IPo<? extends IDto<PO>>> List<IDto<PO>> SwitchAs(final List<PO> pos) {
        if (null == pos) {
            return new ArrayList<>();
        }
        return pos.stream().map(e -> e.buildDto()).collect(Collectors.toList());
    }


}
