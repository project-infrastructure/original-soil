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

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.binding.parser.ParserCache;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.binding.JoinsBinder;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.dto.Pagination;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 动态查询wrapper
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class DynamicJoinQueryWrapper<DTO, E> extends ExtQueryWrapper<DTO, E> implements IAid {
    public DynamicJoinQueryWrapper(Class<DTO> dtoClass, Collection<String> fields) {
        this.dtoClass = dtoClass;
        this.fields = fields;
    }

    /**
     * DTO类
     */
    @Getter
    private Class<DTO> dtoClass;
    /**
     * 字段
     */
    private Collection<String> fields;

    /**
     * dto字段和值
     */
    public List<AnnoJoiner> getAnnoJoiners() {
        return ParserCache.getAnnoJoiners(this.dtoClass, fields);
    }

    /**
     * 查询一条数据
     *
     * @param entityClazz
     * @return
     */
    @Override
    public E queryOne(Class<E> entityClazz) {
        return JoinsBinder.queryOne(this, entityClazz);
    }

    /**
     * 查询一条数据
     *
     * @param entityClazz
     * @return
     */
    @Override
    public List<E> queryList(Class<E> entityClazz) {
        return JoinsBinder.queryList(this, entityClazz);
    }

    /**
     * 查询一条数据
     *
     * @param entityClazz
     * @return
     */
    @Override
    public List<E> queryList(Class<E> entityClazz, Pagination pagination) {
        return JoinsBinder.queryList(this, entityClazz, pagination);
    }

}
