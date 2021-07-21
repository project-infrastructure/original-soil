package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.utensil.util.IUtil;
import org.springframework.util.Assert;

import java.security.MessageDigest;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-01 09:05
 * @Desc : this is class named Md5Util for do Md5Util
 * @Version : v2.0.0.20200301
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class Md5Util implements IUtil {
    private static final String ALGORITHM = "MD5";
    private static final String CHARSET_NAME = "UTF-8";

    private Md5Util() {
        throw new RuntimeException("sorry, can not be an instance.");
    }


    private static final byte[] Md5(String s) {
        try {
            final MessageDigest algorithm = MessageDigest.getInstance(Md5Util.ALGORITHM);
            algorithm.reset();
            algorithm.update(s.getBytes(Md5Util.CHARSET_NAME));
            return algorithm.digest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final String ToHex(byte[] hash) {
        if (JudgeUtil.IsEmpty(hash)) {
            return StringUtil.EMPTY;
        }
        final StringBuffer buf = new StringBuffer(hash.length * 2);
        for (int i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String Hash(String s) {
        Assert.notNull(s, "error: sorry, the first param is can not be null.");
        try {
            return new String(ToHex(Md5(s)).getBytes(Md5Util.CHARSET_NAME), Md5Util.CHARSET_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String Hash(String s, String salt) {
        Assert.notNull(s, "error: sorry, the first param is can not be null.");
        Assert.notNull(salt, "error: sorry, the second param is can not be null.");
        return Hash(s.concat(salt));
    }
}
