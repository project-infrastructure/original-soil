package com.laiyefei.project.infrastructure.original.soil.die.yard.central.core;

import com.laiyefei.project.infrastructure.original.soil.die.yard.central.service.IUserService;
import com.laiyefei.project.infrastructure.original.soil.standard.central.core.ICore;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.central.core.AbsBaseCoreImpl;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dao.IUserDao;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dto.UserDto;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po.User;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.JudgeUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 用户信息核心实现
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Service
public class UserCoreImpl extends AbsBaseCoreImpl<UserDto, IUserDao, User> implements IUserService, ICore {

    @Resource
    private IUserDao userDao;

    @Override
    public User acceptDto(final UserDto dto) {
        final User user = new User();
        if (JudgeUtil.IsNotNULL(dto.getAccount())) {
            user.setAccount(dto.getAccount());
        }
        if (JudgeUtil.IsNotNULL(dto.getName())) {
            user.setName(dto.getName());
        }
        if (JudgeUtil.IsNotNULL(dto.getPlainText())) {
            user.setPlainText(dto.getPlainText());
        }
        if (JudgeUtil.IsNotNULL(dto.getSalt())) {
            user.setSalt(dto.getSalt());
        }
        //构建密码
        if (JudgeUtil.IsNotNULL(dto.getPlainText(), dto.getSalt())) {
            final String password = User.GeneratePassword(dto.getPlainText(), dto.getSalt());
            user.setPassword(password);
        }
        return user;
    }

    @Override
    public boolean verify(String account, String password) {
        if (JudgeUtil.IsNull(account)) {
            return false;
        }
        if (JudgeUtil.IsNull(password)) {
            return false;
        }
        final User user = this.getUserBy(account);
        if (JudgeUtil.IsNull(user)) {
            return false;
        }
        if (JudgeUtil.IsNull(user.getPassword())) {
            return false;
        }

        return user.getPassword().equals(User.GeneratePassword(password, user.getSalt()));
    }

    @Override
    public User getUserBy(String account) {
        return userDao.selectBy(account);
    }

    @Override
    public User getUserById(String id) {
        Assert.notNull(id, "error: sorry, the id is null.");
        return this.userDao.selectById(id);
    }

}
