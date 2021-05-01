package com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.IPojo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 常量对象接口约束
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface ICo extends IPojo {

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
     * 获取常量对象的描述
     *
     * @return
     */
    String getDescription();

    static ICo BuildBy(final String code, final String description) {
        return new ICo() {
            @Override
            public String getCode() {
                return code;
            }

            @Override
            public String getDescription() {
                return description;
            }
        };
    }
}
