package org.ylgzs.info.enums;

import lombok.Getter;

/**
 * @ClassName GradeEnum
 * @Description 年级
 * @Author alex
 * @Date 2018/10/1
 **/
@Getter
public enum  GradeEnum {

    /**
     * 获取年级信息
     */
    LIST_GRADE_FAILED("获取年级信息失败"),

    /**
     * 添加年级信息
     */
    ADD_GRADE_FAILED("添加年级信息失败"),

    /**
     * 年级信息
     */
    TOO_BIG("年级信息错误"),

    ;

    private String message;

    GradeEnum(String message) {
        this.message = message;
    }
}
