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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.binding.query.dynamic.DynamicSqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 通用联表查询Mapper
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Mapper
public interface DynamicQueryMapper {

    /**
     * 动态SQL查询
     *
     * @return
     */
    @SelectProvider(type = DynamicSqlProvider.class, method = "buildSql")
    Map<String, Object> query(@Param(Constants.WRAPPER) QueryWrapper ew);

    /**
     * 动态SQL查询
     *
     * @return
     */
    @SelectProvider(type = DynamicSqlProvider.class, method = "buildSqlForList")
    List<Map<String, Object>> queryForList(@Param(Constants.WRAPPER) QueryWrapper ew);

    /**
     * 动态SQL查询
     *
     * @param page
     * @return
     */
    @SelectProvider(type = DynamicSqlProvider.class, method = "buildSqlForListWithPage")
    IPage<Map<String, Object>> queryForListWithPage(Page<?> page, @Param(Constants.WRAPPER) QueryWrapper ew);

}