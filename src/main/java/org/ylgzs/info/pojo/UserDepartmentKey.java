package org.ylgzs.info.pojo;

public class UserDepartmentKey {
    private Integer iddepartmentId;

    private String userId;

    public UserDepartmentKey(Integer iddepartmentId, String userId) {
        this.iddepartmentId = iddepartmentId;
        this.userId = userId;
    }

    public UserDepartmentKey() {
        super();
    }

    public Integer getIddepartmentId() {
        return iddepartmentId;
    }

    public void setIddepartmentId(Integer iddepartmentId) {
        this.iddepartmentId = iddepartmentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}