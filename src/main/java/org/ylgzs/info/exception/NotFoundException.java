package org.ylgzs.info.exception;

/**
 * @ClassName NotFoundException
 * @Description not found
 * @Author alex
 * @Date 2018/10/17
 **/
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
