package com.laiyefei.project.original.soil.standard.spread.foundation.tools.util;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.util.IUtil;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-01 09:05
 * @Desc : this is class named JudgeUtil for do JudgeUtil
 * @Version : v1.0.0.20200301
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class JudgeUtil implements IUtil {

    private JudgeUtil() {
        throw new RuntimeException("can not be an instance.");
    }

    public static final boolean IsNull(final Object value) {
        return IUtil.IsNull(value);
    }

    public static final boolean IsNULL(final Object... values) {
        if (IsNull(values)) {
            return true;
        }
        for (final Object value : values) {
            if (!IsNull(value)) {
                return false;
            }
        }
        return true;
    }

    public static final boolean IsNotNull(final Object value) {
        return IUtil.IsNotNull(value);
    }

    public static final boolean IsNotNULL(final Object... values) {
        if (IsNull(values)) {
            return false;
        }
        for (final Object value : values) {
            if (IsNull(value)) {
                return false;
            }
        }
        return true;
    }

    public static final <T> T NVL(final T value, final T defaultValue) {
        return IUtil.NVL(value, defaultValue);
    }

    public static final boolean IsArray(final Object value) {
        return IsNotNull(value) && value.getClass().isArray();
    }

    public static final boolean IsEmpty(final Collection<?> collection) {
        return IsNull(collection) || collection.isEmpty();
    }

    public static final boolean IsNotEmpty(final Collection<?> collection) {
        return !IsEmpty(collection);
    }

    public static final boolean IsNotNULL(final UtilHolder... holders) {
        if (JudgeUtil.IsNull(holders)) {
            return false;
        }
        for (UtilHolder holder : holders) {
            if (JudgeUtil.IsNull(holder)) {
                return false;
            }
            if (JudgeUtil.IsNull(holder.export())) {
                return false;
            }
        }
        return true;
    }

    public static final boolean IsEmpty(final Object[] objects) {
        return IsNull(objects) || objects.length <= 0;
    }

    public static final boolean IsNotEmpty(final Object[] objects) {
        return !IsEmpty(objects);
    }

    public static final boolean IsEmpty(final Map<?, ?> map) {
        return IsNull(map) || map.isEmpty();
    }

    public static final boolean IsNotEmpty(final Map<?, ?> map) {
        return IsEmpty(map);
    }

    public static final boolean IsEmpty(final byte[] bytes) {
        return IsNull(bytes) || bytes.length <= 0;
    }

    public static final boolean IsDate(final Object value) {
        return value instanceof Date;
    }

    public interface UtilHolder<T> {
        T export();
    }
}
