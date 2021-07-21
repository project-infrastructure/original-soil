package com.laiyefei.project.infrastructure.original.soil.standard.foundation.supplier;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.IFoundation;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.co.ICo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 第三方对象接入接口约束
 * @Version : v2.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface ISupplier extends IFoundation {

    /**
     * 获取第三方常量描述对象
     *
     * @return
     */
    ICo gainCo();
}
