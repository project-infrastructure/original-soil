package com.laiyefei.project.infrastructure.original.soil.common.controller;

import com.laiyefei.project.infrastructure.original.soil.common.pojo.ao.Power;
import com.laiyefei.project.infrastructure.original.soil.common.pojo.co.AccessType;
import com.laiyefei.project.infrastructure.original.soil.common.pojo.co.PermissionType;
import com.laiyefei.project.infrastructure.original.soil.common.pojo.vo.VPage;
import com.laiyefei.project.infrastructure.original.soil.common.pojo.dto.BaseDto;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.dto.AbsDto;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.fo.IHolder;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 防腐层控制层接口约束
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IBaseController<DTO extends AbsDto> extends com.laiyefei.project.infrastructure.original.soil.standard.spread.adaptive.controller.IBaseController<DTO> {

    @Power(name = "增加", accessType = AccessType.Private, permissionType = PermissionType.Api)
    @ApiOperation("增加")
    @PostMapping(value = "/add", name = "增加")
    @ResponseBody
    default BaseDto add(@RequestBody DTO dto) {
        return BaseDto.BuildBy(addFunction(dto).done());
    }

    IHolder.IExecuteOk addFunction(DTO dto);

    @Power(name = "删除", accessType = AccessType.Private, permissionType = PermissionType.Api)
    @ApiOperation("删除")
    @DeleteMapping(value = "/{id}", name = "删除")
    @ResponseBody
    default BaseDto delete(@PathVariable("id") String id) {
        return BaseDto.BuildBy(deleteFunction(id).done());
    }

    IHolder.IExecuteOk deleteFunction(String id);

    @Power(name = "查询", accessType = AccessType.Public, permissionType = PermissionType.Api)
    @ApiOperation("查询")
    @GetMapping(value = "/{id}", name = "查询")
    @ResponseBody
    default BaseDto<DTO> get(@PathVariable("id") String id) {
        return BaseDto.BuildSuccessBy(getFunction(id));
    }

    Supplier<DTO> getFunction(String id);

    @Power(name = "列表查询", accessType = AccessType.Public, permissionType = PermissionType.Api)
    @ApiOperation("列表查询")
    @PostMapping(value = "/list", name = "列表查询")
    @ResponseBody
    default BaseDto<List<DTO>> list(@RequestBody DTO condition) {
        return BaseDto.BuildSuccessBy(listFunction(condition));
    }

    Supplier<List<DTO>> listFunction(DTO condition);

    @Power(name = "分页查询", accessType = AccessType.Public, permissionType = PermissionType.Api)
    @ApiOperation("分页查询")
    @PostMapping(value = "/page", name = "分页查询")
    @ResponseBody
    default BaseDto<VPage> page(
            @RequestParam(value = "i", defaultValue = "1") Integer index,
            @RequestParam(value = "s", defaultValue = "10") Integer size,
            @RequestBody DTO condition) {
        return BaseDto.BuildSuccessBy(pageFunction(index, size, condition));
    }

    Supplier<VPage> pageFunction(Integer index, Integer size, DTO condition);

    @Power(name = "更新", accessType = AccessType.Private, permissionType = PermissionType.Api)
    @ApiOperation("更新")
    @PutMapping(value = "/update", name = "更新")
    @ResponseBody
    default BaseDto update(@RequestBody DTO dto) {
        return BaseDto.BuildBy(updateFunction(dto).done());
    }

    IHolder.IExecuteOk updateFunction(DTO dto);
}
