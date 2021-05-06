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
package com.laiyefei.project.original.soil.whole.kernel.aid.binding;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;
import com.laiyefei.project.original.soil.whole.kernel.aid.ContextHelper;
import com.laiyefei.project.original.soil.whole.kernel.aid.binding.parser.ParserCache;
import com.laiyefei.project.original.soil.whole.kernel.aid.binding.query.dynamic.AnnoJoiner;
import com.laiyefei.project.original.soil.whole.kernel.aid.binding.query.dynamic.DynamicJoinQueryWrapper;
import com.laiyefei.project.original.soil.whole.kernel.aid.exception.BusinessException;
import com.laiyefei.project.original.soil.whole.kernel.pojo.co.Constant;
import com.laiyefei.project.original.soil.whole.kernel.pojo.dao.DynamicQueryMapper;
import com.laiyefei.project.original.soil.whole.kernel.pojo.dto.Pagination;
import com.laiyefei.project.original.soil.whole.kernel.pojo.dto.Status;
import com.laiyefei.project.original.soil.whole.kernel.prepper.SystemConfig;
import com.laiyefei.project.original.soil.whole.kernel.service.BaseService;
import com.laiyefei.project.original.soil.whole.kernel.tools.util.BeanUtil;
import com.laiyefei.project.original.soil.whole.kernel.tools.util.StringUtil;
import com.laiyefei.project.original.soil.whole.kernel.tools.util.Validator;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : join连接查询绑定器
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Slf4j
public class JoinsBinder implements IAid {

    /**
     * 关联查询一条数据
     *
     * @param queryWrapper
     * @param entityClazz  返回结果entity/vo类
     * @return
     * @throws Exception
     */
    public static <DTO, E> E queryOne(QueryWrapper<DTO> queryWrapper, Class<E> entityClazz) {
        List<E> list = executeJoinQuery(queryWrapper, entityClazz, null, true);
        if (Validator.notEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 关联查询符合条件的全部数据集合（不分页）
     *
     * @param queryWrapper 调用QueryBuilder.to*QueryWrapper得到的实例
     * @param entityClazz  返回结果entity/vo类
     * @return
     * @throws Exception
     */
    public static <DTO, E> List<E> queryList(QueryWrapper<DTO> queryWrapper, Class<E> entityClazz) {
        return queryList(queryWrapper, entityClazz, null);
    }

    /**
     * 关联查询符合条件的指定页数据（分页）
     *
     * @param queryWrapper 调用QueryBuilder.to*QueryWrapper得到的实例
     * @param entityClazz  返回结果entity/vo类
     * @param pagination   分页
     * @return
     * @throws Exception
     */
    public static <DTO, E> List<E> queryList(QueryWrapper<DTO> queryWrapper, Class<E> entityClazz, Pagination pagination) {
        return executeJoinQuery(queryWrapper, entityClazz, pagination, false);
    }

    /**
     * 关联查询（分页）
     *
     * @param queryWrapper 调用QueryBuilder.to*QueryWrapper得到的实例
     * @param entityClazz  返回结果entity/vo类
     * @param pagination   分页
     * @return
     * @throws Exception
     */
    private static <DTO, E> List<E> executeJoinQuery(QueryWrapper<DTO> queryWrapper, Class<E> entityClazz, Pagination pagination, boolean limit1) {
        // 非动态查询，走BaseService
        if (queryWrapper instanceof DynamicJoinQueryWrapper == false) {
            BaseService baseService = ContextHelper.getBaseServiceByEntity(entityClazz);
            if (baseService != null) {
                return baseService.getEntityList(queryWrapper, pagination);
            } else {
                throw new BusinessException(Status.FAIL_INVALID_PARAM, "单表查询对象无BaseService实现: " + entityClazz.getSimpleName());
            }
        }
        long begin = System.currentTimeMillis();
        // 转换为queryWrapper
        DynamicJoinQueryWrapper dynamicJoinWrapper = (DynamicJoinQueryWrapper) queryWrapper;
        dynamicJoinWrapper.setMainEntityClass(entityClazz);
        List<Map<String, Object>> mapList = null;
        if (pagination == null) {
            if (limit1) {
                Map<String, Object> oneResult = getDynamicQueryMapper().query(dynamicJoinWrapper);
                if (oneResult != null) {
                    mapList = new ArrayList<>();
                    mapList.add(oneResult);
                }
            } else {
                mapList = getDynamicQueryMapper().queryForList(dynamicJoinWrapper);
            }
        } else {
            // 格式化orderBy
            formatOrderBy(dynamicJoinWrapper, entityClazz, pagination);
            IPage<Map<String, Object>> pageResult = getDynamicQueryMapper().queryForListWithPage(pagination.toPage(), dynamicJoinWrapper);
            pagination.setTotalCount(pageResult.getTotal());
            mapList = pageResult.getRecords();
        }
        long ms = (System.currentTimeMillis() - begin);
        if (ms > 5000) {
            log.warn("{} 动态Join查询执行耗时 {} ms，建议优化", dynamicJoinWrapper.getDtoClass().getSimpleName(), ms);
        }
        if (Validator.isEmpty(mapList)) {
            return Collections.emptyList();
        }
        if (mapList.size() > SystemConfig.getBatchSize()) {
            log.warn("{} 动态Join查询记录数过大( {} 条), 建议优化", dynamicJoinWrapper.getDtoClass().getSimpleName(), mapList.size());
        }
        // 转换查询结果
        List<E> entityList = new ArrayList<>();
        for (Map<String, Object> colValueMap : mapList) {
            Map<String, Object> fieldValueMap = new HashMap<>();
            // 格式化map
            for (Map.Entry<String, Object> entry : colValueMap.entrySet()) {
                String fieldName = StringUtil.toLowerCaseCamel(entry.getKey());
                // 如果是布尔类型，检查entity中的定义是Boolean/boolean
                if (entry.getValue() instanceof Boolean && StringUtil.startsWithIgnoreCase(entry.getKey(), "is_")) {
                    // 检查有is前缀的Boolean类型
                    Field boolType = BeanUtil.extractField(entityClazz, fieldName);
                    if (boolType == null) {
                        // 检查无is前缀的boolean类型
                        String tempFieldName = StringUtil.toLowerCaseCamel(StringUtil.substringAfter(entry.getKey(), "_"));
                        boolType = BeanUtil.extractField(entityClazz, tempFieldName);
                        if (boolType != null) {
                            fieldName = tempFieldName;
                        }
                    }
                }
                fieldValueMap.put(fieldName, entry.getValue());
            }
            // 绑定map到entity
            try {
                E entityInst = entityClazz.newInstance();
                BeanUtil.bindProperties(entityInst, fieldValueMap);
                entityList.add(entityInst);
            } catch (Exception e) {
                log.warn("new实例并绑定属性值异常", e);
            }
        }
        return entityList;
    }

    /**
     * 格式化orderBy
     *
     * @param queryWrapper
     * @param pagination
     */
    private static <E> void formatOrderBy(DynamicJoinQueryWrapper queryWrapper, Class<E> entityClazz, Pagination pagination) {
        // 如果是默认id排序，检查是否有id字段
        if (pagination.isDefaultOrderBy()) {
            // 优化排序
            String pk = ContextHelper.getPrimaryKey(entityClazz);
            // 主键非有序id字段，需要清空默认排序
            if (!Constant.FieldName.id.name().equals(pk)) {
                pagination.clearDefaultOrder();
            }
        }
        // 格式化排序
        if (Validator.notEmpty(pagination.getOrderBy())) {
            List<String> orderByList = new ArrayList<>();
            String[] orderByFields = StringUtil.split(pagination.getOrderBy());
            for (String field : orderByFields) {
                String fieldName = field, orderType = null;
                if (field.contains(":")) {
                    String[] fieldAndOrder = StringUtil.split(field, ":");
                    fieldName = fieldAndOrder[0];
                    orderType = fieldAndOrder[1];
                }
                // 获取列定义的AnnoJoiner 得到别名
                AnnoJoiner joiner = ParserCache.getAnnoJoiner(queryWrapper.getDtoClass(), fieldName);
                if (joiner != null) {
                    if (Validator.notEmpty(joiner.getAlias())) {
                        fieldName = joiner.getAlias() + "." + joiner.getColumnName();
                    } else {
                        fieldName = "self." + joiner.getColumnName();
                    }
                } else {
                    fieldName = "self." + StringUtil.toSnakeCase(fieldName);
                }
                if (Validator.notEmpty(orderType)) {
                    orderByList.add(fieldName + ":" + orderType);
                } else {
                    orderByList.add(fieldName);
                }
            }
            pagination.setOrderBy(StringUtil.join(orderByList));
        }
    }

    /**
     * 获取mapper实例
     *
     * @return
     */
    private static DynamicQueryMapper getDynamicQueryMapper() {
        return ContextHelper.getBean(DynamicQueryMapper.class);
    }

}