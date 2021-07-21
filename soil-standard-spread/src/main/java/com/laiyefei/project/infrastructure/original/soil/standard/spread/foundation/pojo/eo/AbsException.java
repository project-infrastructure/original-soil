package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.eo;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.co.ICo;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.eo.IEo;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.AssertUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.ExceptionEnum;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 控制层异常对象
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class AbsException extends RuntimeException implements IEo {

    private final ICo exInfo;
    private final String code;
    private final String description;

    public AbsException(ICo exInfo) {
        AssertUtil.notNull(exInfo, "error: sorry the exinfo is null.");
        this.exInfo = exInfo;
        this.code = this.exInfo.getCode();
        this.description = this.exInfo.getDescription();
    }

    public AbsException(String code, String description) {
        this.code = code;
        this.description = description;
        this.exInfo = ICo.BuildBy(this.code, this.description);
    }

    public AbsException(ExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.description = exceptionEnum.getDescription();
        this.exInfo = ICo.BuildBy(this.code, this.description);
    }


    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
