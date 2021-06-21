package com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.pojo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.pojo.po.AbsBasePo;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.JudgeUtil;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 基础操作对象
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Mapper
public interface IBaseDao<PO extends AbsBasePo> extends BaseMapper<PO> {
    default boolean ensureExists(PO po) {
        final PO poResult = this.selectOne(Wrappers.query(po));
        if (JudgeUtil.IsNotNULL(poResult)) {
            return true;
        }
        return 0 < this.insert(po);
    }
}
