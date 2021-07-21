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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.dto;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.dto.IDto;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.dto.AbsDto;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.StringUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.Validator;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : JSON返回结果
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class JsonResult extends AbsDto implements IDto {
    private static final long serialVersionUID = 1001L;

    /***
     * 状态码
     */
    private int code;
    /***
     * 消息内容
     */
    private String msg;
    /***
     * 返回结果数据
     */
    private Object data;

    /**
     * 默认成功，无返回数据
     */
    public JsonResult() {
    }

    /**
     * 默认成功，有返回数据
     */
    public JsonResult(Object data) {
        this.code = Status.OK.getIntCode();
        this.msg = Status.OK.getDescription();
        initMsg(null);
        this.data = data;
    }

    /**
     * 默认成功，有返回数据、及附加提示信息
     */
    public JsonResult(Object data, String additionalMsg) {
        this.code = Status.OK.getIntCode();
        this.msg = Status.OK.getDescription();
        initMsg(additionalMsg);
        this.data = data;
    }

    /***
     * 非成功，指定状态
     * @param status
     */
    public JsonResult(Status status) {
        this.code = status.getIntCode();
        this.msg = status.getDescription();
        initMsg(null);
        this.data = null;
    }

    /***
     * 非成功，指定状态及附加提示信息
     * @param status
     * @param additionalMsg
     */
    public JsonResult(Status status, String additionalMsg) {
        this.code = status.getIntCode();
        this.msg = status.getDescription();
        initMsg(additionalMsg);
        this.data = null;
    }

    /**
     * 非成功，指定状态、返回数据
     *
     * @param status
     * @param data
     */
    public JsonResult(Status status, Object data) {
        this.code = status.getIntCode();
        this.msg = status.getDescription();
        initMsg(null);
        this.data = data;
    }

    /**
     * 非成功，指定状态、返回数据、及附加提示信息
     */
    public JsonResult(Status status, Object data, String additionalMsg) {
        this.code = status.getIntCode();
        this.msg = status.getDescription();
        initMsg(additionalMsg);
        this.data = data;
    }

    /***
     * 自定义JsonResult
     * @param code
     * @param label
     * @param data
     */
    public JsonResult(int code, String label, Object data) {
        this.code = code;
        this.msg = label;
        this.data = data;
    }

    /**
     * 设置status，如果msg为空则msg设置为status.label
     *
     * @param status
     * @return
     */
    public JsonResult status(Status status) {
        this.code = status.getIntCode();
        if (this.msg == null) {
            this.msg = status.getDescription();
        }
        return this;
    }

    /**
     * 设置返回数据
     *
     * @param data
     * @return
     */
    public JsonResult data(Object data) {
        this.data = data;
        return this;
    }

    /**
     * 设置msg
     *
     * @param additionalMsg
     * @return
     */
    public JsonResult msg(String additionalMsg) {
        initMsg(additionalMsg);
        return this;
    }

    /***
     * 绑定分页信息
     * @param pagination
     */
    public JsonResult bindPagination(Pagination pagination) {
        return new PagingJsonResult(this, pagination);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    /**
     * 赋值msg（去掉重复前缀以支持与BusinessException嵌套使用）
     *
     * @param additionalMsg
     */
    private void initMsg(String additionalMsg) {
        if (Validator.notEmpty(additionalMsg)) {
            if (StringUtil.startsWith(additionalMsg, this.msg)) {
                this.msg = additionalMsg;
            } else {
                this.msg += ": " + additionalMsg;
            }
        }
    }

    /***
     * 请求处理成功
     */
    public static JsonResult OK() {
        return new JsonResult(Status.OK);
    }

    /***
     * 请求处理成功
     */
    public static JsonResult OK(Object data) {
        return new JsonResult(Status.OK, data);
    }

    /***
     * 部分成功（一般用于批量处理场景，只处理筛选后的合法数据）
     */
    public static JsonResult WARN_PARTIAL_SUCCESS(String msg) {
        return new JsonResult(Status.WARN_PARTIAL_SUCCESS).msg(msg);
    }

    /***
     * 有潜在的性能问题
     */
    public static JsonResult WARN_PERFORMANCE_ISSUE(String msg) {
        return new JsonResult(Status.WARN_PERFORMANCE_ISSUE).msg(msg);
    }

    /***
     * 传入参数不对
     */
    public static JsonResult FAIL_INVALID_PARAM(String msg) {
        return new JsonResult(Status.FAIL_INVALID_PARAM).msg(msg);
    }

    /***
     * Token无效或已过期
     */
    public static JsonResult FAIL_INVALID_TOKEN(String msg) {
        return new JsonResult(Status.FAIL_INVALID_TOKEN).msg(msg);
    }

    /***
     * 没有权限执行该操作
     */
    public static JsonResult FAIL_NO_PERMISSION(String msg) {
        return new JsonResult(Status.FAIL_NO_PERMISSION).msg(msg);
    }

    /***
     * 请求资源不存在
     */
    public static JsonResult FAIL_NOT_FOUND(String msg) {
        return new JsonResult(Status.FAIL_NOT_FOUND).msg(msg);
    }

    /***
     * 数据校验不通过
     */
    public static JsonResult FAIL_VALIDATION(String msg) {
        return new JsonResult(Status.FAIL_VALIDATION).msg(msg);
    }

    /***
     * 操作执行失败
     */
    public static JsonResult FAIL_OPERATION(String msg) {
        return new JsonResult(Status.FAIL_OPERATION).msg(msg);
    }

    /***
     * 系统异常
     */
    public static JsonResult FAIL_EXCEPTION(String msg) {
        return new JsonResult(Status.FAIL_EXCEPTION).msg(msg);
    }

    /***
     * 缓存清空
     */
    public static JsonResult MEMORY_EMPTY_LOST(String msg) {
        return new JsonResult(Status.MEMORY_EMPTY_LOST).msg(msg);
    }
}