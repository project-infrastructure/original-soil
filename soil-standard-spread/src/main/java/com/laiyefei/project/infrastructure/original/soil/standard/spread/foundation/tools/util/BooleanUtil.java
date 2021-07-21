package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.utensil.util.IUtil;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-04-04 19:11
 * @Desc : this is class named BooleanUtil for do BooleanUtil
 * @Version : v2.0.0.20200404
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class BooleanUtil implements IUtil {
    public static final Boolean EMPTY = false;

    private BooleanUtil() {
        throw new RuntimeException("can no be an instance.");
    }


    public static final Boolean IsEmpty(final Boolean value) {
        if (JudgeUtil.IsNull(value)) {
            return true;
        }
        return BooleanUtil.EMPTY.equals(value);
    }

    public static final Boolean IsNotEmpty(final Boolean value) {
        return !BooleanUtil.IsEmpty(value);
    }

    public static final Boolean IsTrue(final Boolean... booleans) {
        if (JudgeUtil.IsEmpty(booleans)) {
            return false;
        }
        for (final Boolean aBoolean : booleans) {
            if (!aBoolean) {
                return false;
            }
        }
        return true;
    }

    public static final Boolean IsFalse(final Boolean... booleans) {
        if (JudgeUtil.IsNull(booleans)) {
            return true;
        }
        for (final Boolean aBoolean : booleans) {
            if (aBoolean) {
                return false;
            }
        }
        return true;
    }
}
