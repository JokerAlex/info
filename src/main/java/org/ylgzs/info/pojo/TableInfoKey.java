package org.ylgzs.info.pojo;

import lombok.ToString;

@ToString
public class TableInfoKey {
    private Integer tableInfoId;

    private Integer userUserId;

    public TableInfoKey(Integer tableInfoId, Integer userUserId) {
        this.tableInfoId = tableInfoId;
        this.userUserId = userUserId;
    }

    public TableInfoKey() {
        super();
    }

    public Integer getTableInfoId() {
        return tableInfoId;
    }

    public void setTableInfoId(Integer tableInfoId) {
        this.tableInfoId = tableInfoId;
    }

    public Integer getUserUserId() {
        return userUserId;
    }

    public void setUserUserId(Integer userUserId) {
        this.userUserId = userUserId;
    }
}