package com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.util;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.ITools;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 帮助工具接口约束
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IUtil extends ITools {
    static <T> T GetNULL() {
        return null;
    }

    static boolean IsNull(final Object value) {
        return null == value;
    }

    static boolean IsNotNull(final Object value) {
        return !IsNull(value);
    }

    static <T> T NVL(final T value, final T defaultValue) {
        return IsNull(value) ? defaultValue : value;
    }
}
