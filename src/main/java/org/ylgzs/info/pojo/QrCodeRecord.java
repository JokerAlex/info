package org.ylgzs.info.pojo;

import java.util.Date;

public class QrCodeRecord extends QrCodeRecordKey {
    private String qrcodeRecordCode;

    private Integer qrcodeRecordStatus;

    private Date qrcodeRecordCreateTime;

    private Date qrcodeRecordUpdateTime;

    public QrCodeRecord(Integer qrcodeRecordId, Integer userUserId, String qrcodeRecordCode, Integer qrcodeRecordStatus, Date qrcodeRecordCreateTime, Date qrcodeRecordUpdateTime) {
        super(qrcodeRecordId, userUserId);
        this.qrcodeRecordCode = qrcodeRecordCode;
        this.qrcodeRecordStatus = qrcodeRecordStatus;
        this.qrcodeRecordCreateTime = qrcodeRecordCreateTime;
        this.qrcodeRecordUpdateTime = qrcodeRecordUpdateTime;
    }

    public QrCodeRecord() {
        super();
    }

    public String getQrcodeRecordCode() {
        return qrcodeRecordCode;
    }

    public void setQrcodeRecordCode(String qrcodeRecordCode) {
        this.qrcodeRecordCode = qrcodeRecordCode == null ? null : qrcodeRecordCode.trim();
    }

    public Integer getQrcodeRecordStatus() {
        return qrcodeRecordStatus;
    }

    public void setQrcodeRecordStatus(Integer qrcodeRecordStatus) {
        this.qrcodeRecordStatus = qrcodeRecordStatus;
    }

    public Date getQrcodeRecordCreateTime() {
        return qrcodeRecordCreateTime;
    }

    public void setQrcodeRecordCreateTime(Date qrcodeRecordCreateTime) {
        this.qrcodeRecordCreateTime = qrcodeRecordCreateTime;
    }

    public Date getQrcodeRecordUpdateTime() {
        return qrcodeRecordUpdateTime;
    }

    public void setQrcodeRecordUpdateTime(Date qrcodeRecordUpdateTime) {
        this.qrcodeRecordUpdateTime = qrcodeRecordUpdateTime;
    }
}