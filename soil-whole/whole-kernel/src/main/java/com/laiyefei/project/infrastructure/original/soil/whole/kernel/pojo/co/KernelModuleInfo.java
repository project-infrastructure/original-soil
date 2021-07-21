package com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.co;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.co.ICo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 模块信息
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@RequiredArgsConstructor
@Getter
public enum KernelModuleInfo implements ICo {
    ;
    public static final String BASE_PACKAGE = "com.laiyefei.project.original.soil.kernel";
    public static final String DAO_PACKAGE = "com.laiyefei.project.original.soil.kernel.pojo.dao";
    private final String code;
    private final String description;
}
