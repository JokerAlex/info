package org.ylgzs.info.constant;

/**
 * @ClassName Constants
 * @Description 常量
 * @Author alex
 * @Date 2018/10/7
 **/
public final class Constants {

    private Constants() {}

    /**
     * 表格状态，发布记录状态
     */
    public static final Integer STATUS_ON = 1;

    public static final Integer STATUS_OFF = 0;

    public static final String USER_LOGIN_NAME = "login_name";

    public static final String EMAIL = "email";

    /**
     * 用户角色0-管理员，1-部门管理员，2-辅导员，3-普通用户
     */
    public static final Integer ADMIN = 0;

    public static final Integer DEPARTMENT_ADMIN = 1;

    public static final Integer COUNSELOR = 2;

    public static final Integer TEACHER = 3;
}
