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

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.utensil.util.IUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : JSON 工具类
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class JSON extends JSONObject implements IUtil {
    private static final Logger log = LoggerFactory.getLogger(JSON.class);

    /**
     * 序列化配置
     */
    private static SerializeConfig serializeConfig = new SerializeConfig();

    static {
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer(DateUtil.FORMAT_DATETIME_Y4MDHM));
    }

    /**
     * 将Java对象转换为Json String
     *
     * @param object
     * @return
     */
    public static String stringify(Object object) {
        return toJSONString(object, serializeConfig);
    }

    /***
     * 将JSON字符串转换为java对象
     * @param jsonStr
     * @return
     */
    public static Map toMap(String jsonStr) {
        return parseObject(jsonStr);
    }

    /***
     * 将JSON字符串转换为java对象
     * @param jsonStr
     * @return
     */
    public static LinkedHashMap toLinkedHashMap(String jsonStr) {
        if (Validator.isEmpty(jsonStr)) {
            return null;
        }
        return toJavaObject(jsonStr, LinkedHashMap.class);
    }

    /***
     * 将JSON字符串转换为java对象
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> T toJavaObject(String jsonStr, Class<T> clazz) {
        return JSONObject.parseObject(jsonStr, clazz);
    }

}