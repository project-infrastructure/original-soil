/*
 * Copyright (c) 2015-2020, www.dibo.ltd (service@dibo.ltd).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.laiyefei.project.original.soil.whole.kernel.aid.exception;

import com.laiyefei.project.original.soil.whole.kernel.pojo.dto.Status;
import com.laiyefei.project.original.soil.whole.kernel.tools.util.StringUtil;
import com.laiyefei.project.original.soil.whole.kernel.tools.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 全局异常统一处理的默认实现 （继承自该类并添加@ControllerAdvice注解即可自动支持兼容页面和JSON的异常处理）
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class DefaultExceptionHandler {
    private final static Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    /**
     * 统一处理校验错误 BindResult
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public Object validExceptionHandler(Exception ex) {
        Map<String, Object> map = new HashMap<>();
        BindingResult br = null;
        if (ex instanceof BindException) {
            br = ((BindException) ex).getBindingResult();
        } else if (ex instanceof MethodArgumentNotValidException) {
            br = ((MethodArgumentNotValidException) ex).getBindingResult();
        }
        if (br != null && br.hasErrors()) {
            map.put("code", Status.FAIL_VALIDATION.getIntCode());
            String validateErrorMsg = Validator.getBindingError(br);
            map.put("msg", validateErrorMsg);
            log.warn("数据校验失败, {}: {}", br.getObjectName(), validateErrorMsg);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * 统一异常处理类
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Object handleException(HttpServletRequest request, Exception e) {
        HttpStatus status = getStatus(request);
        Map<String, Object> map = null;
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            map = be.toMap();
        } else if (e.getCause() instanceof BusinessException) {
            BusinessException be = (BusinessException) e.getCause();
            map = be.toMap();
        } else {
            map = new HashMap<>();
            map.put("code", status.value());
            String msg = e.getMessage();
            //空指针异常
            if (msg == null) {
                msg = e.getClass().getSimpleName();
            }
            map.put("msg", msg);
        }
        log.warn("请求处理异常", e);
        if (isJsonRequest(request)) {
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            //获取错误页面
            String viewName = getViewName(request, e);
            map.put("exception", e);
            map.put("status", status.value());
            map.put("message", map.get("msg"));
            map.put("timestamp", new Date());
            map.remove("msg");
            return new ModelAndView(viewName, map);
        }
    }

    /**
     * 获取默认的错误页面
     *
     * @param request
     * @param ex
     * @return
     */
    protected String getViewName(HttpServletRequest request, Exception ex) {
        return "error";
    }

    /**
     * 是否为JSON数据请求
     *
     * @param request
     * @return
     */
    protected boolean isJsonRequest(HttpServletRequest request) {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return true;
        }
        return StringUtil.containsIgnoreCase(request.getHeader("Accept"), "json")
                || StringUtil.containsIgnoreCase(request.getHeader("content-type"), "json")
                || StringUtil.containsIgnoreCase(request.getContentType(), "json");
    }

    /**
     * 获取状态码
     *
     * @param request
     * @return
     */
    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
