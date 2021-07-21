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

import com.baomidou.mybatisplus.extension.service.IService;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.ISetter;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.BeanUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.StringUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-10-02 18:09
 * @Desc : Entity实体绑定Binder，用于绑定当前一个entity到目标对象的属性
 * @Version : v2.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class EntityBinder<T> extends BaseBinder<T> {
    private static final Logger log = LoggerFactory.getLogger(EntityBinder.class);

    /***
     * 给待绑定list中VO对象赋值的setter属性名
     */
    protected String annoObjectField;
    /***
     * 给待绑定list中VO对象赋值的setter属性class类型
     */
    protected Class<?> annoObjectFieldClass;

    /***
     * 构造方法
     * @param referencedService
     * @param voList
     */
    public EntityBinder(IService<T> referencedService, List voList) {
        super(referencedService, voList);
    }

    /***
     * 指定VO绑定属性赋值的setter方法
     * @param voSetter VO中调用赋值的setter方法
     * @param <T1> VO类型
     * @param <R> set方法参数类型
     * @return
     */
    public <T1, R> BaseBinder<T> set(ISetter<T1, R> voSetter, Class annoObjectFieldClass) {
        return set(BeanUtil.convertToFieldName(voSetter), annoObjectFieldClass);
    }

    /***
     * 指定VO绑定属性赋值的set属性
     * @param annoObjectField VO中调用赋值的setter属性
     * @return
     */
    public BaseBinder<T> set(String annoObjectField, Class annoObjectFieldClass) {
        this.annoObjectField = annoObjectField;
        this.annoObjectFieldClass = annoObjectFieldClass;
        return this;
    }

    @Override
    public void bind() {
        if (Validator.isEmpty(annoObjectList)) {
            return;
        }
        if (referencedEntityPrimaryKey == null) {
            log.warn("调用错误：无法从condition中解析出字段关联.");
        }
        // 提取注解条件中指定的对应的列表
        String annoObjectForeignKeyField = StringUtil.toLowerCaseCamel(annoObjectForeignKey);
        List annoObjectForeignKeyList = BeanUtil.collectToList(annoObjectList, annoObjectForeignKeyField);
        if (Validator.isEmpty(annoObjectForeignKeyList)) {
            return;
        }
        // 结果转换Map
        Map<String, Object> valueEntityMap = new HashMap<>();
        // 通过中间表关联Entity
        // @BindEntity(entity = Organization.class, condition = "this.department_id=department.id AND department.org_id=id AND department.level=1")
        // Organization organization;
        if (middleTable != null) {
            Map<String, Object> middleTableResultMap = middleTable.executeOneToOneQuery(annoObjectForeignKeyList);
            if (Validator.notEmpty(middleTableResultMap)) {
                // 提取entity主键值集合
                Collection middleTableColumnValueList = middleTableResultMap.values();
                // 构建查询条件
                queryWrapper.in(StringUtil.toSnakeCase(referencedEntityPrimaryKey), middleTableColumnValueList);
                // 查询entity列表
                List<T> list = getEntityList(queryWrapper);
                if (Validator.notEmpty(list)) {
                    // 转换entity列表为Map<ID, Entity>
                    Map<String, T> listMap = BeanUtil.convertToStringKeyObjectMap(list, StringUtil.toLowerCaseCamel(referencedEntityPrimaryKey));
                    if (Validator.notEmpty(listMap)) {
                        for (Map.Entry<String, Object> entry : middleTableResultMap.entrySet()) {
                            Object fetchValueId = entry.getValue();
                            if (fetchValueId == null) {
                                continue;
                            }
                            String key = entry.getKey();
                            T entity = listMap.get(String.valueOf(fetchValueId));
                            valueEntityMap.put(key, cloneOrConvertBean(entity));
                        }
                    }
                }
            }
        }
        // 直接关联Entity
        // @BindEntity(entity = Department.class, condition="department_id=id")
        // Department department;
        else {
            // 构建查询条件
            queryWrapper.in(StringUtil.toSnakeCase(referencedEntityPrimaryKey), annoObjectForeignKeyList);
            // 查询entity列表
            List<T> list = getEntityList(queryWrapper);
            if (Validator.notEmpty(list)) {
                String refEntityPKFieldName = StringUtil.toLowerCaseCamel(referencedEntityPrimaryKey);
                for (T entity : list) {
                    String pkValue = BeanUtil.getStringProperty(entity, refEntityPKFieldName);
                    valueEntityMap.put(pkValue, cloneOrConvertBean(entity));
                }
            }
        }
        // 绑定结果
        BeanUtil.bindPropValueOfList(annoObjectField, annoObjectList, annoObjectForeignKey, valueEntityMap);
    }

    /**
     * 克隆Entity/VO对象（如果与Entity类型不一致，如VO则先转型）
     *
     * @param value
     */
    protected Object cloneOrConvertBean(T value) {
        if (value == null) {
            return value;
        }
        if (value.getClass().getName().equals(annoObjectFieldClass.getName())) {
            return BeanUtil.cloneBean(value);
        } else {
            return BeanUtil.convert(value, annoObjectFieldClass);
        }
    }
}
