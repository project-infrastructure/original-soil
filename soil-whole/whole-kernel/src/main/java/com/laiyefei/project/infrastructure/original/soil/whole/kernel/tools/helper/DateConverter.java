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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.helper;

import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : Spring表单自动绑定到Java属性时的日期格式转换
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class DateConverter<T extends DateConverter.ISourceTransformBound> implements Converter<T, Date> {
    private static final Logger log = LoggerFactory.getLogger(DateConverter.class);

    interface ISourceTransformBound {
        String getString();
    }

    private final T source;

    public DateConverter(T source) {
        this.source = source;
    }

    @Override
    public Date convert(T source) {
        return DateUtil.fuzzyConvert(source.getString());
    }

    public Date convertByString() {
        return DateUtil.fuzzyConvert(source.getString());
    }
}