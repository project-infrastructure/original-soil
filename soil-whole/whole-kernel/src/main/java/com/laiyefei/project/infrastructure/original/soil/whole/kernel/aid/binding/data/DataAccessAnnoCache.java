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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.binding.data;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.dto.Status;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.BeanUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.Validator;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.binding.QueryBuilder;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.exception.BusinessException;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.ao.DataAccessCheckpoint;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.co.CheckpointType;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-10-02 18:09
 * @Desc : 数据访问权限的注解缓存
 * @Version : v2.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Slf4j
public class DataAccessAnnoCache implements IAid {
    /**
     * 注解缓存
     */
    private static Map<String, String[]> DATA_PERMISSION_ANNO_CACHE = new ConcurrentHashMap<>();

    /**
     * 是否有检查点注解
     *
     * @param entityDto
     * @return
     */
    public static boolean hasDataAccessCheckpoint(Class<?> entityDto) {
        initClassCheckpoint(entityDto);
        String[] columns = DATA_PERMISSION_ANNO_CACHE.get(entityDto.getName());
        if (Validator.isEmpty(columns)) {
            return false;
        }
        for (String type : columns) {
            if (Validator.notEmpty(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取数据权限的用户类型列名
     *
     * @param entityDto
     * @return
     */
    public static String getDataPermissionColumn(Class<?> entityDto, CheckpointType type) {
        initClassCheckpoint(entityDto);
        int typeIndex = type.index();
        String key = entityDto.getName();
        String[] columns = DATA_PERMISSION_ANNO_CACHE.get(key);
        if (columns != null && (columns.length - 1) >= typeIndex) {
            return columns[typeIndex];
        }
        return null;
    }

    /**
     * 初始化entityDto的检查点缓存
     *
     * @param entityDto
     */
    private static void initClassCheckpoint(Class<?> entityDto) {
        String key = entityDto.getName();
        if (!DATA_PERMISSION_ANNO_CACHE.containsKey(key)) {
            String[] results = {"", "", "", "", ""};
            List<Field> fieldList = BeanUtil.extractFields(entityDto, DataAccessCheckpoint.class);
            if (Validator.notEmpty(fieldList)) {
                for (Field fld : fieldList) {
                    DataAccessCheckpoint checkpoint = fld.getAnnotation(DataAccessCheckpoint.class);
                    if (Validator.notEmpty(results[checkpoint.type().index()])) {
                        throw new BusinessException(Status.FAIL_VALIDATION, entityDto.getSimpleName() + "中DataPermissionCheckpoint同类型注解重复！");
                    }
                    results[checkpoint.type().index()] = QueryBuilder.getColumnName(fld);
                }
            }
            DATA_PERMISSION_ANNO_CACHE.put(key, results);
        }
    }

}
