package com.laiyefei.project.original.soil.dependencies.ssmp.central.core;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.laiyefei.project.original.soil.dependencies.ssmp.central.service.IBaseService;
import com.laiyefei.project.original.soil.dependencies.ssmp.foundation.pojo.dao.IBaseDao;
import com.laiyefei.project.original.soil.dependencies.ssmp.foundation.pojo.po.AbsBasePo;
import com.laiyefei.project.original.soil.standard.spread.foundation.pojo.dto.AbsDto;
import com.laiyefei.project.original.soil.standard.spread.foundation.tools.util.JudgeUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 基础服务核心实现
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class AbsBaseCoreImpl<DTO extends AbsDto, DAO extends IBaseDao<PO>, PO extends AbsBasePo<DTO>> extends ServiceImpl<DAO, PO> implements IBaseService<DTO, PO> {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected IBaseDao<PO> baseDao;

    @PostConstruct
    private void afterCheck() {
        if (JudgeUtil.IsNull(baseDao)) {
            throw new RuntimeException("error: the baseDao is null.");
        }
    }
}
