package org.ylgzs.info.pojo;

import lombok.ToString;

@ToString
public class QrCodeRecordKey {
    private Integer qrcodeRecordId;

    private Integer userUserId;

    public QrCodeRecordKey(Integer qrcodeRecordId, Integer userUserId) {
        this.qrcodeRecordId = qrcodeRecordId;
        this.userUserId = userUserId;
    }

    public QrCodeRecordKey() {
        super();
    }

    public Integer getQrcodeRecordId() {
        return qrcodeRecordId;
    }

    public void setQrcodeRecordId(Integer qrcodeRecordId) {
        this.qrcodeRecordId = qrcodeRecordId;
    }

    public Integer getUserUserId() {
        return userUserId;
    }

    public void setUserUserId(Integer userUserId) {
        this.userUserId = userUserId;
    }
}