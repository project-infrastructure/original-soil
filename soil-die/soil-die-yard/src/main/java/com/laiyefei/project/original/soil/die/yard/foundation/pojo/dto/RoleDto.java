package com.laiyefei.project.original.soil.die.yard.foundation.pojo.dto;

import com.laiyefei.project.original.soil.standard.spread.foundation.pojo.dto.AbsDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 基本角色信息传输对象
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@ApiModel("角色传输对象")
public class RoleDto extends AbsDto {
    @ApiModelProperty("id")
    protected String id;
    @ApiModelProperty("编码")
    protected String code;
    @ApiModelProperty("名称")
    protected String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode(){
        return this.code;
    }
    public String getName(){
        return this.name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
}
