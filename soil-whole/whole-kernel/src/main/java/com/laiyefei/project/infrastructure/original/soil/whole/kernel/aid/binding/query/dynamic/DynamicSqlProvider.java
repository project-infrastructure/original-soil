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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.binding.parser.ParserCache;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.prepper.SystemConfig;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.binding.QueryBuilder;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.co.Constant;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.StringUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.Validator;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 动态SQL构建Provider
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class DynamicSqlProvider implements IAid {
    /**
     * 构建动态SQL
     *
     * @param ew
     * @return
     */
    public String buildSql(QueryWrapper ew) {
        return buildDynamicSql(null, ew, true);
    }

    /**
     * 构建动态SQL
     *
     * @param ew
     * @return
     */
    public String buildSqlForList(QueryWrapper ew) {
        return buildDynamicSql(null, ew, false);
    }

    /**
     * 构建动态SQL
     *
     * @param page 分页参数，用于MP分页插件AOP，不可删除
     * @param ew
     * @return
     */
    public <DTO> String buildSqlForListWithPage(Page<?> page, QueryWrapper<DTO> ew) {
        return buildDynamicSql(page, ew, false);
    }

    /**
     * 构建动态SQL
     *
     * @param page 分页参数，用于MP分页插件AOP，不可删除
     * @param ew
     * @return
     */
    private <DTO> String buildDynamicSql(Page<?> page, QueryWrapper<DTO> ew, boolean limit1) {
        DynamicJoinQueryWrapper wrapper = (DynamicJoinQueryWrapper) ew;
        return new SQL() {{
            if (Validator.isEmpty(ew.getSqlSelect())) {
                SELECT("self.*");
            } else {
                SELECT(formatSqlSelect(ew.getSqlSelect()));
            }
            FROM(wrapper.getEntityTable() + " self");
            //提取字段，根据查询条件中涉及的表，动态join
            List<AnnoJoiner> annoJoinerList = wrapper.getAnnoJoiners();
            if (Validator.notEmpty(annoJoinerList)) {
                Set<String> tempSet = new HashSet<>();
                StringBuilder sb = new StringBuilder();
                for (AnnoJoiner joiner : annoJoinerList) {
                    if (Validator.notEmpty(joiner.getJoin()) && Validator.notEmpty(joiner.getOnSegment())) {
                        if (joiner.getMiddleTable() != null) {
                            sb.setLength(0);
                            sb.append(joiner.getMiddleTable()).append(" ").append(joiner.getMiddleTableAlias()).append(" ON ").append(joiner.getMiddleTableOnSegment());
                            if (StringUtil.containsIgnoreCase(joiner.getMiddleTable(), " " + Constant.COLUMN_IS_DELETED) == false && ParserCache.hasDeletedColumn(joiner.getMiddleTable())) {
                                sb.append(" AND ").append(joiner.getMiddleTableAlias()).append(".").append(Constant.COLUMN_IS_DELETED).append(" = ").append(SystemConfig.getActiveFlagValue());
                            }
                            String joinSegment = sb.toString();
                            if (!tempSet.contains(joinSegment)) {
                                LEFT_OUTER_JOIN(joinSegment);
                                tempSet.add(joinSegment);
                            }
                        }
                        sb.setLength(0);
                        sb.append(joiner.getJoin()).append(" ").append(joiner.getAlias()).append(" ON ").append(joiner.getOnSegment());
                        if (StringUtil.containsIgnoreCase(joiner.getOnSegment(), " " + Constant.COLUMN_IS_DELETED) == false && ParserCache.hasDeletedColumn(joiner.getJoin())) {
                            sb.append(" AND ").append(joiner.getAlias()).append(".").append(Constant.COLUMN_IS_DELETED).append(" = ").append(SystemConfig.getActiveFlagValue());
                        }
                        String joinSegment = sb.toString();
                        if (!tempSet.contains(joinSegment)) {
                            LEFT_OUTER_JOIN(joinSegment);
                            tempSet.add(joinSegment);
                        }
                    }
                }
                tempSet = null;
            }
            MergeSegments segments = ew.getExpression();
            if (segments != null) {
                String normalSql = segments.getNormal().getSqlSegment();
                if (Validator.notEmpty(normalSql)) {
                    WHERE(formatNormalSql(normalSql));
                    // 动态为主表添加is_deleted=0
                    String isDeletedSection = "self." + Constant.COLUMN_IS_DELETED;
                    if (QueryBuilder.checkHasColumn(segments.getNormal(), isDeletedSection) == false && ParserCache.hasDeletedColumn(wrapper.getEntityTable())) {
                        WHERE(isDeletedSection + " = " + SystemConfig.getActiveFlagValue());
                    }
                    if (segments.getOrderBy() != null) {
                        String orderBySql = segments.getOrderBy().getSqlSegment();
                        int beginIndex = StringUtil.indexOfIgnoreCase(orderBySql, "ORDER BY ");
                        if (beginIndex >= 0) {
                            orderBySql = StringUtil.substring(orderBySql, beginIndex + "ORDER BY ".length());
                            ORDER_BY(orderBySql);
                        }
                    }
                }
            }
            if (limit1) {
                //LIMIT(1);
                //TODO.. the mybatis version is 2.5.5 right.
                //sql().limit = variable;
                //sql().limitingRowsStrategy = AbstractSQL.SQLStatement.LimitingRowsStrategy.OFFSET_LIMIT;
            }
        }}.toString();
    }


    /**
     * 格式化sql select列语句
     *
     * @param sqlSelect
     * @return
     */
    private String formatSqlSelect(String sqlSelect) {
        String[] columns = StringUtil.split(sqlSelect);
        List<String> selects = new ArrayList<>(columns.length);
        for (String column : columns) {
            column = StringUtil.removeDuplicateBlank(column).trim();
            selects.add("self." + StringUtil.toSnakeCase(column));
        }
        return StringUtil.join(selects);
    }

    /**
     * 格式化where条件的sql
     *
     * @param normalSql
     * @return
     */
    private String formatNormalSql(String normalSql) {
        if (normalSql.startsWith("(") && normalSql.endsWith(")")) {
            return StringUtil.substring(normalSql, 1, normalSql.length() - 1);
        }
        return normalSql;
    }

}
