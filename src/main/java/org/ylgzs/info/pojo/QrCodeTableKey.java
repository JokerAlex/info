package org.ylgzs.info.pojo;

import lombok.ToString;

@ToString
public class QrCodeTableKey {
    private Integer qrcodeId;

    private Integer tableId;

    public QrCodeTableKey(Integer qrcodeId, Integer tableId) {
        this.qrcodeId = qrcodeId;
        this.tableId = tableId;
    }

    public QrCodeTableKey() {
        super();
    }

    public Integer getQrcodeId() {
        return qrcodeId;
    }

    public void setQrcodeId(Integer qrcodeId) {
        this.qrcodeId = qrcodeId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }
}