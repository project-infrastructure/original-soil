package com.laiyefei.project.original.soil.standard.spread.foundation.tools.util;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.util.IUtil;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-01 09:05
 * @Desc : this is class named StringUtil for do StringUtil
 * @Version : v1.0.0.20200301
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class StringUtil extends StringUtils implements IUtil {
    public static final String SPLICE = "⊕";
    public static final String SIMPLY_UUID_ZERO = "00000000000000000000000000000000";
    public static final String EMPTY = "";
    public static final String MESSAGE_EMPTY = "【暂无信息】";
    public static final String SPACE_ENGLISH = " ";
    public static final String SPACE_CHINESE = "　";
    public static final String ERROR = "[error]";
    public static final String UNKNOWN = "[UnKnown]";
    public static final String UNDER_LINE = "_";
    public static final String MIDDLE_LINE = "-";
    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String SLASH = "/";
    public static final String BACK_SLASH = "\\";

    private StringUtil() {
        throw new RuntimeException("sorry, can not be an instance.");
    }


    public static final String NDVL(final String not, String... values) {
        if (StringUtil.IsEmpty(not) || JudgeUtil.IsNull(values)) {
            return StringUtil.EMPTY;
        }
        for (final String value : values) {
            if (not.equalsIgnoreCase(value)
                    || StringUtil.IsEmpty(value)) {
                continue;
            }
            return value;
        }
        return StringUtil.EMPTY;
    }

    public static final String Trim(final String value) {
        return JudgeUtil.IsNull(value) ? StringUtil.EMPTY : value.trim();
    }

    public static final boolean IsEmpty(final String value) {
        return JudgeUtil.IsNull(value) || value.length() <= 0;
    }

    public static final boolean IsTrimEmpty(final String value) {
        return JudgeUtil.IsNull(value) || value.trim().length() <= 0;
    }

    public static final boolean IsNotTrimEmpty(final String value) {
        return !IsTrimEmpty(value);
    }

    public static final String SubString(final String value, int index) {
        if (IsEmpty(value)) {
            return StringUtil.EMPTY;
        }
        if (index < 0) {
            index = 0;
        }
        if (value.length() < index) {
            index = value.length();
        }
        return value.substring(index);
    }

    public static final String SubString(final String value, int start, int end) {
        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }
        if (value.length() < end) {
            end = value.length();
        }
        if (end < start) {
            return StringUtil.EMPTY;
        }
        return value.substring(start, end);
    }

    public static final boolean IsContainsIgnoreCase(String value, final String... singles) {
        if (IsEmpty(value)) {
            return false;
        }
        if (singles.length <= 0) {
            return false;
        }
        value = value.toLowerCase();
        for (final String single : singles) {
            if (value.contains(single.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static final boolean IsInStringIgnoreCase(String single, final String... values) {
        if (IsEmpty(single)) {
            return false;
        }
        if (values.length <= 0) {
            return false;
        }
        single = single.toLowerCase();
        for (String value : values) {
            if (value.toLowerCase().contains(single)) {
                return true;
            }
        }
        return false;
    }

    public static final String getHashKey(final String... keys) {
        if (JudgeUtil.IsNull(keys)) {
            return StringUtil.EMPTY;
        }
        if (keys.length <= 0) {
            return StringUtil.EMPTY;
        }
        for (String key : keys) {
            if (IsEmpty(key)) {
                return StringUtil.EMPTY;
            }
        }
        final List<String> keyList = Arrays.asList(keys);
        Collections.sort(keyList);
        final StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            sb.append(key);
        }
        return Md5Util.Hash(sb.toString());
    }


    public static final String ToCamelCase(final String value) {
        if (IsTrimEmpty(value)) {
            return StringUtil.EMPTY;
        }
        if (!value.contains(StringUtil.UNDER_LINE)) {
            return SubString(value, 0, 1).toUpperCase()
                    .concat(SubString(value, 1));
        }

        final StringBuilder result = new StringBuilder();
        // 切割
        final String[] camels = value.split(StringUtil.UNDER_LINE);
        for (String camel : camels) {
            if (camel.isEmpty()) {
                continue;
            }
            // 首字母大写
            result.append(SubString(camel, 0, 1).toUpperCase());
            result.append(SubString(camel, 1).toLowerCase());
        }
        return result.toString();
    }

    public static String ToUnderScoreCase(final String value) {
        if (IsTrimEmpty(value)) {
            return StringUtil.EMPTY;
        }

        final StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preBig = true;
        // 当前字符是否大写
        boolean currBig = true;
        // 下一字符是否大写
        boolean nextBig = true;
        for (int i = 0; i < value.length(); i++) {
            char item = value.charAt(i);
            if (0 < i) {
                preBig = Character.isUpperCase(value.charAt(i - 1));
            } else {
                preBig = false;
            }

            currBig = Character.isUpperCase(item);

            if (i < value.length() - 1) {
                nextBig = Character.isUpperCase(value.charAt(i + 1));
            }
            if (!nextBig && currBig) {
                boolean can = false;
                if (preBig || 0 != i) {
                    sb.append(StringUtil.UNDER_LINE);
                }
            }
            sb.append(Character.toLowerCase(item));
        }

        return sb.toString();
    }

    public static final String CleanSpecial(String from) {
        String to = from;
        Field[] fields = StringUtil.class.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                try {
                    String toDoReplace = String.valueOf(field.get(StringUtil.class));
                    if (!toDoReplace.equals(StringUtil.EMPTY)) {
                        to = to.replaceAll(BACK_SLASH.concat(toDoReplace), StringUtil.EMPTY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
        return to;
    }
}
