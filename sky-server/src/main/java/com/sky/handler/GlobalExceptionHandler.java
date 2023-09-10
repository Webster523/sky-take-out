package com.sky.handler;

import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

import static com.sky.constant.MessageConstant.ALREADY_EXISTED;
import static com.sky.constant.MessageConstant.UNKNOWN_ERROR;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result exceptionHandler(SQLIntegrityConstraintViolationException exception) {
        // error message template: Duplicate entry 'zhangsan' for key 'employee.idx_username'
        String message = UNKNOWN_ERROR;
        if (exception.getMessage().contains("Duplicate entry")) {
            String[] tokens = exception.getMessage().split(" ");
            message = tokens[2] + ALREADY_EXISTED;
        }
        return Result.error(message);
    }

    /**
     * 捕获业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

}
