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


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co.ICo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 比较条件枚举类
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@RequiredArgsConstructor
@Getter
public enum Comparison implements ICo {
    EQ("1", "相等，默认"),
    IN("2", "IN"),

    STARTSWITH("3", "以xx起始"),

    LIKE("4", "LIKE"),
    CONTAINS("5", "包含，等同LIKE"),

    GT("6", "大于"),
    GE("7", "大于等于"),
    LT("8", "小于"),
    LE("9", "小于等于"),

    BETWEEN("10", "介于-之间"),
    BETWEEN_BEGIN("11", "介于之后"),
    BETWEEN_END("12", "介于之前");

    private final String code;
    private final String description;
}
