package org.ylgzs.info.enums;

import lombok.Getter;

/**
 * @ClassName ResultEnum
 * @Description 请求返回码枚举
 * @Author alex
 * @Date 2018/10/1
 **/
@Getter
public enum ResultEnum {

    /**
     * 统一回复，请求成功
     */
    SUCCESS(0, "请求成功"),

    /**
     * 统一回复，请求失败
     */
    ERROR(1, "请求失败"),

    /**
     * 统一回复，非法参数
     */
    ILLEGAL_PARAMETER(2, "参数错误"),

    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
