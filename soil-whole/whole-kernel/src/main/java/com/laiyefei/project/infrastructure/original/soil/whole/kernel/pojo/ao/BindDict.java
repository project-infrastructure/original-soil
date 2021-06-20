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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.ao;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.ao.Ao;

import java.lang.annotation.*;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-09-29 18:09
 * @Desc : 绑定字典注解
 * @Version : v1.0.0.20200929
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Ao
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BindDict {

    /***
     * 绑定数据字典类型
     * @return
     */
    String type();

    /***
     * 数据字典项取值字段
     * @return
     */
    String field() default "";
}