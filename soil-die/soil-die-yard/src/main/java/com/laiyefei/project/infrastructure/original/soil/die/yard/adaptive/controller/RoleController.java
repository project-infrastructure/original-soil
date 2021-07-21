package com.laiyefei.project.infrastructure.original.soil.die.yard.adaptive.controller;

import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dto.RoleDto;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po.Role;
import com.laiyefei.project.infrastructure.original.soil.standard.adaptive.controller.IController;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.controller.AbsBaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 角色信息控制层
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Api(tags = "角色信息")
@RequestMapping(value = "/role", name = "角色信息控制层")
@RestController
public class RoleController extends AbsBaseController<RoleDto, Role> implements IController {

}
