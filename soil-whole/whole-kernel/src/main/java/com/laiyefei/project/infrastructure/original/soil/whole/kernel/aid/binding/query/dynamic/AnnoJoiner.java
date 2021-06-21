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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.binding.query.dynamic;

import com.baomidou.mybatisplus.annotation.TableField;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.binding.parser.ParserCache;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.ao.BindQuery;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.co.Comparison;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.StringUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.Validator;
import lombok.Getter;
import lombok.Setter;

import javax.lang.model.type.NullType;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : BindQuery注解连接器
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Getter
@Setter
public class AnnoJoiner implements Serializable, IAid {
    private static final long serialVersionUID = 5998965277333389063L;

    public AnnoJoiner(Field field, BindQuery query) {
        this.fieldName = field.getName();
        this.comparison = query.comparison();
        // 列名
        if (Validator.notEmpty(query.field())) {
            this.columnName = StringUtil.toSnakeCase(query.field());
        } else if (field.isAnnotationPresent(TableField.class)) {
            this.columnName = field.getAnnotation(TableField.class).value();
        }
        if (Validator.isEmpty(this.columnName)) {
            this.columnName = StringUtil.toSnakeCase(field.getName());
        }
        // join 表名
        if (!NullType.class.equals(query.entity())) {
            this.join = ParserCache.getEntityTableName(query.entity());
        }
        // 条件
        if (Validator.notEmpty(query.condition())) {
            this.condition = query.condition();
        }
    }

    private Comparison comparison;

    private String fieldName;

    private String columnName;

    private String condition;

    private String join;
    /**
     * 别名
     */
    private String alias;
    /**
     * on条件
     */
    private String onSegment;

    /**
     * 中间表
     */
    private String middleTable;

    /**
     * 中间表别名
     */
    public String getMiddleTableAlias() {
        if (middleTable != null && alias != null) {
            return alias + "m";
        }
        return null;
    }

    /**
     * 中间表on
     */
    private String middleTableOnSegment;

    /**
     * 解析
     */
    public void parse() {
        // 解析查询
        JoinConditionManager.parseJoinCondition(this);
    }
}
