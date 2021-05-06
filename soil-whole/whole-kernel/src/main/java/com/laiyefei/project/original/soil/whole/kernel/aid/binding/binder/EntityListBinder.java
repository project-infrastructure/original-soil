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
package com.laiyefei.project.original.soil.whole.kernel.aid.binding.binder;

import com.baomidou.mybatisplus.extension.service.IService;
import com.laiyefei.project.original.soil.whole.kernel.tools.util.BeanUtil;
import com.laiyefei.project.original.soil.whole.kernel.tools.util.StringUtil;
import com.laiyefei.project.original.soil.whole.kernel.tools.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-10-02 18:09
 * @Desc : Entity集合绑定实现
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class EntityListBinder<T> extends EntityBinder<T> {
    private static final Logger log = LoggerFactory.getLogger(EntityListBinder.class);

    /***
     * 构造方法
     * @param serviceInstance
     * @param voList
     */
    public EntityListBinder(IService<T> serviceInstance, List voList) {
        super(serviceInstance, voList);
    }

    @Override
    public void bind() {
        if (Validator.isEmpty(annoObjectList)) {
            return;
        }
        if (referencedEntityPrimaryKey == null) {
            log.warn("调用错误：无法从condition中解析出字段关联.");
        }
        // 提取主键pk列表
        String annoObjectForeignKeyField = StringUtil.toLowerCaseCamel(annoObjectForeignKey);
        List annoObjectForeignKeyList = BeanUtil.collectToList(annoObjectList, annoObjectForeignKeyField);
        if (Validator.isEmpty(annoObjectForeignKeyList)) {
            return;
        }
        Map<String, List> valueEntityListMap = new HashMap<>();
        // 解析中间表查询 1-N关联，如：
        //User.class @BindEntityList(entity = Role.class, condition="this.id=user_role.user_id AND user_role.role_id=id")
        if (middleTable != null) {
            Map<String, List> middleTableResultMap = middleTable.executeOneToManyQuery(annoObjectForeignKeyList);
            if (Validator.notEmpty(middleTableResultMap)) {
                // 收集查询结果values集合
                List entityIdList = extractIdValueFromMap(middleTableResultMap);
                // 构建查询条件
                queryWrapper.in(StringUtil.toSnakeCase(referencedEntityPrimaryKey), entityIdList);
                // 查询entity列表: List<Role>
                List list = getEntityList(queryWrapper);
                if (Validator.notEmpty(list)) {
                    // 转换entity列表为Map<ID, Entity>
                    Map<String, T> entityMap = BeanUtil.convertToStringKeyObjectMap(list, StringUtil.toLowerCaseCamel(referencedEntityPrimaryKey));
                    for (Map.Entry<String, List> entry : middleTableResultMap.entrySet()) {
                        // List<roleId>
                        List annoObjFKList = entry.getValue();
                        if (Validator.isEmpty(annoObjFKList)) {
                            continue;
                        }
                        List valueList = new ArrayList();
                        for (Object obj : annoObjFKList) {
                            T ent = entityMap.get(String.valueOf(obj));
                            if (ent != null) {
                                valueList.add(cloneOrConvertBean(ent));
                            }
                        }
                        valueEntityListMap.put(entry.getKey(), valueList);
                    }
                }
            }
        } else {
            // 构建查询条件
            queryWrapper.in(StringUtil.toSnakeCase(referencedEntityPrimaryKey), annoObjectForeignKeyList);
            // 查询entity列表
            List<T> list = getEntityList(queryWrapper);
            if (Validator.notEmpty(list)) {
                for (T entity : list) {
                    String keyValue = BeanUtil.getStringProperty(entity, StringUtil.toLowerCaseCamel(referencedEntityPrimaryKey));
                    List entityList = valueEntityListMap.get(keyValue);
                    if (entityList == null) {
                        entityList = new ArrayList<>();
                        valueEntityListMap.put(keyValue, entityList);
                    }
                    entityList.add(cloneOrConvertBean(entity));
                }
            }
        }
        // 绑定结果
        BeanUtil.bindPropValueOfList(annoObjectField, annoObjectList, annoObjectForeignKey, valueEntityListMap);
    }


}
