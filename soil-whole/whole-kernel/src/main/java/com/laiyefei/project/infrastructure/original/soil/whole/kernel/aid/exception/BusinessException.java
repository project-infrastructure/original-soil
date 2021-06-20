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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.exception;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.dto.Status;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 通用的业务异常类 BusinessException，(json形式返回值同JsonResult，便于前端统一处理)
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class BusinessException extends RuntimeException implements IAid {

    /**
     * 错误的状态
     */
    private Status status;

    /**
     * 默认：操作失败
     */
    public BusinessException() {
        super(Status.FAIL_OPERATION.getDescription());
        this.status = Status.FAIL_OPERATION;
    }

    /**
     * 自定义状态码
     *
     * @param status
     */
    public BusinessException(Status status) {
        super(status.getDescription());
        this.status = status;
    }

    /**
     * 自定义状态码和异常
     *
     * @param status
     */
    public BusinessException(Status status, Throwable ex) {
        super(status.getDescription(), ex);
        this.status = status;
    }

    /**
     * 自定义状态码和内容提示
     *
     * @param status
     * @param msg
     */
    public BusinessException(Status status, String msg) {
        super(status.getDescription() + ": " + msg);
        this.status = status;
    }

    /**
     * 自定义内容提示
     *
     * @param msg
     */
    public BusinessException(String msg) {
        super(msg);
    }

    /**
     * 自定义内容提示
     *
     * @param status
     * @param msg
     */
    public BusinessException(Status status, String msg, Throwable ex) {
        super(status.getDescription() + ": " + msg, ex);
        this.status = status;
    }

    /**
     * 转换为Map
     *
     * @return
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", status.getIntCode());
        map.put("msg", getMessage());
        return map;
    }

    /**
     * 获取status，以便复用
     *
     * @return
     */
    public Status getStatus() {
        return this.status;
    }
}
