package org.ylgzs.info.pojo;

import java.util.Date;

public class QrCodeTable extends QrCodeTableKey {
    private String tableStatus;

    private Date createTime;

    private Date updateTime;

    public QrCodeTable(Integer qrcodeId, Integer tableId, String tableStatus, Date createTime, Date updateTime) {
        super(qrcodeId, tableId);
        this.tableStatus = tableStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public QrCodeTable() {
        super();
    }

    public String getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(String tableStatus) {
        this.tableStatus = tableStatus == null ? null : tableStatus.trim();
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