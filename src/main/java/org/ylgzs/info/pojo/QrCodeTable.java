package org.ylgzs.info.pojo;

import lombok.ToString;

import java.util.Date;

@ToString
public class QrCodeTable extends QrCodeTableKey {
    private Integer tableStatus;

    private Date createTime;

    private Date updateTime;

    public QrCodeTable(Integer qrcodeId, Integer tableId, Integer tableStatus, Date createTime, Date updateTime) {
        super(qrcodeId, tableId);
        this.tableStatus = tableStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public QrCodeTable() {
        super();
    }

    public Integer getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(Integer tableStatus) {
        this.tableStatus = tableStatus;
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