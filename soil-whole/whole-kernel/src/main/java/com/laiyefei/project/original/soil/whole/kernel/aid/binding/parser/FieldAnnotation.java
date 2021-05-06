/*
 * Copyright (c) 2015-2020, www.dibo.ltd (service@dibo.ltd).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.laiyefei.project.original.soil.whole.kernel.aid.binding.parser;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;

import java.lang.annotation.Annotation;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 字段名与注解的包装对象关系
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class FieldAnnotation implements IAid {
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private Class<?> fieldClass;
    /**
     * 注解
     */
    private Annotation annotation;

    public FieldAnnotation(String fieldName, Class fieldClass, Annotation annotation) {
        this.fieldName = fieldName;
        this.fieldClass = fieldClass;
        this.annotation = annotation;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public Class getFieldClass() {
        return fieldClass;
    }
}