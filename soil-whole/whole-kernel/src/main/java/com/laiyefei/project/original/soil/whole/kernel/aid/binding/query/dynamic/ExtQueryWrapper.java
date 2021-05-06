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
package com.laiyefei.project.original.soil.whole.kernel.aid.binding.query.dynamic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;
import com.laiyefei.project.original.soil.whole.kernel.aid.ContextHelper;
import com.laiyefei.project.original.soil.whole.kernel.aid.binding.parser.ParserCache;
import com.laiyefei.project.original.soil.whole.kernel.aid.exception.BusinessException;
import com.laiyefei.project.original.soil.whole.kernel.pojo.dto.Pagination;
import com.laiyefei.project.original.soil.whole.kernel.pojo.dto.Status;
import com.laiyefei.project.original.soil.whole.kernel.service.BaseService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 动态查询wrapper
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class ExtQueryWrapper<DTO, E> extends QueryWrapper<DTO> implements IAid {
    /**
     * 主实体class
     */
    @Getter
    @Setter
    private Class<E> mainEntityClass;

    /**
     * 获取entity表名
     *
     * @return
     */
    public String getEntityTable() {
        return ParserCache.getEntityTableName(getMainEntityClass());
    }

    /**
     * 查询一条数据
     *
     * @param entityClazz
     * @return
     */
    public E queryOne(Class<E> entityClazz) {
        this.mainEntityClass = entityClazz;
        BaseService baseService = ContextHelper.getBaseServiceByEntity(this.mainEntityClass);
        if (baseService != null) {
            return (E) baseService.getEntity(this);
        } else {
            throw new BusinessException(Status.FAIL_INVALID_PARAM, "单表查询对象无BaseService实现: " + this.mainEntityClass.getSimpleName());
        }
    }

    /**
     * 查询一条数据
     *
     * @param entityClazz
     * @return
     */
    public List<E> queryList(Class<E> entityClazz) {
        BaseService baseService = ContextHelper.getBaseServiceByEntity(entityClazz);
        if (baseService != null) {
            return (List<E>) baseService.getEntityList(this);
        } else {
            throw new BusinessException(Status.FAIL_INVALID_PARAM, "单表查询对象无BaseService实现: " + entityClazz.getSimpleName());
        }
    }

    /**
     * 查询一条数据
     *
     * @param entityClazz
     * @return
     */
    public List<E> queryList(Class<E> entityClazz, Pagination pagination) {
        BaseService baseService = ContextHelper.getBaseServiceByEntity(entityClazz);
        if (baseService != null) {
            return (List<E>) baseService.getEntityList(this, pagination);
        } else {
            throw new BusinessException(Status.FAIL_INVALID_PARAM, "单表查询对象无BaseService实现: " + entityClazz.getSimpleName());
        }
    }

}
