package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.co.ICo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-04-07 12:52
 * @Desc : this is class named EncodeType.
 * @Version : v2.0.0.20200407
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public final class EncodeType implements ICo {
    public static final String UTF8 = "UTF-8";

    private EncodeType() {
        throw new RuntimeException("can no be an instance.");
    }

    @Override
    public String getCode() {
        throw new RuntimeException("error: can not impl code.");
    }

    @Override
    public String getDescription() {
        throw new RuntimeException("error: can not impl description.");
    }
}
