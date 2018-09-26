package org.ylgzs.info.pojo;

import java.util.Date;

public class UserDepartment extends UserDepartmentKey {
    private Date createTime;

    private Date updateTime;

    public UserDepartment(Integer iddepartmentId, String userId, Date createTime, Date updateTime) {
        super(iddepartmentId, userId);
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public UserDepartment() {
        super();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}