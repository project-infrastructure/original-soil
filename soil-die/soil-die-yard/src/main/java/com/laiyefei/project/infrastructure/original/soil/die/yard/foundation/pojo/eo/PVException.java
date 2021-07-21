package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.eo;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.co.ICo;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.zoo.eo.IEo;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.ExceptionEnum;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.eo.AbsException;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : page view exception
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class PVException implements IEo {
    public static class LoginTimeout extends AbsException {
        public LoginTimeout(String code, String description) {
            super(code, description);
        }

        public LoginTimeout(ExceptionEnum exceptionEnum) {
            super(exceptionEnum);
        }
    }

    public static class NoAccess extends AbsException {
        public NoAccess(String code, String description) {
            super(code, description);
        }

        public NoAccess(ExceptionEnum exceptionEnum) {
            super(exceptionEnum);
        }
    }

    public static class NoUser extends AbsException {
        public NoUser(String code, String description) {
            super(code, description);
        }

        public NoUser(ExceptionEnum exceptionEnum) {
            super(exceptionEnum);
        }
    }

    public static class UnLogin extends AbsException {
        public UnLogin(String code, String description) {
            super(code, description);
        }

        public UnLogin() {
            super(ExceptionEnum.UnLogin);
        }
    }

    public static class VerifyFailed extends AbsException {
        public VerifyFailed(String code, String description) {
            super(code, description);
        }

        public VerifyFailed(ExceptionEnum exceptionEnum) {
            super(exceptionEnum);
        }

        public VerifyFailed(Exception exception) {
            super(ICo.BuildBy(ExceptionEnum.VerifyFailed.getCode(), exception.getMessage()));
        }
    }
}
