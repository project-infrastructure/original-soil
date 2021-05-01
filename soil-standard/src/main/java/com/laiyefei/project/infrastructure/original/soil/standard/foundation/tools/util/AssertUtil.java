package com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.util;


/**
 * @Author : leaf.fly(?)
 * @Create : 2020-09-04 14:09
 * @Desc : 断言工具
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class AssertUtil implements IUtil {
    public static void notNull(Object object, String message) {
        if (null == object) {
            throw new IllegalArgumentException(message);
        }
    }
}
