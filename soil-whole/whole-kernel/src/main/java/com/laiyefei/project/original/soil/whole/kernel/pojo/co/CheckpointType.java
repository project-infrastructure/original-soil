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
package com.laiyefei.project.original.soil.whole.kernel.pojo.co;

import com.laiyefei.project.infrastructure.standard.java.foundation.pojo.co.ICo;
import lombok.Getter;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-10-02 18:09
 * @Desc : checkpoint类型
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Getter
public enum CheckpointType implements ICo {

    USER(0, "用户范围"),
    ORG(1, "组织范围"),
    POSITION(2, "岗位范围"),
    EXT_OBJ(3, "扩展对象范围");

    private final String code;
    private final String description;

    CheckpointType(int code, String description) {
        this.code = String.valueOf(code);
        this.description = description;
    }

    public int index() {
        return Integer.valueOf(this.code);
    }
}