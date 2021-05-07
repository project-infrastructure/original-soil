package com.laiyefei.project.original.soil.die.yard.foundation.pojo.dto;

import com.laiyefei.project.original.soil.standard.spread.foundation.pojo.dto.AbsDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.Assert;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 票据
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Data
@ApiModel("登录票据")
@EqualsAndHashCode(callSuper = false)
public final class Ticket extends AbsDto {

    @ApiModelProperty("账号")
    private String acc;
    @ApiModelProperty("密码")
    private String pwd;
    @ApiModelProperty(value = "盐", hidden = true)
    private String salt;

    public static final UserDto BuildBy(final Ticket ticket) {
        Assert.notNull(ticket, "error: sorry, the ticket is null.");
        final UserDto userDto = new UserDto();
        userDto.setAccount(ticket.getAcc());
        userDto.setPlainText(ticket.getPwd());
        userDto.setSalt(ticket.getSalt());
        return userDto;
    }

}
