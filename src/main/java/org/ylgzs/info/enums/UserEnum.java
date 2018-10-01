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
     * 个人信息更新结果
     */
    UPDATE_FAILED("个人信息更新失败"),

    /**
     * 密码检验
     */
    OLD_PASS_WRONG("原密码错误"),

    /**
     * 密码检验
     */
    NEW_PASS_SAME_AS_OLD("新密码与原密码相同"),

    /**
     * 密码更新结果
     */
    UPDATE_PASSWORD_FAILED("跟新密码失败"),

    /**
     * 登录名是否可用
     */
    INVALID_USER_LOGIN_NAME("登录名已存在"),

    /**
     * 邮箱是否可用
     */
    INVALID_EMAIL("邮箱已存在"),

    /**
     * 获取用户列表
     */
    LIST_USER_FAILED("获取用户列表失败"),

    ;

    private String message;

    UserEnum(String message) {
        this.message = message;
    }
}
