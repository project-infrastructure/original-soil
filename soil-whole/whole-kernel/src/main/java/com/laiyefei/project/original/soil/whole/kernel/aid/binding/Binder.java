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
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;
import com.laiyefei.project.original.soil.whole.kernel.pojo.dto.Pagination;

import java.util.List;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 绑定器统一调用入口类
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class Binder implements IAid {

    /**
     * 关联查询一条主表数据
     *
     * @param queryWrapper
     * @param entityClazz  返回结果entity/vo类
     * @return
     * @throws Exception
     */
    public static <DTO, E> E joinQueryOne(QueryWrapper<DTO> queryWrapper, Class<E> entityClazz) {
        return JoinsBinder.queryOne(queryWrapper, entityClazz);
    }

    /**
     * 关联查询符合条件的全部主表数据集合（不分页）
     *
     * @param queryWrapper 调用QueryBuilder.to*QueryWrapper得到的实例
     * @param entityClazz  返回结果entity/vo类
     * @return
     * @throws Exception
     */
    public static <DTO, E> List<E> joinQueryList(QueryWrapper<DTO> queryWrapper, Class<E> entityClazz) {
        return JoinsBinder.queryList(queryWrapper, entityClazz);
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
    public static <DTO, E> List<E> joinQueryList(QueryWrapper<DTO> queryWrapper, Class<E> entityClazz, Pagination pagination) {
        return JoinsBinder.queryList(queryWrapper, entityClazz, pagination);
    }

    /**
     * 自动转换和绑定单个VO中的注解关联（禁止循环调用，多个对象请调用convertAndBind(voList, voClass)）
     *
     * @param voClass 需要转换的VO class
     * @param <E>
     * @param <VO>
     * @return
     */
    public static <E, VO> VO convertAndBindRelations(E entity, Class<VO> voClass) {
        return RelationsBinder.convertAndBind(entity, voClass);
    }

    /**
     * 自动转换和绑定多个VO中的注解关联
     *
     * @param entityList 需要转换的VO list
     * @param voClass    VO class
     * @param <E>
     * @param <VO>
     * @return
     */
    public static <E, VO> List<VO> convertAndBindRelations(List<E> entityList, Class<VO> voClass) {
        return RelationsBinder.convertAndBind(entityList, voClass);
    }

    /**
     * 自动绑定单个VO的关联对象（禁止循环调用，多个对象请调用bind(voList)）
     *
     * @param vo 需要注解绑定的对象
     * @return
     * @throws Exception
     */
    public static <VO> void bindRelations(VO vo) {
        RelationsBinder.bind(vo);
    }

    /**
     * 自动绑定多个VO集合的关联对象
     *
     * @param voList 需要注解绑定的对象集合
     * @return
     * @throws Exception
     */
    public static <VO> void bindRelations(List<VO> voList) {
        RelationsBinder.bind(voList);
    }

}
