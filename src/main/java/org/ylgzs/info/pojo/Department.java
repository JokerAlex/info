package org.ylgzs.info.pojo;

import lombok.ToString;

import java.util.Date;

@ToString
public class Department {
    private Integer departmentId;

    private String departmentName;

    private Date departmentCreateTime;

    private Date departmentUpdateTime;

    public Department(Integer departmentId, String departmentName, Date departmentCreateTime, Date departmentUpdateTime) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentCreateTime = departmentCreateTime;
        this.departmentUpdateTime = departmentUpdateTime;
    }

    public Department() {
        super();
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public Date getDepartmentCreateTime() {
        return departmentCreateTime;
    }

    public void setDepartmentCreateTime(Date departmentCreateTime) {
        this.departmentCreateTime = departmentCreateTime;
    }

    public Date getDepartmentUpdateTime() {
        return departmentUpdateTime;
    }

    public void setDepartmentUpdateTime(Date departmentUpdateTime) {
        this.departmentUpdateTime = departmentUpdateTime;
    }
}