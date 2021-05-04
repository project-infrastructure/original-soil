package com.laiyefei.project.original.soil.standard.spread.foundation.pojo.co;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co.ICo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-01 09:05
 * @Desc : this is class named OSType for do OSType
 * @Version : v1.0.0.20200301
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public enum OSType implements ICo {
    WINDOWS("windows", "windows操作系统"),
    LINUX("linux", "linux操作系统");

    public static final OSType findBy(final String code) {
        if (null == code) {
            throw new RuntimeException("sorry, the code for find ostype is can not be [null].");
        }
        final String findCode = code.toLowerCase();
        for (OSType item : OSType.values()) {
            if (item.getCode().contains(findCode)) {
                return item;
            }
        }
        throw new RuntimeException("sorry, can not be find code in ostype.");
    }

    private final String code;
    private final String description;

    OSType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
