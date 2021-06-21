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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.po.IPo;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.JSON;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.Validator;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 附带extdata扩展字段的Entity父类 （已废弃）
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Deprecated
public abstract class BaseExtPo extends BasePo implements IPo {
    private static final long serialVersionUID = 10204L;

    @TableField
    private String extdata; //扩展数据

    @TableField(exist = false)
    private Map<String, Object> extdataMap;

    public String getExtdata() {
        if (Validator.isEmpty(this.extdataMap)) {
            return null;
        }
        return JSON.toJSONString(this.extdataMap);
    }

    public BaseExtPo setExtdata(String extdata) {
        if (Validator.notEmpty(extdata)) {
            this.extdataMap = JSON.toLinkedHashMap(extdata);
        }
        return this;
    }

    /***
     * 从extdata JSON中提取扩展属性值
     * @param extAttrName
     * @return
     */
    public Object getFromExt(String extAttrName) {
        if (this.extdataMap == null) {
            return null;
        }
        return this.extdataMap.get(extAttrName);
    }

    /***
     * 添加扩展属性和值到extdata JSON中
     * @param extAttrName
     * @param extAttrValue
     */
    public BaseExtPo addIntoExt(String extAttrName, Object extAttrValue) {
        if (extAttrName == null && extAttrValue == null) {
            return this;
        }
        if (this.extdataMap == null) {
            this.extdataMap = new LinkedHashMap<>();
        }
        this.extdataMap.put(extAttrName, extAttrValue);
        return this;
    }

}
