package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : token传输实体
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Data
@Builder
@ApiModel("登录票据")
public class TokenDto {
    private String key;
    private String token;
    private String redirect;
}
