package org.ylgzs.info.enums;

import lombok.Getter;

/**
 * @ClassName UserEnum
 * @Description TODO
 * @Author alex
 * @Date 2018/10/1
 **/
@Getter
public enum UserEnum {

    /**
     * 注册结果
     */
    REGISTRATION_FAILED("注册失败"),

    /**
     * 登录名是否可用
     */
    INVALID_USER_LOGIN_NAME("登录名已存在"),

    /**
     * 邮箱是否可用
     */
    INVALID_EMAIL("邮箱已存在"),

    ;

    private String message;

    UserEnum(String message) {
        this.message = message;
    }
}
