package org.ylgzs.info.enums;

import lombok.Getter;

/**
 * @ClassName DepartmentEnum
 * @Description 部门
 * @Author alex
 * @Date 2018/10/1
 **/
@Getter
public enum DepartmentEnum {

    /**
     * 获取部门信息
     */
    LIST_DEPARTMENT_FAILED("获取部门信息失败"),

    /**
     * 添加部门信息
     */
    ADD_DEPARTMENT_FAILED("添加部门信息失败"),

    /**
     * 更新部门信息
     */
    UPDATE_DEPARTMENT_FAILED("更新部门信息失败"),
    ;

    private String message;

    DepartmentEnum(String message) {
        this.message = message;
    }
}
