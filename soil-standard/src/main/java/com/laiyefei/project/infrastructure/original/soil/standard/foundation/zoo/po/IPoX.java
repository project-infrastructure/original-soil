package com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.po;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.dto.IDtoX;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 持久化对象接口约束
 * @Version : v2.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IPoX<DTO extends IDtoX<? extends IPoX<DTO>>> extends IPo {

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
    static <DTO extends IDtoX<? extends IPoX<DTO>>> IPoX<DTO> AcceptWith(final DTO dto) {
        return dto.buildPo();
    }

    /**
     * 接受dto转换成po
     *
     * @param dtos
     * @param <DTO>
     * @return
     */
    static <DTO extends IDtoX<? extends IPoX<DTO>>> List<IPoX<DTO>> AcceptWith(final List<DTO> dtos) {
        if (null == dtos) {
            return new ArrayList<>();
        }
        return dtos.stream().map(e -> AcceptWith(e)).collect(Collectors.toList());
    }
}
