package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.utensil.util.IUtil;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-04-04 19:11
 * @Desc : this is class named LongUtil for do LongUtil
 * @Version : v2.0.0.20200404
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class LongUtil implements IUtil {
    public static final Long EMPTY = 0L;
    public static final Long ZERO = EMPTY;
    public static final Long ONE = 1L;

    private LongUtil() {
        throw new RuntimeException("can no be an instance.");
    }

}
