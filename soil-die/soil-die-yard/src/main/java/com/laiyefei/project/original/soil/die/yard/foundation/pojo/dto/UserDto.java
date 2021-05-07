package com.laiyefei.project.original.soil.die.yard.foundation.pojo.dto;

import com.laiyefei.project.original.soil.standard.spread.foundation.pojo.dto.AbsDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 基本用户信息传输对象
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@ApiModel("用户传输对象")
public class UserDto extends AbsDto {
    @ApiModelProperty("id")
    protected String id;
    @ApiModelProperty("账号")
    protected String account;
    @ApiModelProperty("姓名")
    protected String name;
    @ApiModelProperty("密码")
    protected String password;
    @ApiModelProperty("明文")
    protected String plainText;
    @ApiModelProperty("盐")
    protected String salt;

    //java bean here
    //====================================================================
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
