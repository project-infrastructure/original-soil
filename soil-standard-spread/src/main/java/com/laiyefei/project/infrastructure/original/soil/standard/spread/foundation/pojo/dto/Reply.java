package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.dto;

import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.bo.AbsBaseBo;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.po.AbsPo;
import lombok.Getter;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 基础传输对象
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Getter
public abstract class Reply<BO extends AbsBaseBo<? extends AbsDto<? extends BO>, ? extends AbsPo<? extends BO>>, RESULT extends AbsDto<? extends BO>> extends AbsDto<BO> {
    protected final boolean ok;
    protected final String code;
    protected final String name;
    protected RESULT result;

    public Reply(final BO bo,
                 final boolean ok,
                 final String code,
                 final String name) {
        this(bo, ok, code, name, null);
    }

    public Reply(final BO bo,
                 final boolean ok,
                 final String code,
                 final String name,
                 final RESULT result) {
        super(bo);
        this.ok = ok;
        this.code = code;
        this.name = name;
        this.result = result;
    }
}
