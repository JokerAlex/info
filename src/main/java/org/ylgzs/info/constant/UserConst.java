package org.ylgzs.info.constant;


/**
 * @ClassName UserConst
 * @Description 常量
 * @Author alex
 * @Date 2018/10/1
 **/
public interface UserConst {

    String USER_LOGIN_NAME = "login_name";

    String EMAIL = "email";

    /**
     * 用户角色0-管理员，1-部门管理员，2-辅导员，3-普通用户
     */
    Integer ADMIN = 0;

    Integer DEPARTMENT_ADMIN = 1;

    Integer COUNSELOR = 2;

    Integer TEACHER = 3;
}
