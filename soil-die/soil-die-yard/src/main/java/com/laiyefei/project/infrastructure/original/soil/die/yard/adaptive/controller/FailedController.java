package com.laiyefei.project.infrastructure.original.soil.die.yard.adaptive.controller;

import com.laiyefei.project.infrastructure.original.soil.standard.adaptive.controller.IController;
import com.laiyefei.project.infrastructure.original.soil.common.pojo.dto.BaseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 失败信息控制层
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Api(tags = "失败信息控制")
@RequestMapping(value = "/failed", name = "失败信息控制")
@RestController
public class FailedController implements IController {

    @ApiOperation("ajax失败信息")
    @RequestMapping(value = "/ajax", name = "ajax失败信息")
    public BaseDto ajax() {
        return BaseDto.BuildFailed();
    }
}
