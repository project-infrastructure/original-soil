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
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.dto.IDto;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.po.IPo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 数据字典实体
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Getter
@Setter
@Accessors(chain = true)
public class Dictionary extends BaseExtPo implements IPo {
    private static final long serialVersionUID = 11301L;

    /***
     * 上级ID
     */
    @NotNull(message = "上级ID不能为空，如无请设为0")
    @TableField
    private Long parentId = 0L;

    /***
     * 数据字典类型
     */
    @NotNull(message = "数据字典类型不能为空！")
    @Length(max = 50, message = "数据字典类型长度超长！")
    @TableField
    private String type;

    /***
     * 数据字典项的显示名称
     */
    @NotNull(message = "数据字典项名称不能为空！")
    @Length(max = 100, message = "数据字典项名称长度超长！")
    @TableField
    private String itemName;

    /***
     * 数据字典项的存储值（编码）
     */
    @Length(max = 100, message = "数据字典项编码长度超长！")
    @TableField
    private String itemValue;

    /***
     * 备注信息
     */
    @Length(max = 200, message = "数据字典备注长度超长！")
    @TableField
    private String description;

    /***
     * 排序号
     */
    @TableField
    private Integer sortId;

    /***
     * 是否为系统预置（预置不可删除）
     */
    @TableField("is_deletable")
    private boolean deletable = false;

    /***
     * 是否可编辑
     */
    @TableField("is_editable")
    private boolean editable = false;

    @Override
    public IDto buildDto() {
        throw new RuntimeException("error: no impl.");
    }
}
