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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.prepper.SystemConfig;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.ContextHelper;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.co.Constant;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.SqlExecutor;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.StringUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.Validator;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 中间表
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class MiddleTable implements IAid {
    private static final Logger log = LoggerFactory.getLogger(MiddleTable.class);
    /**
     * 中间表
     */
    private String table;
    /**
     * 与注解VO的外键关联的连接字段
     */
    private String equalsToAnnoObjectFKColumn;
    /**
     * 与被引用Entity属性主键的连接字段
     */
    private String equalsToRefEntityPkColumn;
    /**
     * 附加条件
     */
    private List<String> additionalConditions;

    public MiddleTable(String table) {
        this.table = table;
    }

    /**
     * 连接（左手连接VO的外键，右手连接Entity属性的主键）
     *
     * @param equalsToAnnoObjectFKColumn
     * @param equalsToRefEntityPkColumn
     * @return
     */
    public MiddleTable connect(String equalsToAnnoObjectFKColumn, String equalsToRefEntityPkColumn) {
        if (Validator.notEmpty(this.equalsToAnnoObjectFKColumn) && Validator.notEquals(this.equalsToAnnoObjectFKColumn, equalsToAnnoObjectFKColumn)) {
            log.warn("中间表关联字段被覆盖: {}->{}，暂仅支持单字段关联，请检查注解条件！", this.equalsToAnnoObjectFKColumn, equalsToAnnoObjectFKColumn);
        }
        if (Validator.notEmpty(this.equalsToRefEntityPkColumn) && Validator.notEquals(this.equalsToRefEntityPkColumn, equalsToRefEntityPkColumn)) {
            log.warn("中间表关联字段被覆盖: {}->{}，暂仅支持单字段关联，请检查注解条件！", this.equalsToRefEntityPkColumn, equalsToRefEntityPkColumn);
        }
        this.equalsToAnnoObjectFKColumn = equalsToAnnoObjectFKColumn;
        this.equalsToRefEntityPkColumn = equalsToRefEntityPkColumn;
        return this;
    }

    /**
     * 添加中间表查询所需的附加条件
     *
     * @param additionalCondition
     */
    public MiddleTable addAdditionalCondition(String additionalCondition) {
        if (this.additionalConditions == null) {
            this.additionalConditions = new ArrayList<>();
        }
        this.additionalConditions.add(additionalCondition);
        return this;
    }

    public String getEqualsToAnnoObjectFKColumn() {
        return equalsToAnnoObjectFKColumn;
    }

    public String getEqualsToRefEntityPkColumn() {
        return equalsToRefEntityPkColumn;
    }

    /**
     * 执行1-1关联查询，得到关联映射Map
     *
     * @param annoObjectForeignKeyList
     * @return
     */
    public Map<String, Object> executeOneToOneQuery(List annoObjectForeignKeyList) {
        //id //org_id
        String keyName = getEqualsToAnnoObjectFKColumn(), valueName = getEqualsToRefEntityPkColumn();
        TableLinkage linkage = ParserCache.getTableLinkage(table);
        // 有定义mapper，首选mapper
        if (linkage != null) {
            List<Map<String, Object>> resultSetMapList = queryByMapper(linkage, annoObjectForeignKeyList);
            return SqlExecutor.convertToOneToOneResult(resultSetMapList, keyName, valueName);
        } else {
            // 提取中间表查询SQL: SELECT id, org_id FROM department WHERE id IN(?)
            String sql = toSQL(annoObjectForeignKeyList);
            // 执行查询并合并结果
            Map<String, Object> middleTableResultMap = SqlExecutor.executeQueryAndMergeOneToOneResult(sql, annoObjectForeignKeyList, keyName, valueName);
            return middleTableResultMap;
        }
    }

    /**
     * 执行1-N关联查询，得到关联映射Map
     *
     * @param annoObjectForeignKeyList
     * @return
     */
    public Map<String, List> executeOneToManyQuery(List annoObjectForeignKeyList) {
        //user_id //role_id
        String keyName = getEqualsToAnnoObjectFKColumn(), valueName = getEqualsToRefEntityPkColumn();
        TableLinkage linkage = ParserCache.getTableLinkage(table);
        // 有定义mapper，首选mapper
        if (linkage != null) {
            List<Map<String, Object>> resultSetMapList = queryByMapper(linkage, annoObjectForeignKeyList);
            return SqlExecutor.convertToOneToManyResult(resultSetMapList, keyName, valueName);
        } else {
            // 提取中间表查询SQL: SELECT user_id, role_id FROM user_role WHERE user_id IN(?)
            String sql = toSQL(annoObjectForeignKeyList);
            // 执行查询并合并结果
            Map<String, List> middleTableResultMap = SqlExecutor.executeQueryAndMergeOneToManyResult(sql, annoObjectForeignKeyList, keyName, valueName);
            return middleTableResultMap;
        }
    }

    /**
     * 通过定义的Mapper查询结果
     *
     * @param linkage
     * @param annoObjectForeignKeyList
     * @return
     */
    private List<Map<String, Object>> queryByMapper(TableLinkage linkage, List annoObjectForeignKeyList) {
        BaseMapper mapper = (BaseMapper) ContextHelper.getBean(linkage.getMapperClass());
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntityClass(linkage.getEntityClass());
        queryWrapper.select(equalsToAnnoObjectFKColumn, equalsToRefEntityPkColumn);
        queryWrapper.in(equalsToAnnoObjectFKColumn, annoObjectForeignKeyList);
        if (additionalConditions != null) {
            for (String condition : additionalConditions) {
                queryWrapper.apply(condition);
            }
        }
        List<Map<String, Object>> resultSetMapList = mapper.selectMaps(queryWrapper);
        return resultSetMapList;
    }

    /**
     * 转换查询SQL
     *
     * @param annoObjectForeignKeyList 注解外键值的列表，用于拼接SQL查询
     * @return
     */
    private String toSQL(List annoObjectForeignKeyList) {
        if (Validator.isEmpty(annoObjectForeignKeyList)) {
            return null;
        }
        String params = StringUtil.repeat("?", ",", annoObjectForeignKeyList.size());
        // 构建SQL
        return new SQL() {{
            SELECT(equalsToAnnoObjectFKColumn + Constant.SEPARATOR_COMMA + equalsToRefEntityPkColumn);
            FROM(table);
            WHERE(equalsToAnnoObjectFKColumn + " IN (" + params + ")");
            // 添加删除标记
            boolean appendDeleteFlag = true;
            if (additionalConditions != null) {
                for (String condition : additionalConditions) {
                    WHERE(condition);
                    if (StringUtil.containsIgnoreCase(condition, Constant.COLUMN_IS_DELETED)) {
                        appendDeleteFlag = false;
                    }
                }
            }
            // 如果需要删除
            if (appendDeleteFlag && ParserCache.hasDeletedColumn(table)) {
                WHERE(Constant.COLUMN_IS_DELETED + " = " + SystemConfig.getActiveFlagValue());
            }
        }}.toString();
    }
}
