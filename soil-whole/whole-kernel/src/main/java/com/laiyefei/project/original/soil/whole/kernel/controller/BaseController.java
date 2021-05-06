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
package com.laiyefei.project.original.soil.whole.kernel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.laiyefei.project.infrastructure.original.soil.standard.adaptive.controller.IController;
import com.laiyefei.project.original.soil.whole.kernel.aid.binding.Binder;
import com.laiyefei.project.original.soil.whole.kernel.aid.binding.QueryBuilder;
import com.laiyefei.project.original.soil.whole.kernel.pojo.co.Constant;
import com.laiyefei.project.original.soil.whole.kernel.tools.util.StringUtil;
import com.laiyefei.project.original.soil.whole.kernel.tools.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : Controller的父类
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class BaseController implements IController {
    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected HttpServletRequest request;

    /***
     * 构建查询QueryWrapper (根据BindQuery注解构建相应的查询条件)
     * @param entityOrDto Entity对象或者DTO对象 (属性若无BindQuery注解，默认构建为为EQ相等条件)
     * @return
     */
    public <DTO> QueryWrapper<DTO> buildQueryWrapper(DTO entityOrDto) throws Exception {
        if (entityOrDto instanceof HttpServletRequest) {
            throw new Exception("参数错误：buildQueryWrapper()参数为Entity/DTO对象！");
        }
        return QueryBuilder.toQueryWrapper(entityOrDto, extractQueryParams());
    }

    /***
     * 构建查询LambdaQueryWrapper (根据BindQuery注解构建相应的查询条件)
     * @param entityOrDto Entity对象或者DTO对象 (属性若无BindQuery注解，默认构建为为EQ相等条件)
     * @return
     */
    public <DTO> LambdaQueryWrapper<DTO> buildLambdaQueryWrapper(DTO entityOrDto) throws Exception {
        if (entityOrDto instanceof HttpServletRequest) {
            throw new Exception("参数错误：buildQueryWrapper()参数为Entity/DTO对象！");
        }
        return QueryBuilder.toLambdaQueryWrapper(entityOrDto, extractQueryParams());
    }

    /***
     * 获取请求参数Map
     * @return
     */
    public Map<String, Object> getParamsMap() throws Exception {
        return getParamsMap(null);
    }

    /***
     * 获取请求参数Map
     * @return
     */
    private Map<String, Object> getParamsMap(List<String> paramList) throws Exception {
        Map<String, Object> result = new HashMap<>(8);
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            // 如果非要找的参数，则跳过
            if (Validator.notEmpty(paramList) && !paramList.contains(paramName)) {
                continue;
            }
            String[] values = request.getParameterValues(paramName);
            if (Validator.notEmpty(values)) {
                if (values.length == 1) {
                    if (Validator.notEmpty(values[0])) {
                        String paramValue = java.net.URLDecoder.decode(values[0], Constant.CHARSET_UTF8);
                        result.put(paramName, paramValue);
                    }
                } else {
                    String[] valueArray = new String[values.length];
                    for (int i = 0; i < values.length; i++) {
                        valueArray[i] = java.net.URLDecoder.decode(values[i], Constant.CHARSET_UTF8);
                    }
                    // 多个值需传递到后台SQL的in语句
                    result.put(paramName, valueArray);
                }
            }
        }

        return result;
    }

    /***
     * 获取请求URI (去除contextPath)
     * @return
     */
    protected String getRequestMappingURI() {
        String contextPath = request.getContextPath();
        if (Validator.notEmpty(contextPath)) {
            return StringUtil.replace(request.getRequestURI(), contextPath, "");
        }
        return request.getRequestURI();
    }

    /**
     * 提取请求参数名集合
     *
     * @return
     */
    protected Set<String> extractQueryParams() {
        Map<String, Object> paramValueMap = convertParams2Map();
        if (Validator.notEmpty(paramValueMap)) {
            return paramValueMap.keySet();
        }
        return Collections.EMPTY_SET;
    }

    /***
     * 将请求参数值转换为Map
     * @return
     */
    protected Map<String, Object> convertParams2Map() {
        Map<String, Object> result = new HashMap<>(8);
        if (request == null) {
            return result;
        }
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] values = request.getParameterValues(paramName);
            if (Validator.notEmpty(values)) {
                if (values.length == 1) {
                    if (Validator.notEmpty(values[0])) {
                        result.put(paramName, values[0]);
                    }
                } else {
                    // 多个值需传递到后台SQL的in语句
                    result.put(paramName, values);
                }
            }
        }
        return result;
    }

    /**
     * 自动转换为VO并绑定关联关系
     *
     * @param entityList
     * @param voClass
     * @param <VO>
     * @return
     */
    protected <VO> List<VO> convertToVoAndBindRelations(List entityList, Class<VO> voClass) {
        // 转换为VO
        List<VO> voList = Binder.convertAndBindRelations(entityList, voClass);
        return voList;
    }

    /***
     * 打印所有参数信息
     */
    protected void dumpParams() {
        Map<String, String[]> params = request.getParameterMap();
        if (params != null && !params.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                String[] values = entry.getValue();
                if (values != null && values.length > 0) {
                    sb.append(entry.getKey() + "=" + StringUtil.join(values) + "; ");
                }
            }
            log.debug(sb.toString());
        }
    }

    /**
     * 从request获取Long参数
     *
     * @param param
     * @return
     */
    protected Long getLong(String param) {
        return StringUtil.toLong(request.getParameter(param));
    }

    /**
     * 从request获取Long参数
     *
     * @param param
     * @param defaultValue
     * @return
     */
    protected long getLong(String param, Long defaultValue) {
        return StringUtil.toLong(request.getParameter(param), defaultValue);
    }

    /**
     * 从request获取Int参数
     *
     * @param param
     * @return
     */
    protected Integer getInteger(String param) {
        return StringUtil.toInt(request.getParameter(param));
    }

    /**
     * 从request获取Int参数
     *
     * @param param
     * @param defaultValue
     * @return
     */
    protected int getInt(String param, Integer defaultValue) {
        return StringUtil.toInt(request.getParameter(param), defaultValue);
    }

    /***
     * 从request中获取boolean值
     * @param param
     * @return
     */
    protected boolean getBoolean(String param) {
        return StringUtil.toBoolean(request.getParameter(param));
    }

    /***
     * 从request中获取boolean值
     * @param param
     * @param defaultBoolean
     * @return
     */
    protected boolean getBoolean(String param, boolean defaultBoolean) {
        return StringUtil.toBoolean(request.getParameter(param), defaultBoolean);
    }

    /**
     * 从request获取Double参数
     *
     * @param param
     * @return
     */
    protected Double getDouble(String param) {
        if (Validator.notEmpty(request.getParameter(param))) {
            return Double.parseDouble(request.getParameter(param));
        }
        return null;
    }

    /**
     * 从request获取Double参数
     *
     * @param param
     * @param defaultValue
     * @return
     */
    protected Double getDouble(String param, Double defaultValue) {
        if (Validator.notEmpty(request.getParameter(param))) {
            return Double.parseDouble(request.getParameter(param));
        }
        return defaultValue;
    }

    /**
     * 从request获取String参数
     *
     * @param param
     * @return
     */
    protected String getString(String param) {
        if (Validator.notEmpty(request.getParameter(param))) {
            return request.getParameter(param);
        }
        return null;
    }

    /**
     * 从request获取String参数
     *
     * @param param
     * @param defaultValue
     * @return
     */
    protected String getString(String param, String defaultValue) {
        if (Validator.notEmpty(request.getParameter(param))) {
            return request.getParameter(param);
        }
        return defaultValue;
    }

    /**
     * 从request获取String[]参数
     *
     * @param param
     * @return
     */
    protected String[] getStringArray(String param) {
        if (request.getParameterValues(param) != null) {
            return request.getParameterValues(param);
        }
        return null;
    }

    /***
     * 从request里获取String列表
     * @param param
     * @return
     */
    protected List<String> getStringList(String param) {
        String[] strArray = getStringArray(param);
        if (Validator.isEmpty(strArray)) {
            return null;
        }
        return Arrays.asList(strArray);
    }

    /***
     * 从request里获取Long列表
     * @param param
     * @return
     */
    protected List<Long> getLongList(String param) {
        String[] strArray = getStringArray(param);
        if (Validator.isEmpty(strArray)) {
            return null;
        }
        List<Long> longList = new ArrayList<>();
        for (String str : strArray) {
            if (Validator.notEmpty(str)) {
                longList.add(Long.parseLong(str));
            }
        }
        return longList;
    }
}