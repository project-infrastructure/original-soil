package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.util.IUtil;

import java.util.Date;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-27 23:19
 * @Desc : this is class named ObjectUtil for do ObjectUtil
 * @Version : v1.0.0.20200327
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class ObjectUtil implements IUtil {

    private ObjectUtil() {
        throw new RuntimeException("can not be an instance");
    }

    public static final <T> T GetNULL() {
        return IUtil.GetNULL();
    }

    public static final <T, R> R Cast(final T t) {
        try {
            return (R) t;
        } catch (Exception re) {
            throw new RuntimeException("类型转换出错：".concat(re.getMessage()));
        }
    }


    public static final Object DefaultValueFormat(final Object value) {
        if (JudgeUtil.IsDate(value)) {
            return DateUtil.Format((Date) value, "yyyy-MM-dd HH:mm:ss");
        }
        return value;
    }

}
