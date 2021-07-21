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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.binding.binder;

import com.baomidou.mybatisplus.extension.service.IService;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.IGetter;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.ISetter;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.BeanUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.StringUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-10-02 18:09
 * @Desc : 关联字段绑定
 * @Version : v2.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class FieldBinder<T> extends BaseBinder<T> {
    private static final Logger log = LoggerFactory.getLogger(FieldBinder.class);

    /**
     * VO对象绑定赋值的属性名列表
     */
    protected List<String> annoObjectSetterPropNameList;
    /**
     * DO/Entity对象对应的getter取值属性名列表
     */
    protected List<String> referencedGetterColumnNameList;

    /***
     * 构造方法
     * @param serviceInstance
     * @param voList
     */
    public FieldBinder(IService<T> serviceInstance, List voList) {
        super(serviceInstance, voList);
    }

    /***
     * 指定VO绑定属性赋值的setter和DO/Entity取值的getter方法
     * @param toVoSetter VO中调用赋值的setter方法
     * @param <T1> VO类型
     * @param <T2> DO类型
     * @param <R> set方法参数类型
     * @return
     */
    public <T1, T2, R> FieldBinder<T> link(IGetter<T2> fromDoGetter, ISetter<T1, R> toVoSetter) {
        return link(BeanUtil.convertToFieldName(fromDoGetter), BeanUtil.convertToFieldName(toVoSetter));
    }

    /***
     * 指定VO绑定赋值的setter属性名和DO/Entity取值的getter属性名
     * @param toVoField VO中调用赋值的setter属性名
     * @return
     */
    public FieldBinder<T> link(String fromDoField, String toVoField) {
        if (annoObjectSetterPropNameList == null) {
            annoObjectSetterPropNameList = new ArrayList<>();
        }
        annoObjectSetterPropNameList.add(toVoField);
        if (referencedGetterColumnNameList == null) {
            referencedGetterColumnNameList = new ArrayList<>();
        }
        referencedGetterColumnNameList.add(StringUtil.toSnakeCase(fromDoField));
        return this;
    }

    @Override
    public void bind() {
        if (Validator.isEmpty(annoObjectList)) {
            return;
        }
        if (referencedGetterColumnNameList == null) {
            log.error("调用错误：字段绑定必须调用link()指定字段赋值和取值的setter/getter方法！");
            return;
        }
        // 解析默认主键字段名
        String referencedEntityPkName = StringUtil.toSnakeCase(referencedEntityPrimaryKey);
        String annoObjectFkFieldName = StringUtil.toLowerCaseCamel(annoObjectForeignKey);
        // 提取主键pk列表
        List annoObjectForeignKeyList = BeanUtil.collectToList(annoObjectList, annoObjectFkFieldName);
        if (Validator.isEmpty(annoObjectForeignKeyList)) {
            return;
        }
        // 将结果list转换成map
        Map<String, Object> middleTableResultMap = null;
        //@BindField(entity = Organization.class, field="name", condition="this.department_id=department.id AND department.org_id=id")
        //String orgName;
        if (middleTable != null) {
            middleTableResultMap = middleTable.executeOneToOneQuery(annoObjectForeignKeyList);
            if (Validator.notEmpty(middleTableResultMap)) {
                // 收集查询结果values集合
                Collection middleTableColumnValueList = middleTableResultMap.values();
                // 构建查询条件
                List<String> selectColumns = new ArrayList<>(referencedGetterColumnNameList.size() + 1);
                selectColumns.add(referencedEntityPkName);
                selectColumns.addAll(referencedGetterColumnNameList);
                queryWrapper.select(StringUtil.toStringArray(selectColumns)).in(StringUtil.toSnakeCase(referencedEntityPrimaryKey), middleTableColumnValueList);
            } else {
                return;
            }
        } else {
            // 构建查询条件
            List<String> selectColumns = new ArrayList<>(referencedGetterColumnNameList.size() + 1);
            selectColumns.add(referencedEntityPkName);
            selectColumns.addAll(referencedGetterColumnNameList);
            queryWrapper.select(StringUtil.toStringArray(selectColumns)).in(referencedEntityPkName, annoObjectForeignKeyList);
        }

        // 获取匹配结果的mapList
        List<Map<String, Object>> mapList = getMapList(queryWrapper);
        if (Validator.isEmpty(mapList)) {
            return;
        }
        // 将结果list转换成map
        Map<String, Map<String, Object>> referencedEntityPk2DataMap = new HashMap<>(mapList.size());
        for (Map<String, Object> map : mapList) {
            Object valObj = getValueIgnoreKeyCase(map, referencedEntityPkName);
            if (valObj != null) {
                referencedEntityPk2DataMap.put(StringUtil.valueOf(valObj), map);
            }
        }
        // 遍历list并赋值
        for (Object annoObject : annoObjectList) {
            // 将数子类型转换成字符串，以便解决类型不一致的问题
            String annoObjectId = BeanUtil.getStringProperty(annoObject, annoObjectFkFieldName);
            // 通过中间结果Map转换得到OrgId
            if (Validator.notEmpty(middleTableResultMap)) {
                Object value = middleTableResultMap.get(annoObjectId);
                annoObjectId = String.valueOf(value);
            }
            Map<String, Object> relationMap = referencedEntityPk2DataMap.get(annoObjectId);
            if (relationMap != null) {
                for (int i = 0; i < annoObjectSetterPropNameList.size(); i++) {
                    Object valObj = getValueIgnoreKeyCase(relationMap, referencedGetterColumnNameList.get(i));
                    BeanUtil.setProperty(annoObject, annoObjectSetterPropNameList.get(i), valObj);
                }
            }
        }

    }

}
