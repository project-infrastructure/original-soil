package com.laiyefei.project.infrastructure.original.soil.common.aid;

import com.laiyefei.project.infrastructure.original.soil.common.pojo.dto.BaseDto;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.eo.ControllerException;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.eo.DaoException;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.eo.OtherException;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.eo.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 默认异常处理
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class DefaultExceptionHandler {

    @ControllerAdvice
    public static class ControllerHandler {
        @ExceptionHandler(OtherException.class)
        public BaseDto handleException(OtherException exception) {
            return BaseDto.BuildFailedBy(exception.getCode(), exception.getDescription());
        }

        @ExceptionHandler(ControllerException.class)
        public BaseDto handleException(ControllerException exception) {
            return BaseDto.BuildFailedBy(exception.getCode(), exception.getDescription());
        }

        @ExceptionHandler(ServiceException.class)
        public BaseDto handleException(ServiceException exception) {
            return BaseDto.BuildFailedBy(exception.getCode(), exception.getDescription());
        }

        @ExceptionHandler(DaoException.class)
        public BaseDto handleException(DaoException exception) {
            return BaseDto.BuildFailedBy(exception.getCode(), exception.getDescription());
        }
    }

    @RestControllerAdvice
    public static class RestControllerHandler {
        @ExceptionHandler(OtherException.class)
        public BaseDto handleException(OtherException exception) {
            return BaseDto.BuildFailedBy(exception.getCode(), exception.getDescription());
        }

        @ExceptionHandler(ControllerException.class)
        public BaseDto handleException(ControllerException exception) {
            return BaseDto.BuildFailedBy(exception.getCode(), exception.getDescription());
        }

        @ExceptionHandler(ServiceException.class)
        public BaseDto handleException(ServiceException exception) {
            return BaseDto.BuildFailedBy(exception.getCode(), exception.getDescription());
        }

        @ExceptionHandler(DaoException.class)
        public BaseDto handleException(DaoException exception) {
            return BaseDto.BuildFailedBy(exception.getCode(), exception.getDescription());
        }
    }

}
