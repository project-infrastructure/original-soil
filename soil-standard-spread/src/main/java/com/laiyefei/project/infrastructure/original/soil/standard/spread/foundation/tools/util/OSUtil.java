package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.util.IUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.OSType;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.CMD;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.Properties;

import java.io.IOException;


/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-01 09:05
 * @Desc : this is class named OSUtil for do OSUtil
 * @Version : v1.0.0.20200301
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class OSUtil implements IUtil {

    private OSUtil() {
        throw new RuntimeException("sorry, util is can not be an instance.");
    }

    //region 操作系统类型
    private static final String osNameLower = System.getProperty(Properties.OsName.getCode());

    public static final OSType getCurrentOSType() {
        return OSType.findBy(osNameLower);
    }

    public static final boolean isCurrentWin() {
        return getCurrentOSType().equals(OSType.WINDOWS);
    }

    public static final boolean isCurrentLinux() {
        return getCurrentOSType().equals(OSType.LINUX);
    }
    //endregion

    //region CMD 的操作
    public static final void runCommand(final String cmdPath) {
        try {
            Runtime.getRuntime().exec(CMD.buildCommand(cmdPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion
}
