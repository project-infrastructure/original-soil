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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.prepper;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.prepper.IPrepper;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 系统默认配置
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class SystemConfig implements IPrepper {
    private static final Logger log = LoggerFactory.getLogger(SystemConfig.class);

    /**
     * 从当前配置文件获取配置参数值
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return PropertiesUtil.get(key);
    }

    /**
     * 从当前配置文件获取配置参数值
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static String getProperty(String key, String defaultValue) {
        String value = PropertiesUtil.get(key);
        return value != null ? value : defaultValue;
    }

    /***
     *  从默认的/指定的 Properties文件获取boolean值
     * @param key
     * @return
     */
    public static boolean isTrue(String key) {
        return PropertiesUtil.getBoolean(key);
    }

    /***
     * 获取int类型
     * @param key
     * @return
     */
    public static Integer getInteger(String key) {
        return PropertiesUtil.getInteger(key);
    }

    /***
     * 获取int类型
     * @param key
     * @return
     */
    public static Integer getInteger(String key, int defaultValue) {
        Integer value = PropertiesUtil.getInteger(key);
        return value != null ? value : defaultValue;
    }

    /***
     * 获取截取长度
     * @return
     */
    public static int getCutLength() {
        Integer length = PropertiesUtil.getInteger("system.default.cutLength");
        if (length != null) {
            return length;
        }
        return 20;
    }

    /***
     * 默认页数
     * @return
     */
    public static int getPageSize() {
        Integer length = PropertiesUtil.getInteger("system.pagination.pageSize");
        if (length != null) {
            return length;
        }
        return 20;
    }

    /***
     * 获取批量插入的每批次数量
     * @return
     */
    public static int getBatchSize() {
        Integer length = PropertiesUtil.getInteger("system.batch.size");
        if (length != null) {
            return length;
        }
        return 1000;
    }

    private static String ACTIVE_FLAG_VALUE = null;

    /**
     * 获取有效记录的标记值，如 0
     *
     * @return
     */
    public static String getActiveFlagValue() {
        if (ACTIVE_FLAG_VALUE == null) {
            ACTIVE_FLAG_VALUE = getProperty("mybatis-plus.global-config.db-config.logic-not-delete-value", "0");
        }
        return ACTIVE_FLAG_VALUE;
    }
}