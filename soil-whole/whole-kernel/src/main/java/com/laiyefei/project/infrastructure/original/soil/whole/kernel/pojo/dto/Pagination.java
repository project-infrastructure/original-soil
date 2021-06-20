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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.dto.IDto;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.prepper.SystemConfig;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.StringUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.Validator;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.co.Constant;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 分页 (属性以下划线开头以避免与提交参数字段冲突)
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Getter
@Setter
@Accessors(chain = true)
public class Pagination implements IDto, Serializable {
    private static final Logger log = LoggerFactory.getLogger(Pagination.class);

    private static final long serialVersionUID = -4083929594112114522L;

    /***
     * 当前页
     */
    private int pageIndex = 1;
    /***
     * 默认每页数量10
     */
    private int pageSize = SystemConfig.getPageSize();
    /***
     * count总数
     */
    private long totalCount = 0;
    /**
     * 默认排序
     */
    private static final String DEFAULT_ORDER_BY = Constant.FieldName.id.name() + ":" + Constant.ORDER_DESC;

    /**
     * 排序
     */
    private String orderBy = DEFAULT_ORDER_BY;

    public Pagination() {
    }

    /***
     * 指定当前页数
     */
    public Pagination(int pageIndex) {
        setPageIndex(pageIndex);
    }

    public void setPageSize(int pageSize) {
        if (pageSize > 1000) {
            log.warn("分页pageSize过大，将被调整为默认限值，请检查调用是否合理！pageSize=" + pageSize);
            pageSize = 1000;
        }
        this.pageSize = pageSize;
    }

    /***
     * 获取总的页数
     * @return
     */
    public int getTotalPage() {
        if (totalCount <= 0) {
            return 0;
        }
        return (int) Math.ceil((float) totalCount / pageSize);
    }

    /**
     * 清空默认排序
     */
    public void clearDefaultOrder() {
        // 是否为默认排序
        if (isDefaultOrderBy()) {
            orderBy = null;
        }
    }

    /**
     * 是否为默认排序
     *
     * @return
     */
    @JSONField(serialize = false)
    public boolean isDefaultOrderBy() {
        return Validator.equals(orderBy, DEFAULT_ORDER_BY);
    }

    /**
     * 转换为IPage
     *
     * @param <T>
     * @return
     */
    public <T> Page<T> toPage() {
        List<OrderItem> orderItemList = null;
        // 解析排序
        if (Validator.notEmpty(this.orderBy)) {
            orderItemList = new ArrayList<>();
            // orderBy=shortName:DESC,age:ASC,birthdate
            String[] orderByFields = StringUtil.split(this.orderBy);
            for (String field : orderByFields) {
                if (field.contains(":")) {
                    String[] fieldAndOrder = StringUtil.split(field, ":");
                    String columnName = StringUtil.toSnakeCase(fieldAndOrder[0]);
                    if (Constant.ORDER_DESC.equalsIgnoreCase(fieldAndOrder[1])) {
                        orderItemList.add(OrderItem.desc(columnName));
                    } else {
                        orderItemList.add(OrderItem.asc(columnName));
                    }
                } else {
                    orderItemList.add(OrderItem.asc(StringUtil.toSnakeCase(field)));
                }
            }
        }
        Page<T> page = new Page<T>()
                .setCurrent(getPageIndex())
                .setSize(getPageSize())
                // 如果前端传递过来了缓存的总数，则本次不再count统计
                .setTotal(getTotalCount() > 0 ? -1 : getTotalCount());
        if (orderItemList != null) {
            page.addOrder(orderItemList);
        }
        return page;
    }

    /**
     * 当id不是主键的时候，默认使用创建时间排序
     *
     * @return
     */
    public String setDefaultCreateTimeOrderBy() {
        return this.orderBy = Constant.FieldName.createTime.name() + ":" + Constant.ORDER_DESC;
    }
}