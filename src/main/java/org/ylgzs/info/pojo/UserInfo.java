package org.ylgzs.info.pojo;

import java.util.Date;

public class UserInfo {
    private Integer userId;

    private String userLoginName;

    private String userPassword;

    private String userName;

    private String userEmail;

    private Integer userEmailConfirm;

    private Integer userRole;

    private Date userCreateTime;

    private Date userUpdateTime;

    private String gradeGradeId;

    public UserInfo(Integer userId, String userLoginName, String userPassword, String userName, String userEmail, Integer userEmailConfirm, Integer userRole, Date userCreateTime, Date userUpdateTime, String gradeGradeId) {
        this.userId = userId;
        this.userLoginName = userLoginName;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userEmailConfirm = userEmailConfirm;
        this.userRole = userRole;
        this.userCreateTime = userCreateTime;
        this.userUpdateTime = userUpdateTime;
        this.gradeGradeId = gradeGradeId;
    }

    public UserInfo() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName == null ? null : userLoginName.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public Integer getUserEmailConfirm() {
        return userEmailConfirm;
    }

    public void setUserEmailConfirm(Integer userEmailConfirm) {
        this.userEmailConfirm = userEmailConfirm;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public Date getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(Date userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    public Date getUserUpdateTime() {
        return userUpdateTime;
    }

    public void setUserUpdateTime(Date userUpdateTime) {
        this.userUpdateTime = userUpdateTime;
    }

    public String getGradeGradeId() {
        return gradeGradeId;
    }

    public void setGradeGradeId(String gradeGradeId) {
        this.gradeGradeId = gradeGradeId == null ? null : gradeGradeId.trim();
    }
}