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


import com.laiyefei.project.original.soil.whole.kernel.pojo.ao.*;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : VO绑定注解的归类分组，用于缓存解析后的结果
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class BindAnnotationGroup {
    /**
     * Dictionary注解
     */
    private List<FieldAnnotation> bindDictAnnotations;
    /**
     * 字段关联注解
     */
    private List<FieldAnnotation> bindFieldAnnotations;
    /**
     * 实体关联注解
     */
    private List<FieldAnnotation> bindEntityAnnotations;
    /**
     * 实体集合关联注解
     */
    private List<FieldAnnotation> bindEntityListAnnotations;
    /**
     * 实体集合关联注解
     */
    private List<FieldAnnotation> bindFieldListAnnotations;

    /**
     * 添加注解
     *
     * @param fieldName
     * @param annotation
     */
    public void addBindAnnotation(String fieldName, Class<?> fieldClass, Annotation annotation) {
        if (annotation instanceof BindDict) {
            if (bindDictAnnotations == null) {
                bindDictAnnotations = new ArrayList<>();
            }
            bindDictAnnotations.add(new FieldAnnotation(fieldName, fieldClass, annotation));
        } else if (annotation instanceof BindField) {
            if (bindFieldAnnotations == null) {
                bindFieldAnnotations = new ArrayList<>();
            }
            bindFieldAnnotations.add(new FieldAnnotation(fieldName, fieldClass, annotation));
        } else if (annotation instanceof BindPo) {
            if (bindEntityAnnotations == null) {
                bindEntityAnnotations = new ArrayList<>();
            }
            bindEntityAnnotations.add(new FieldAnnotation(fieldName, fieldClass, annotation));
        } else if (annotation instanceof BindPoList) {
            if (bindEntityListAnnotations == null) {
                bindEntityListAnnotations = new ArrayList<>();
            }
            bindEntityListAnnotations.add(new FieldAnnotation(fieldName, fieldClass, annotation));
        } else if (annotation instanceof BindFieldList) {
            if (bindFieldListAnnotations == null) {
                bindFieldListAnnotations = new ArrayList<>();
            }
            bindFieldListAnnotations.add(new FieldAnnotation(fieldName, fieldClass, annotation));
        }
    }

    public List<FieldAnnotation> getBindDictAnnotations() {
        return bindDictAnnotations;
    }

    public List<FieldAnnotation> getBindFieldAnnotations() {
        return bindFieldAnnotations;
    }

    public List<FieldAnnotation> getBindEntityAnnotations() {
        return bindEntityAnnotations;
    }

    public List<FieldAnnotation> getBindEntityListAnnotations() {
        return bindEntityListAnnotations;
    }

    public List<FieldAnnotation> getBindFieldListAnnotations() {
        return bindFieldListAnnotations;
    }

    public boolean isNotEmpty() {
        return bindDictAnnotations != null || bindFieldAnnotations != null || bindEntityAnnotations != null || bindEntityListAnnotations != null || bindFieldListAnnotations != null;
    }

}
