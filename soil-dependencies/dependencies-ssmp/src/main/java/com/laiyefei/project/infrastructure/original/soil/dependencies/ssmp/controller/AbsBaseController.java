package com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laiyefei.project.infrastructure.original.soil.common.controller.IBaseController;
import com.laiyefei.project.infrastructure.original.soil.common.pojo.vo.VPage;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.central.service.IBaseService;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.pojo.po.AbsBasePo;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.dto.AbsDto;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.fo.IHolder;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.JudgeUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Supplier;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 基础控制器
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class AbsBaseController<DTO extends AbsDto, PO extends AbsBasePo<DTO>> implements IBaseController<DTO> {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected IBaseService<DTO, PO> baseService;

    @PostConstruct
    private void afterCheck() {
        if (JudgeUtil.IsNull(this.baseService)) {
            throw new RuntimeException("error: the baseService is null.");
        }
    }

    @Override
    public IHolder.IExecuteOk addFunction(DTO dto) {
        return () -> {
            return this.baseService.save(dto);
        };
    }

    @Override
    public IHolder.IExecuteOk deleteFunction(String id) {
        return () -> {
            return this.baseService.removeById(id);
        };
    }

    @Override
    public Supplier<DTO> getFunction(String id) {
        return () -> {
            final PO po = this.baseService.getById(id);
            if (JudgeUtil.IsNull(po)) {
                return ObjectUtil.GetNULL();
            }
            return po.buildDto();
        };
    }

    @Override
    public Supplier<List<DTO>> listFunction(DTO condition) {
        return () -> {
            return this.baseService.list(condition);
        };
    }

    @Override
    public Supplier<VPage> pageFunction(Integer index, Integer size, DTO condition) {
        return () -> {
            Page<PO> page = this.baseService.page(new Page<>(index, size),
                    Wrappers.query(this.baseService.acceptDto(condition)));
            List<DTO> dtos = this.baseService.buildDtos(page.getRecords());
            return new VPage(dtos, page.getTotal());
        };
    }

    @Override
    public IHolder.IExecuteOk updateFunction(DTO dto) {
        return () -> {
            return this.baseService.updateById(dto);
        };
    }
}
