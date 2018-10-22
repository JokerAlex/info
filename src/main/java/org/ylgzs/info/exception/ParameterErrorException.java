package org.ylgzs.info.exception;

/**
 * @ClassName ParameterErrorException
 * @Description ParameterError
 * @Author alex
 * @Date 2018/10/17
 **/
public class ParameterErrorException extends RuntimeException {
    public ParameterErrorException(String message) {
        super(message);
    }
}
