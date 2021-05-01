package com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.po;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.IPojo;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.dto.IDto;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.util.AssertUtil;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 持久化对象接口约束
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IPo<DTO extends IDto> extends IPojo {

    /**
     * 创建数据传输对象
     *
     * @return
     */
    DTO buildDto();


    /**
     * 接受dto转换成po
     *
     * @param dto
     * @param <DTO>
     * @return
     */
    static <DTO extends IDto> IPo<DTO> AcceptWith(final DTO dto) {
        throw new RuntimeException("error: no impl yet.");
    }

    /**
     * 接受dto转换成po
     *
     * @param dtos
     * @param <DTO>
     * @return
     */
    static <DTO extends IDto> List<IPo<DTO>> AcceptWith(final List<DTO> dtos) {
        AssertUtil.notNull(dtos, "error: dtos is null");
        return dtos.stream().map(e -> AcceptWith(e)).collect(Collectors.toList());
    }
}
