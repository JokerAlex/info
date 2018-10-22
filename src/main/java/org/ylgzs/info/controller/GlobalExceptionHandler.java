package org.ylgzs.info.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ylgzs.info.exception.NotFoundException;
import org.ylgzs.info.exception.ParameterErrorException;
import org.ylgzs.info.vo.ServerResponse;

/**
 * @ClassName ExceptionHandler
 * @Description exception
 * @Author alex
 * @Date 2018/10/17
 **/

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ServerResponse handlerException(Exception e) {
        log.error(e.getMessage());
        return ServerResponse.isError(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ServerResponse handlerNotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        return ServerResponse.isError(e.getMessage());
    }

    @ExceptionHandler(ParameterErrorException.class)
    @ResponseBody
    public ServerResponse handlerParameterErrorException(ParameterErrorException e) {
        log.error(e.getMessage());
        return ServerResponse.isError(e.getMessage());
    }
}
