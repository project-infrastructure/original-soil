package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.utensil.util.IUtil;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-05-10 11:24
 * @Desc : this is class named ProjectUtil.
 * @Version : v2.0.0.20200510
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class ProjectUtil implements IUtil {
    public static final String[] SPECIAL_SIGNS = {"-", "/", "_", "\\.", "=", ">", "<", "\\*"};

    public static String FormatProjectCode(String src) {
        final StringBuilder sb = new StringBuilder();
        for (final String specialSign : SPECIAL_SIGNS) {
            src = src.replaceAll(specialSign, StringUtil.SPLICE);
        }
        final String[] srcs = src.split(StringUtil.SPLICE);
        for (final String s : srcs) {
            sb.append(StringUtil.ToCamelCase(s));
        }
        return sb.toString();
    }
}
