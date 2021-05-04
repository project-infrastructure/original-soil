package com.laiyefei.project.original.soil.standard.spread.foundation.tools.util;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.util.IUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-04-04 09:59
 * @Desc : this is class named DateUtil.
 * @Version : v1.0.0.20200404
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class DateUtil implements IUtil {
    private DateUtil() {
        throw new RuntimeException("can not be an instance.");
    }

    public static final Date GetNow() {
        return new Date();
    }

    public static final String Format(final Date date, final String formatter) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatter);
        return simpleDateFormat.format(date);
    }

    public static final String DefaultFormat(final Date date) {
        return Format(date, "yyyy-MM-dd HH:mm:ss");
    }
}
