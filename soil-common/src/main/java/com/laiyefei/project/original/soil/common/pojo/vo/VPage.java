package com.laiyefei.project.original.soil.common.pojo.vo;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.vo.IVo;
import com.laiyefei.project.original.soil.standard.spread.foundation.pojo.dto.AbsDto;
import com.laiyefei.project.original.soil.standard.spread.foundation.pojo.vo.IVPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 分页对象
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@ApiModel("分页对象")
public class VPage implements IVo, IVPage {
    @ApiModelProperty("分页记录")
    protected final List<? extends AbsDto> records;
    @ApiModelProperty("总数")
    protected final long total;

    public VPage(List<? extends AbsDto> records, long total) {
        this.records = records;
        this.total = total;
    }

    public List<? extends AbsDto> getRecords() {
        return records;
    }

    public long getTotal() {
        return total;
    }
}
