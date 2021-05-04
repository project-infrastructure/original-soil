package com.laiyefei.project.original.soil.standard.spread.foundation.pojo.eo;

import com.laiyefei.project.original.soil.standard.spread.foundation.pojo.co.ExceptionEnum;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 控制层异常对象
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class OtherException extends AbsException {
    public OtherException(String code, String description) {
        super(code, description);
    }

    public OtherException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum);
    }
}
