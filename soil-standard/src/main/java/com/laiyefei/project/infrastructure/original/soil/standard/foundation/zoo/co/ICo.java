package com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.co;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.IZoo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 常量对象接口约束
 * @Version : v2.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface ICo extends IZoo {

    default boolean isEnum() {
        return this.getClass().isEnum();
    }

    /**
     * 获取常量对象的编码
     *
     * @return
     */
    String getCode();

    /**
     * 获取常量名称
     *
     * @return
     */
    default String getName() {
        return this.getCode();
    }

    /**
     * 获取常量对象的描述
     *
     * @return
     */
    default String getDescription() {
        return this.getName();
    }

    static ICo BuildBy(final String code) {
        return ICo.BuildBy(code, code, code);
    }

    static ICo BuildBy(final String code, final String name) {
        return ICo.BuildBy(code, name, name);
    }

    static ICo BuildBy(final String code, final String name, final String description) {
        return new ICo() {
            @Override
            public String getCode() {
                return code;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getDescription() {
                return description;
            }
        };
    }
}
