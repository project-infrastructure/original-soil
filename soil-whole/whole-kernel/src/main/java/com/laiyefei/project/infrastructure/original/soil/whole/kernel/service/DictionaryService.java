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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.service;


import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.dto.DictionaryVO;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.dto.KeyValue;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.po.Dictionary;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.IGetter;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.ISetter;

import java.util.List;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 数据字典Service
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface DictionaryService extends BaseService<Dictionary> {

    /***
     * 获取对应类型的键值对
     * @param type
     * @return
     */
    List<KeyValue> getKeyValueList(String type);

    /***
     * 绑定itemName字段到VoList
     * @param voList
     * @param setFieldLabelFn
     * @param getFieldIdFn
     * @param <T1>
     * @param <T2>
     * @param <S>
     */
    <T1, T2, S> void bindItemLabel(List voList, ISetter<T1, S> setFieldLabelFn,
                                   IGetter<T2> getFieldIdFn, String type);

    /***
     * 绑定itemName字段到VoList
     * @param voList
     * @param setFieldName
     * @param getFieldName
     */
    void bindItemLabel(List voList, String setFieldName, String getFieldName, String type);

    /***
     * 添加多层级数据字典 (已废弃，请调用createDictAndChildren)
     * @param dictVO
     * @return
     */
    @Deprecated
    boolean addDictTree(DictionaryVO dictVO);

    /**
     * 添加字典定义及其子项
     *
     * @param dictVO
     * @return
     */
    boolean createDictAndChildren(DictionaryVO dictVO);

    /**
     * 更新字典定义及其子项
     *
     * @param dictVO
     * @return
     */
    boolean updateDictAndChildren(DictionaryVO dictVO);

    /**
     * 删除字典定义及其子项
     *
     * @param id
     * @return
     */
    boolean deleteDictAndChildren(Long id);

}
