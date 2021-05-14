package com.laiyefei.project.original.soil.die.yard.foundation.performer;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.laiyefei.project.original.soil.die.yard.central.service.IUserService;
import com.laiyefei.project.original.soil.die.yard.foundation.pojo.dto.Ticket;
import com.laiyefei.project.original.soil.die.yard.foundation.pojo.eo.RestException;
import com.laiyefei.project.original.soil.die.yard.foundation.pojo.po.User;
import com.laiyefei.project.original.soil.standard.spread.foundation.performer.TokenConfiger;
import com.laiyefei.project.original.soil.standard.spread.foundation.tools.util.JudgeUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : token 数据处理组件
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Component
public final class TokenDataHolder {

    private final TokenConfiger tokenConfiger;
    private final IUserService userService;
    //一天
    private final long expireTimeDelta = 24 * 60 * 60 * 1000;

    public TokenDataHolder(TokenConfiger tokenConfiger,
                           IUserService userService) {
        this.tokenConfiger = tokenConfiger;
        this.userService = userService;
    }

    public String gainToken(String userId) {
        Assert.notNull(userId, "error: sorry, userId is null.");
        return JWT.create().withExpiresAt(new Date(System.currentTimeMillis() + expireTimeDelta))
                .withAudience(userId)
                .sign(Algorithm.HMAC256(TokenConfiger.TOKEN_HEADER_CONFIG_KEY));
    }

    public String gainToken(Ticket ticket) {
        Assert.notNull(ticket, "error: ticket, userId is null.");
        Assert.notNull(ticket.getAcc(), "error: sorry, the account is null.");
        final User user = this.userService.getUserBy(ticket.getAcc());
        if(JudgeUtil.IsNull(user)){
            throw new RestException.NoUser();
        }
        return this.gainToken(user.getId());
    }

    public String unLashToken(String token) {
        Assert.notNull(token, "error: sorry, the token is null.");
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TokenConfiger.TOKEN_HEADER_CONFIG_KEY)).build();
        List<String> audiences = jwtVerifier.verify(token).getAudience();
        Assert.isTrue(1 == audiences.size(), "error: sorry, the audiences size is not equal 1, so else error, can not through.");
        return audiences.get(0);
    }

}
