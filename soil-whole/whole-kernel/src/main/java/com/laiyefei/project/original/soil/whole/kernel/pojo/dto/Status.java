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
package com.laiyefei.project.original.soil.whole.kernel.pojo.dto;

import com.laiyefei.project.infrastructure.standard.java.foundation.pojo.co.ICo;
import lombok.Getter;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 状态码定义
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Getter
public enum Status implements ICo {
    /***
     * 请求处理成功
     */
    OK(0, "操作成功"),

    /***
     * 部分成功（一般用于批量处理场景，只处理筛选后的合法数据）
     */
    WARN_PARTIAL_SUCCESS(1001, "部分成功"),

    /***
     * 有潜在的性能问题
     */
    WARN_PERFORMANCE_ISSUE(1002, "潜在的性能问题"),

    /***
     * 传入参数不对
     */
    FAIL_INVALID_PARAM(4000, "请求参数不匹配"),

    /***
     * Token无效或已过期
     */
    FAIL_INVALID_TOKEN(4001, "Token无效或已过期"),

    /***
     * 没有权限执行该操作
     */
    FAIL_NO_PERMISSION(4003, "无权执行该操作"),

    /***
     * 请求资源不存在
     */
    FAIL_NOT_FOUND(4004, "请求资源不存在"),

    /***
     * 数据校验不通过
     */
    FAIL_VALIDATION(4005, "数据校验不通过"),

    /***
     * 操作执行失败
     */
    FAIL_OPERATION(4006, "操作执行失败"),

    /***
     * 系统异常
     */
    FAIL_EXCEPTION(5000, "系统异常"),

    /***
     * 缓存清空
     */
    MEMORY_EMPTY_LOST(9999, "缓存清空");

    private final String code;
    private final String description;

    Status(int code, String description) {
        this.code = String.valueOf(code);
        this.description = description;
    }

    public Integer getIntCode() {
        return Integer.valueOf(code);
    }
}