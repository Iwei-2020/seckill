package com.smile.exception;

import com.smile.vo.RespBean;
import com.smile.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;


/**
 * TODO
 * 全局异常处理类
 * @author smilePlus
 * @version 1.0
 * @date 2021/3/7 8:59
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public RespBean exceptionHandler(Exception e) {
        log.error(e.getMessage());
        if (e instanceof GlobalException) {
            GlobalException gloEx = (GlobalException) e;
            return RespBean.error(gloEx.getRespBeanEnum());
        } else if (e instanceof BindException) {
            BindException binEx = (BindException) e;
            RespBean respBean = RespBean.error(RespBeanEnum.BIND_ERROR);
            respBean.setMessage(respBean.getMessage()
                    + binEx.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return respBean;
        } else if (e instanceof ConstraintViolationException) {
            return RespBean.error(RespBeanEnum.USER_UN_LOGIN);
        }
        return RespBean.error(RespBeanEnum.ERROR);
    }
}
