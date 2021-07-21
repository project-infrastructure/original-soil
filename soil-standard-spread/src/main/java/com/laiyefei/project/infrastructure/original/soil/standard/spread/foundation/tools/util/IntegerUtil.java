package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.utensil.util.IUtil;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-04-04 19:11
 * @Desc : this is class named IntegerUtil for do IntegerUtil
 * @Version : v2.0.0.20200404
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class IntegerUtil implements IUtil {
    public static final Integer EMPTY = 0;
    public static final Integer ZERO = EMPTY;
    public static final Integer ONE = 1;

    private IntegerUtil() {
        throw new RuntimeException("can no be an instance.");
    }

}
