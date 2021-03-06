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
package com.laiyefei.project.infrastructure.original.soil.whole.shell.aid;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-10-02 18:09
 * @Desc : 内核配置文件类
 * @Version : v1.0.0.20201002
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@ConfigurationProperties(prefix = "project.original.soil.kernel")
public class KernelProperties implements IAid {

    /**
     * 是否初始化，默认true自动安装SQL
     */
    private boolean initSql = true;

    public boolean isInitSql() {
        return initSql;
    }

    public void setInitSql(boolean initSql) {
        this.initSql = initSql;
    }
}
