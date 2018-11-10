package org.ylgzs.info.pojo;

import java.util.Date;

public class TableInfo extends TableInfoKey {
    private String tableInfoName;

    private String collectionName;

    private String tableInfoDescription;

    private String tableInfoQueryCol;

    private Integer tableInfoPv;

    private Integer tableInfoStatus;

    private Date tableInfoCreateTime;

    private Date tableInfoUpdateTime;

    public TableInfo(Integer tableInfoId, Integer userUserId, String tableInfoName, String collectionName, String tableInfoDescription, String tableInfoQueryCol, Integer tableInfoPv, Integer tableInfoStatus, Date tableInfoCreateTime, Date tableInfoUpdateTime) {
        super(tableInfoId, userUserId);
        this.tableInfoName = tableInfoName;
        this.collectionName = collectionName;
        this.tableInfoDescription = tableInfoDescription;
        this.tableInfoQueryCol = tableInfoQueryCol;
        this.tableInfoPv = tableInfoPv;
        this.tableInfoStatus = tableInfoStatus;
        this.tableInfoCreateTime = tableInfoCreateTime;
        this.tableInfoUpdateTime = tableInfoUpdateTime;
    }

    public TableInfo() {
        super();
    }

    public String getTableInfoName() {
        return tableInfoName;
    }

    public void setTableInfoName(String tableInfoName) {
        this.tableInfoName = tableInfoName == null ? null : tableInfoName.trim();
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName == null ? null : collectionName.trim();
    }

    public String getTableInfoDescription() {
        return tableInfoDescription;
    }

    public void setTableInfoDescription(String tableInfoDescription) {
        this.tableInfoDescription = tableInfoDescription == null ? null : tableInfoDescription.trim();
    }

    public String getTableInfoQueryCol() {
        return tableInfoQueryCol;
    }

    public void setTableInfoQueryCol(String tableInfoQueryCol) {
        this.tableInfoQueryCol = tableInfoQueryCol == null ? null : tableInfoQueryCol.trim();
    }

    public Integer getTableInfoPv() {
        return tableInfoPv;
    }

    public void setTableInfoPv(Integer tableInfoPv) {
        this.tableInfoPv = tableInfoPv;
    }

    public Integer getTableInfoStatus() {
        return tableInfoStatus;
    }

    public void setTableInfoStatus(Integer tableInfoStatus) {
        this.tableInfoStatus = tableInfoStatus;
    }

    public Date getTableInfoCreateTime() {
        return tableInfoCreateTime;
    }

    public void setTableInfoCreateTime(Date tableInfoCreateTime) {
        this.tableInfoCreateTime = tableInfoCreateTime;
    }

    public Date getTableInfoUpdateTime() {
        return tableInfoUpdateTime;
    }

    public void setTableInfoUpdateTime(Date tableInfoUpdateTime) {
        this.tableInfoUpdateTime = tableInfoUpdateTime;
    }
}