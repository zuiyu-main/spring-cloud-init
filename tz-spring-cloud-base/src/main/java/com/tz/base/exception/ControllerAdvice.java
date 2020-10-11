package com.tz.base.exception;

import com.tz.base.model.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author https://github.com/TianPuJun @醉鱼
 * @ClassName ControllerAdvice
 * @Description 统一异常处理
 * @Date 11:23 2020/10/11
 **/
@RestControllerAdvice
@Component
public class ControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(value = InvalidTokenException.class)
    public ResultBean<Exception> invalidTokenExceptionHandler(Exception e){
        LOGGER.error(e.getMessage());
        return  ResultBean.<Exception>builder()
                .msg("token:"+HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .code(HttpStatus.UNAUTHORIZED.value())
                .data(e).build();
    }
    @ExceptionHandler(value = Exception.class)
    public ResultBean<Exception> errorHandler(Exception e){
        return  ResultBean.<Exception>builder()
                .msg("test:"+HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .data(e).build();
    }


}
