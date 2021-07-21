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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.binding.parser;

import com.baomidou.mybatisplus.annotation.TableField;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.co.Constant;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.BeanUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : table的相关线索信息
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Getter
@Setter
public class TableLinkage implements Serializable {
    private static final long serialVersionUID = 4416187849283913895L;

    public TableLinkage(Class<?> entityClass, Class<?> mapperClass) {
        this.entityClass = entityClass;
        this.mapperClass = mapperClass;
        this.table = ParserCache.getEntityTableName(entityClass);
        // 初始化是否有is_deleted
        Field field = BeanUtil.extractField(entityClass, Constant.FieldName.deleted.name());
        if (field != null) {
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableField != null && tableField.exist() == true) {
                this.hasDeleted = true;
            }
        }
    }

    private String table;
    /**
     * 表对应的entity类
     */
    private Class<?> entityClass;

    /**
     * 表对应的mapper类
     */
    private Class<?> mapperClass;

    /**
     * 是否有逻辑删除字段
     */
    private boolean hasDeleted = false;

}
