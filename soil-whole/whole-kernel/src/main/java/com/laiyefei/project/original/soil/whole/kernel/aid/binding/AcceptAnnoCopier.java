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

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;
import com.laiyefei.project.original.soil.whole.kernel.pojo.ao.Accept;
import com.laiyefei.project.original.soil.whole.kernel.tools.util.BeanUtil;
import com.laiyefei.project.original.soil.whole.kernel.tools.util.Validator;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : Accept注解拷贝器
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Slf4j
public class AcceptAnnoCopier implements IAid {
    /**
     * 注解缓存
     */
    private static Map<String, List<String[]>> CLASS_ACCEPT_ANNO_CACHE_MAP = new ConcurrentHashMap<>();
    // 下标
    private static final int IDX_TARGET_FIELD = 0, IDX_SOURCE_FIELD = 1, IDX_OVERRIDE = 2;

    /**
     * 基于注解拷贝属性
     *
     * @param source
     * @param target
     */
    public static void copyAcceptProperties(Object source, Object target) {
        String key = target.getClass().getName();
        // 初始化
        if (!CLASS_ACCEPT_ANNO_CACHE_MAP.containsKey(key)) {
            List<Field> annoFieldList = BeanUtil.extractFields(target.getClass(), Accept.class);
            if (Validator.isEmpty(annoFieldList)) {
                CLASS_ACCEPT_ANNO_CACHE_MAP.put(key, Collections.EMPTY_LIST);
            } else {
                List<String[]> annoDefList = new ArrayList<>(annoFieldList.size());
                for (Field fld : annoFieldList) {
                    Accept accept = fld.getAnnotation(Accept.class);
                    String[] annoDef = {fld.getName(), accept.name(), accept.override() ? "1" : "0"};
                    annoDefList.add(annoDef);
                }
                CLASS_ACCEPT_ANNO_CACHE_MAP.put(key, annoDefList);
            }
        }
        // 解析copy
        List<String[]> acceptAnnos = CLASS_ACCEPT_ANNO_CACHE_MAP.get(key);
        if (Validator.isEmpty(acceptAnnos)) {
            return;
        }
        for (String[] annoDef : acceptAnnos) {
            boolean override = !"0".equals(annoDef[IDX_OVERRIDE]);
            if (!override) {
                Object targetValue = BeanUtil.getProperty(target, annoDef[IDX_TARGET_FIELD]);
                if (targetValue != null) {
                    log.debug("目标对象{}已有值{}，copyAcceptProperties将忽略.", key, targetValue);
                    continue;
                }
            }
            Object sourceValue = BeanUtil.getProperty(source, annoDef[IDX_SOURCE_FIELD]);
            if (sourceValue != null) {
                BeanUtil.setProperty(target, annoDef[IDX_TARGET_FIELD], sourceValue);
            }
        }
    }

}
