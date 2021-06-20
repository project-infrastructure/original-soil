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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.core;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.dao.DictionaryMapper;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.dto.DictionaryVO;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.dto.KeyValue;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.dto.Status;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.po.Dictionary;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.BeanUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.Validator;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.IGetter;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.ISetter;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.exception.BusinessException;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 数据字典相关service实现
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Primary
@Service("dictionaryService")
public class DictionaryServiceImpl extends BaseServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {
    private static final Logger log = LoggerFactory.getLogger(DictionaryServiceImpl.class);

    private static final String FIELD_NAME_ITEM_NAME = BeanUtil.convertToFieldName(Dictionary::getItemName);
    private static final String FIELD_NAME_ITEM_VALUE = BeanUtil.convertToFieldName(Dictionary::getItemValue);
    private static final String FIELD_NAME_TYPE = BeanUtil.convertToFieldName(Dictionary::getType);
    private static final String FIELD_NAME_PARENT_ID = BeanUtil.convertToFieldName(Dictionary::getParentId);

    @Override
    public List<KeyValue> getKeyValueList(String type) {
        // 构建查询条件
        Wrapper queryDictionary = new QueryWrapper<Dictionary>().lambda()
                .select(Dictionary::getItemName, Dictionary::getItemValue)
                .eq(Dictionary::getType, type)
                .gt(Dictionary::getParentId, 0)
                .orderByAsc(Dictionary::getSortId);
        // 返回构建条件
        return getKeyValueList(queryDictionary);
    }

    @Override
    public <T1, T2, S> void bindItemLabel(List voList, ISetter<T1, S> setFieldLabelFn,
                                          IGetter<T2> getFieldIdFn, String type) {
        if (Validator.isEmpty(voList)) {
            return;
        }
        bindingFieldTo(voList)
                .link(Dictionary::getItemName, setFieldLabelFn)
                .joinOn(getFieldIdFn, Dictionary::getItemValue)
                .andEQ(FIELD_NAME_TYPE, type)
                .andGT(FIELD_NAME_PARENT_ID, 0)
                .bind();
    }

    @Override
    public void bindItemLabel(List voList, String setFieldName, String getFieldName, String type) {
        if (Validator.isEmpty(voList)) {
            return;
        }
        bindingFieldTo(voList)
                .link(FIELD_NAME_ITEM_NAME, setFieldName)
                .joinOn(getFieldName, FIELD_NAME_ITEM_VALUE)
                .andEQ(FIELD_NAME_TYPE, type)
                .andGT(FIELD_NAME_PARENT_ID, 0)
                .bind();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addDictTree(DictionaryVO dictVO) {
        return createDictAndChildren(dictVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createDictAndChildren(DictionaryVO dictVO) {
        Dictionary dictionary = dictVO;
        if (!super.createEntity(dictionary)) {
            log.warn("新建数据字典定义失败，type=" + dictVO.getType());
            return false;
        }
        List<Dictionary> children = dictVO.getChildren();
        if (Validator.notEmpty(children)) {
            for (Dictionary dict : children) {
                dict.setParentId(dictionary.getId());
                dict.setType(dictionary.getType());
            }
            // 批量保存
            boolean success = super.createEntities(children);
            if (!success) {
                String errorMsg = "新建数据字典子项失败，type=" + dictVO.getType();
                log.warn(errorMsg);
                throw new BusinessException(Status.FAIL_OPERATION, errorMsg);
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDictAndChildren(DictionaryVO dictVO) {
        //将DictionaryVO转化为Dictionary
        Dictionary dictionary = dictVO;
        if (!super.updateEntity(dictionary)) {
            log.warn("更新数据字典定义失败，type=" + dictVO.getType());
            return false;
        }
        //获取原 子数据字典list
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(Dictionary::getParentId, dictVO.getId());
        List<Dictionary> oldDictList = super.getEntityList(queryWrapper);
        List<Dictionary> newDictList = dictVO.getChildren();
        Set<Long> dictItemIds = new HashSet<>();
        if (Validator.notEmpty(newDictList)) {
            for (Dictionary dict : newDictList) {
                dict.setType(dictVO.getType()).setParentId(dictVO.getId());
                if (Validator.notEmpty(dict.getId())) {
                    dictItemIds.add(dict.getId());
                    if (!super.updateEntity(dict)) {
                        log.warn("更新字典子项失败，itemName=" + dict.getItemName());
                        throw new BusinessException(Status.FAIL_EXCEPTION, "更新字典子项异常");
                    }
                } else {
                    if (!super.createEntity(dict)) {
                        log.warn("新建字典子项失败，itemName=" + dict.getItemName());
                        throw new BusinessException(Status.FAIL_EXCEPTION, "新建字典子项异常");
                    }
                }
            }
        }
        if (Validator.notEmpty(oldDictList)) {
            for (Dictionary dict : oldDictList) {
                if (!dictItemIds.contains(dict.getId())) {
                    if (!super.deleteEntity(dict.getId())) {
                        log.warn("删除子数据字典失败，itemName=" + dict.getItemName());
                        throw new BusinessException(Status.FAIL_EXCEPTION, "删除字典子项异常");
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean deleteDictAndChildren(Long id) {
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(Dictionary::getId, id)
                .or()
                .eq(Dictionary::getParentId, id);
        deleteEntities(queryWrapper);
        return true;
    }
}