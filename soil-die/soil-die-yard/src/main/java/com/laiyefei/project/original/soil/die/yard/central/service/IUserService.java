package com.laiyefei.project.original.soil.die.yard.central.service;

import com.laiyefei.project.original.soil.dependencies.ssmp.central.service.IBaseService;
import com.laiyefei.project.original.soil.die.yard.foundation.pojo.dto.Ticket;
import com.laiyefei.project.original.soil.die.yard.foundation.pojo.dto.UserDto;
import com.laiyefei.project.original.soil.die.yard.foundation.pojo.po.User;
import org.springframework.util.Assert;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 用户信息服务
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IUserService extends IBaseService<UserDto, User> {

    boolean verify(final String account, final String password);

    User getUserBy(final String account);

    User getUserById(final String id);

    default User acceptDto(final Ticket ticket) {
        Assert.notNull(ticket, "error: sorry, the ticket is can not be null.");
        return this.acceptDto(Ticket.BuildBy(ticket));
    }
}
