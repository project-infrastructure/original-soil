package com.laiyefei.project.original.soil.standard.spread.foundation.tools.util;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.util.IUtil;

import java.util.UUID;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-15 15:55
 * @memo : this is class named BuildUtil for do BuildUtil
 * @Version : v1.0.0.20200315
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class BuildUtil implements IUtil {
    private BuildUtil() {
        throw new RuntimeException("sorry, can not be an instance.");
    }

    public static final String BuildId() {
        return SimplifyId(UUID.randomUUID().toString());
    }

    public static final String SimplifyId(final String id) {
        if (JudgeUtil.IsNull(id)) {
            return StringUtil.EMPTY;
        }
        return id.replaceAll("-", StringUtil.EMPTY);
    }
}
