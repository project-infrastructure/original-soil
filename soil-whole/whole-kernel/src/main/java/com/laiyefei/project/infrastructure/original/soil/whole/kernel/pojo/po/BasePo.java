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
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implieDateUtil. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.po.IPo;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.po.AbsPo;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.BeanUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.DateUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.JSON;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.ContextHelper;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.co.Constant;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Map;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : Po基础父类
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class BasePo extends AbsPo implements IPo {
    private static final long serialVersionUID = 10203L;

    /**
     * 默认主键字段id，类型为Long型自增，转json时转换为String
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 默认逻辑删除标记，is_deleted=0有效
     */
    @TableLogic
    @JSONField(serialize = false)
    @TableField(Constant.COLUMN_IS_DELETED)
    private boolean deleted = false;

    /**
     * 默认记录创建时间字段，新建时由数据库赋值
     */
    @JSONField(format = DateUtil.FORMAT_DATETIME_Y4MDHMS)
    @TableField(insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private Date createTime;

    /***
     * Entity对象转为map
     * @return
     */
    public Map<String, Object> toMap() {
        String jsonStr = JSON.stringify(this);
        return JSON.toMap(jsonStr);
    }

    /**
     * 获取主键值
     *
     * @return
     */
    @JSONField(serialize = false)
    public Object getPrimaryKey() {
        String pk = ContextHelper.getPrimaryKey(this.getClass());
        if (Constant.FieldName.id.name().equals(pk)) {
            return getId();
        }
        return BeanUtil.getProperty(this, pk);
    }

    /**
     * Entity对象转为String
     *
     * @return
     */
    @Override
    public String toString() {
        return this.getClass().getName() + ":" + this.getId();
    }
}