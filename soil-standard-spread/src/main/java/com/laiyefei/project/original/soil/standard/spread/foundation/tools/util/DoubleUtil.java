package com.laiyefei.project.original.soil.standard.spread.foundation.tools.util;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.util.IUtil;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-04-04 19:11
 * @Desc : this is class named DoubleUtil for do DoubleUtil
 * @Version : v1.0.0.20200404
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class DoubleUtil implements IUtil {
    public static final Double EMPTY = 0d;

    private DoubleUtil() {
        throw new RuntimeException("cna no be an instance.");
    }
}
