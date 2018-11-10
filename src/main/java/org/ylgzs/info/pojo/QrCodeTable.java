package org.ylgzs.info.pojo;

import java.util.Date;

public class QrCodeTable extends QrCodeTableKey {
    private Date createTime;

    private Date updateTime;

    public QrCodeTable(Integer qrcodeId, Integer tableId, Date createTime, Date updateTime) {
        super(qrcodeId, tableId);
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public QrCodeTable() {
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