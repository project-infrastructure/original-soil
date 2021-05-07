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
package com.laiyefei.project.original.soil.whole.kernel.pojo.ao;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.ao.Ao;
import com.laiyefei.project.original.soil.whole.kernel.pojo.co.CheckpointType;

import java.lang.annotation.*;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-10-02 18:09
 * @Desc : 数据权限检查点 - 添加在entity/dto字段上的注解，可以支持自动检查数据权限
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Ao
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface DataAccessCheckpoint {
    /**
     * 数据权限类型
     *
     * @return
     */
    CheckpointType type();
}