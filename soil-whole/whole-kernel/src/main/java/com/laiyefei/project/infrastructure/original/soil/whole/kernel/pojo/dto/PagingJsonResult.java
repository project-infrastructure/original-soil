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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.dto.IDto;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.StringUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.Validator;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.co.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : JSON返回结果
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class PagingJsonResult extends JsonResult implements IDto {
    private static final long serialVersionUID = 1001L;

    /***
     * 分页相关信息
     */
    private Pagination page;

    /**
     * 默认成功，无返回数据
     */
    public PagingJsonResult(JsonResult jsonResult, Pagination pagination) {
        super(jsonResult.getCode(), jsonResult.getMsg(), jsonResult.getData());
        this.page = pagination;
    }

    /**
     * 基于IPage<T>转换为PagingJsonResult
     *
     * @param iPage
     * @param <T>
     */
    public <T> PagingJsonResult(IPage<T> iPage) {
        Pagination pagination = new Pagination();
        pagination.setPageIndex((int) iPage.getCurrent());
        pagination.setPageSize((int) iPage.getSize());
        pagination.setTotalCount(iPage.getTotal());
        if (Validator.notEmpty(iPage.orders())) {
            List<String> orderByList = new ArrayList<>();
            iPage.orders().stream().forEach(o -> {
                if (o.isAsc()) {
                    orderByList.add(o.getColumn());
                } else {
                    orderByList.add(o.getColumn() + ":" + Constant.ORDER_DESC);
                }
            });
            pagination.setOrderBy(StringUtil.join(orderByList));
        }
        this.page = pagination;
        this.data(iPage.getRecords());
    }

    public Pagination getPage() {
        return page;
    }

}