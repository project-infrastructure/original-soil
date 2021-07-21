package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.eo;

import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.ExceptionEnum;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 控制层异常对象
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class ControllerException extends AbsException {
    public ControllerException() {
        super(ExceptionEnum.Controller);
    }

}
