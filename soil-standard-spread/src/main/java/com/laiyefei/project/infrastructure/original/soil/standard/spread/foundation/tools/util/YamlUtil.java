package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.utensil.util.IUtil;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-01 09:05
 * @Desc : this is class named YamlUtil for do YamlUtil
 * @Version : v2.0.0.20200301
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class YamlUtil implements IUtil {
    private static final String[] RESOURCE_TYPES = {".yml", ".yaml"};

    private YamlUtil() {
        throw new RuntimeException("error: can not be an instance.");
    }

    public static final boolean isYML(final String fileName) {
        if (JudgeUtil.IsNull(fileName)) {
            throw new RuntimeException("error: filename is null");
        }
        for (final String resourceType : RESOURCE_TYPES) {
            if (fileName.endsWith(resourceType)) {
                return true;
            }
        }
        return false;
    }

}
