package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po;


import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dao.IUserDao;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dto.UserDto;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.aid.ContextHolder;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.pojo.po.AbsBasePo;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.JudgeUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.Md5Util;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 基本用户信息持久化对象
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class User extends AbsBasePo<UserDto> {

    protected String account;
    protected String name;
    protected String password;
    protected String plainText;
    protected String salt;

    @Override
    public UserDto buildDto() {
        return new UserDto() {

            @Override
            public String getId() {
                return User.this.id;
            }

            @Override
            public String getAccount() {
                return User.this.account;
            }

            @Override
            public String getName() {
                return User.this.name;
            }

            @Override
            public String getPassword() {
                return User.this.password;
            }

            @Override
            public String getPlainText() {
                return User.this.plainText;
            }

            @Override
            public String getSalt() {
                return User.this.salt;
            }
        };
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

    public static final String GeneratePassword(final String plainText, String salt) {
        salt = JudgeUtil.NVL(salt, GenerateSalt());
        return Md5Util.Hash(plainText, salt);
    }

    public static final String GenerateSalt() {
        return String.format("%06d", RandomUtils.nextInt(0, 1000000));
    }


    public static final User BuildBy(String account, String name, String plainText) {
        final User user = new User();
        user.setAccount(account);
        user.setName(name);
        user.setPlainText(plainText);
        return user;
    }

    public static final User AdornWholeBy(User user) {
        Assert.notNull(user, "error: sorry, the user is null.");
        final User newUser = new User();
        newUser.setAccount(user.getAccount());
        newUser.setName(user.getName());
        newUser.setPlainText(user.getPlainText());
        newUser.setSalt(JudgeUtil.NVL(user.getSalt(), GenerateSalt()));
        newUser.setPassword(User.GeneratePassword(user.getPlainText(), newUser.getSalt()));
        return newUser;
    }

    public static final User UnpackConditionBy(User user) {
        Assert.notNull(user, "error: sorry, the user is null.");
        final User newUser = new User();
        newUser.setAccount(user.getAccount());
        newUser.setName(user.getName());
        newUser.setPlainText(user.getPlainText());
        return newUser;
    }

    public static final User Just(String account) {
        return ContextHolder.DoDaoAction(IUserDao.class, dao -> {
            final User user = dao.selectBy(account);
            Assert.notNull(user, "error: sorry, the user account is ".concat(account).concat(" not exist."));
            return user;
        });
    }

    public static final User Find(String account) {
        return ContextHolder.DoDaoAction(IUserDao.class, dao -> {
            return dao.selectBy(account);
        });
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(this.id, user.id) ||
                Objects.equals(this.account, user.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account);
    }
}
