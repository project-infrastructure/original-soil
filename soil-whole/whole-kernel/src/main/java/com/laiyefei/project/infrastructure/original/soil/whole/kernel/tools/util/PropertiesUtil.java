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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.util.IUtil;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.aid.ContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 配置文件工具类
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class PropertiesUtil implements IUtil {
    private static final Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

    /***
     *  读取配置项的值
     * @param key
     * @return
     */
    public static String get(String key) {
        // 获取配置值
        Environment environment = ContextHolder.getContext().getEnvironment();
        if (environment == null) {
            log.warn("无法获取上下文Environment，请在Spring初始化之后调用!");
            return null;
        }
        String value = environment.getProperty(key);
        // 任何password相关的参数需解密
        boolean isSensitiveConfig = key.contains(".password") || key.contains(".secret");
        if (value != null && isSensitiveConfig) {
            value = Encryptor.decrypt(value);
        }
        return value;
    }

    /***
     *  读取int型的配置项
     * @param key
     * @return
     */
    public static Integer getInteger(String key) {
        // 获取配置值
        String value = get(key);
        if (Validator.notEmpty(value)) {
            return Integer.parseInt(value);
        }
        return null;
    }

    /***
     * 读取boolean值的配置项
     */
    public static boolean getBoolean(String key) {
        // 获取配置值
        String value = get(key);
        if (Validator.notEmpty(value)) {
            return Validator.isTrue(value);
        }
        return false;
    }
}
