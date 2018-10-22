package org.ylgzs.info.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @ClassName UserInfoVo
 * @Description 展示
 * @Author alex
 * @Date 2018/10/1
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoVo {
    private Integer userId;

    private String userLoginName;

    private String userName;

    private String userEmail;

    private Integer userEmailConfirm;

    private Integer departmentDepartmentId;

    private String gradeGradeId;

    private String userRole;

    private String userInfoOpenId;

    private String userCreateTime;

    private String userUpdateTime;

    public UserInfoVo() {
    }

    public UserInfoVo(Integer userId, String userLoginName, String userName, String userEmail, Integer userEmailConfirm, Integer departmentDepartmentId, String gradeGradeId, String userRole) {
        this.userId = userId;
        this.userLoginName = userLoginName;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userEmailConfirm = userEmailConfirm;
        this.departmentDepartmentId = departmentDepartmentId;
        this.gradeGradeId = gradeGradeId;
        this.userRole = userRole;
    }

    public UserInfoVo(Integer userId, String userLoginName, String userName, String userEmail, Integer userEmailConfirm, Integer departmentDepartmentId, String gradeGradeId, String userRole, String userInfoOpenId, String userCreateTime, String userUpdateTime) {
        this.userId = userId;
        this.userLoginName = userLoginName;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userEmailConfirm = userEmailConfirm;
        this.departmentDepartmentId = departmentDepartmentId;
        this.gradeGradeId = gradeGradeId;
        this.userRole = userRole;
        this.userInfoOpenId = userInfoOpenId;
        this.userCreateTime = userCreateTime;
        this.userUpdateTime = userUpdateTime;
    }
}
