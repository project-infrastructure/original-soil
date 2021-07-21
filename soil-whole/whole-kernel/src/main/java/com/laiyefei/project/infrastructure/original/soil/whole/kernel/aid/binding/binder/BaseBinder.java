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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.binding.binder;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.binding.parser.MiddleTable;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.prepper.SystemConfig;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.IGetter;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.service.BaseService;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.BeanUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.StringUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-10-02 18:09
 * @Desc : 关系绑定Binder父类
 * @Version : v2.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class BaseBinder<T> {
    private static final Logger log = LoggerFactory.getLogger(BaseBinder.class);
    /***
     * 需要绑定到的VO注解对象List
     */
    protected List annoObjectList;
    /***
     * VO注解对象中的外键属性
     */
    protected String annoObjectForeignKey;
    /**
     * 被关联对象的Service实例
     */
    protected IService<T> referencedService;
    /***
     * DO对象中的主键属性名
     */
    protected String referencedEntityPrimaryKey;
    /**
     * 初始化QueryWrapper
     */
    protected QueryWrapper queryWrapper;

    /**
     * 多对多关联的桥接表，如 user_role<br>
     * 多对多注解示例: id=user_role.user_id AND user_role.role_id=id
     */
    protected MiddleTable middleTable;

    protected Class<T> referencedEntityClass;

    /***
     * 构造方法
     * @param serviceInstance
     * @param voList
     */
    public BaseBinder(IService<T> serviceInstance, List voList) {
        this.referencedService = serviceInstance;
        this.annoObjectList = voList;
        this.queryWrapper = new QueryWrapper<T>();
        this.referencedEntityClass = BeanUtil.getGenericityClass(referencedService, 1);
    }

    /**
     * join连接条件，指定当前VO的取值方法和关联entity的取值方法
     *
     * @param annoObjectFkGetter       当前VO的取值方法
     * @param referencedEntityPkGetter 关联entity的取值方法
     * @param <T1>                     当前VO的对象类型
     * @param <T2>                     关联对象entity类型
     * @return
     */
    public <T1, T2> BaseBinder<T> joinOn(IGetter<T1> annoObjectFkGetter, IGetter<T2> referencedEntityPkGetter) {
        return joinOn(BeanUtil.convertToFieldName(annoObjectFkGetter), BeanUtil.convertToFieldName(referencedEntityPkGetter));
    }

    /**
     * join连接条件，指定当前VO的取值方法和关联entity的取值方法
     *
     * @param annoObjectForeignKey       当前VO的取值属性名
     * @param referencedEntityPrimaryKey 关联entity的属性
     * @return
     */
    public BaseBinder<T> joinOn(String annoObjectForeignKey, String referencedEntityPrimaryKey) {
        this.annoObjectForeignKey = StringUtil.toLowerCaseCamel(annoObjectForeignKey);
        this.referencedEntityPrimaryKey = StringUtil.toLowerCaseCamel(referencedEntityPrimaryKey);
        return this;
    }

    public BaseBinder<T> andEQ(String fieldName, Object value) {
        queryWrapper.eq(StringUtil.toSnakeCase(fieldName), formatValue(fieldName, value));
        return this;
    }

    public BaseBinder<T> andNE(String fieldName, Object value) {
        queryWrapper.ne(StringUtil.toSnakeCase(fieldName), formatValue(fieldName, value));
        return this;
    }

    public BaseBinder<T> andGT(String fieldName, Object value) {
        queryWrapper.gt(StringUtil.toSnakeCase(fieldName), formatValue(fieldName, value));
        return this;
    }

    public BaseBinder<T> andGE(String fieldName, Object value) {
        queryWrapper.ge(StringUtil.toSnakeCase(fieldName), formatValue(fieldName, value));
        return this;
    }

    public BaseBinder<T> andLT(String fieldName, Object value) {
        queryWrapper.lt(StringUtil.toSnakeCase(fieldName), formatValue(fieldName, value));
        return this;
    }

    public BaseBinder<T> andLE(String fieldName, Object value) {
        queryWrapper.le(StringUtil.toSnakeCase(fieldName), formatValue(fieldName, value));
        return this;
    }

    public BaseBinder<T> andIsNotNull(String fieldName) {
        queryWrapper.isNotNull(StringUtil.toSnakeCase(fieldName));
        return this;
    }

    public BaseBinder<T> andIsNull(String fieldName) {
        queryWrapper.isNull(StringUtil.toSnakeCase(fieldName));
        return this;
    }

    public BaseBinder<T> andBetween(String fieldName, Object begin, Object end) {
        queryWrapper.between(StringUtil.toSnakeCase(fieldName), formatValue(fieldName, begin), formatValue(fieldName, end));
        return this;
    }

    public BaseBinder<T> andLike(String fieldName, String value) {
        queryWrapper.like(StringUtil.toSnakeCase(fieldName), formatValue(fieldName, value));
        return this;
    }

    public BaseBinder<T> andIn(String fieldName, Collection valueList) {
        queryWrapper.in(StringUtil.toSnakeCase(fieldName), valueList);
        return this;
    }

    public BaseBinder<T> andNotIn(String fieldName, Collection valueList) {
        queryWrapper.notIn(StringUtil.toSnakeCase(fieldName), valueList);
        return this;
    }

    public BaseBinder<T> andNotBetween(String fieldName, Object begin, Object end) {
        queryWrapper.notBetween(StringUtil.toSnakeCase(fieldName), formatValue(fieldName, begin), formatValue(fieldName, end));
        return this;
    }

    public BaseBinder<T> andNotLike(String fieldName, String value) {
        queryWrapper.notLike(StringUtil.toSnakeCase(fieldName), formatValue(fieldName, value));
        return this;
    }

    public BaseBinder<T> andApply(String applySql) {
        queryWrapper.apply(applySql);
        return this;
    }

    public BaseBinder<T> withMiddleTable(MiddleTable middleTable) {
        this.middleTable = middleTable;
        return this;
    }

    /***
     * 执行绑定, 交由子类实现
     */
    public abstract void bind();

    /**
     * 获取EntityList
     *
     * @param queryWrapper
     * @return
     */
    protected List<T> getEntityList(Wrapper queryWrapper) {
        if (referencedService instanceof BaseService) {
            return ((BaseService) referencedService).getEntityList(queryWrapper);
        } else {
            List<T> list = referencedService.list(queryWrapper);
            return checkedList(list);
        }
    }

    /**
     * 获取Map结果
     *
     * @param queryWrapper
     * @return
     */
    protected List<Map<String, Object>> getMapList(Wrapper queryWrapper) {
        if (referencedService instanceof BaseService) {
            return ((BaseService) referencedService).getMapList(queryWrapper);
        } else {
            List<Map<String, Object>> list = referencedService.listMaps(queryWrapper);
            return checkedList(list);
        }
    }

    /**
     * 从map中取值，如直接取为null尝试转换大写后再取，以支持ORACLE等大写命名数据库
     *
     * @param map
     * @param key
     * @return
     */
    protected Object getValueIgnoreKeyCase(Map<String, Object> map, String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        if (map.containsKey(key.toUpperCase())) {
            return map.get(key.toUpperCase());
        }
        return null;
    }

    /**
     * 从Map中提取ID的值
     *
     * @param middleTableResultMap
     * @return
     */
    protected List extractIdValueFromMap(Map<String, List> middleTableResultMap) {
        List entityIdList = new ArrayList();
        for (Map.Entry<String, List> entry : middleTableResultMap.entrySet()) {
            if (Validator.isEmpty(entry.getValue())) {
                continue;
            }
            for (Object id : entry.getValue()) {
                if (!entityIdList.contains(id)) {
                    entityIdList.add(id);
                }
            }
        }
        return entityIdList;
    }

    /**
     * 检查list，结果过多打印warn
     *
     * @param list
     * @return
     */
    private List checkedList(List list) {
        if (list == null) {
            list = Collections.emptyList();
        } else if (list.size() > SystemConfig.getBatchSize()) {
            log.warn("单次查询记录数量过大，返回结果数={}，请检查！", list.size());
        }
        return list;
    }

    /**
     * 格式化条件值
     *
     * @param fieldName 属性名
     * @param value     值
     * @return
     */
    private Object formatValue(String fieldName, Object value) {
        if (value instanceof String && StringUtil.contains((String) value, "'")) {
            return StringUtil.replace((String) value, "'", "");
        }
        // 转型
        if (this.referencedEntityClass != null) {
            Field field = BeanUtil.extractField(this.referencedEntityClass, StringUtil.toLowerCaseCamel(fieldName));
            if (field != null) {
                return BeanUtil.convertValueToFieldType(value, field);
            }
        }
        return value;
    }

}