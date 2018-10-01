package org.ylgzs.info.pojo;

import lombok.ToString;

import java.util.Date;

@ToString
public class Grade {
    private String gradeId;

    private Date gradeCreateTime;

    private Date gradeUpdateTime;

    public Grade(String gradeId, Date gradeCreateTime, Date gradeUpdateTime) {
        this.gradeId = gradeId;
        this.gradeCreateTime = gradeCreateTime;
        this.gradeUpdateTime = gradeUpdateTime;
    }

    public Grade() {
        super();
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId == null ? null : gradeId.trim();
    }

    public Date getGradeCreateTime() {
        return gradeCreateTime;
    }

    public void setGradeCreateTime(Date gradeCreateTime) {
        this.gradeCreateTime = gradeCreateTime;
    }

    public Date getGradeUpdateTime() {
        return gradeUpdateTime;
    }

    public void setGradeUpdateTime(Date gradeUpdateTime) {
        this.gradeUpdateTime = gradeUpdateTime;
    }
}