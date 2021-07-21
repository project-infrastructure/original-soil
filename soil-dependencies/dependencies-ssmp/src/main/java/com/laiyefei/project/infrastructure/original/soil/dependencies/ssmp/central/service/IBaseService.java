package com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.central.service;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.pojo.po.AbsBasePo;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.dto.AbsDto;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.eo.ServiceException;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.JudgeUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 基础服务接口
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IBaseService<DTO extends AbsDto, PO extends AbsBasePo<DTO>> extends IService<PO> {
    PO acceptDto(DTO dto);

    default List<PO> acceptDto(List<DTO> dtos) {
        return Optional.of(dtos)
                .orElse(new ArrayList<>())
                .stream().map(e -> this.acceptDto(e))
                .collect(Collectors.toList());
    }

    default List<DTO> buildDtos(final List<PO> pos) {
        return Optional.of(pos)
                .orElse(new ArrayList<>())
                .stream().map(e -> e.buildDto())
                .collect(Collectors.toList());
    }

    default boolean save(DTO entity) {
        return this.save(this.acceptDto(entity));
    }

    default boolean saveOrUpdate(DTO entity, boolean insert) {
        return insert ? this.save(entity) : this.updateById(entity);
    }

    @Transactional(rollbackFor = ServiceException.class)
    default boolean save(List<DTO> list) {
        return this.saveBatch(list.stream().map(this::acceptDto).collect(Collectors.toList()));
    }

    default boolean save(Collection<DTO> list, int batchSize) {
        return this.saveBatch(list.stream().map(this::acceptDto).collect(Collectors.toList()), batchSize);
    }

    default boolean saveOrUpdate(Collection<DTO> list) {
        return this.saveOrUpdateBatch(list.stream().map(this::acceptDto).collect(Collectors.toList()));
    }

    default boolean saveOrUpdate(Collection<DTO> list, int batchSize) {
        return this.saveOrUpdateBatch(list.stream().map(this::acceptDto).collect(Collectors.toList()), batchSize);
    }

    default boolean updateById(DTO dto) {
        return this.updateById(this.acceptDto(dto));
    }

    default List<DTO> list(DTO condition) {
        return Optional.of(this.list(Wrappers.query(this.acceptDto(condition))))
                .orElse(new ArrayList<>())
                .stream().map(e -> e.buildDto())
                .collect(Collectors.toList());
    }


    default boolean ensureExists(PO po) {
        return ensureExists(po, po);
    }

    default boolean ensureExists(PO condition, PO dump) {
        Assert.notNull(condition, "error: sorry, the condition is null.");
        Assert.notNull(dump, "error: sorry, the dump is null.");
        final PO poResult = this.getOne(Wrappers.query(condition));
        if (JudgeUtil.IsNotNULL(poResult)) {
            return true;
        }
        return this.save(dump);
    }
}
