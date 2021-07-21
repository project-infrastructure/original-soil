package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.aid;

import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.co.WhiteList;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.eo.PVException;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.eo.RestException;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;
import com.laiyefei.project.infrastructure.original.soil.common.pojo.dto.BaseDto;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.eo.AbsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 异常处理类
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class MyExceptionHandler {

    @RestControllerAdvice
    public static final class RestControllerHandler implements IAid {
        @ExceptionHandler(RuntimeException.class)
        public BaseDto handleException(RuntimeException exception) {
            return BaseDto.BuildFailedBy(exception.getClass().getName(), exception.getMessage());
        }

        @ExceptionHandler({
                RestException.VerifyFailed.class,
                RestException.UnLogin.class,
                RestException.NoUser.class,
                RestException.NoAccess.class,
                RestException.LoginTimeout.class
        })
        public BaseDto handleException(AbsException absException) throws IOException {
            return BaseDto.BuildFailedBy(absException.getCode(), absException.getDescription());
        }
    }

    @ControllerAdvice
    public static final class ControllerHandler implements IAid {

        @ExceptionHandler({
                PVException.VerifyFailed.class,
                PVException.UnLogin.class,
                PVException.NoUser.class,
                PVException.NoAccess.class,
                PVException.LoginTimeout.class
        })
        public ModelAndView handleException(HttpServletResponse response) throws IOException {
            response.sendRedirect(WhiteList.LOGIN_ROUTE);
            ModelAndView mv = new ModelAndView();
            mv.setViewName(WhiteList.LOGIN_ROUTE_PAGE_VIEW);
            return mv;
        }
    }

}
