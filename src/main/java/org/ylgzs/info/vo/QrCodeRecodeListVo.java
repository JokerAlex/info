package org.ylgzs.info.vo;

import lombok.Data;

/**
 * @ClassName QrCodeRecodeListVo
 * @Description vo
 * @Author alex
 * @Date 2018/10/7
 **/
@Data
public class QrCodeRecodeListVo {

    private Integer qrcodeRecordId;

    private String qrcodeRecordCode;

    private String qrcodeRecordName;

    private Integer qrcodeRecordStatus;

    private String updateTime;

    public QrCodeRecodeListVo() {
    }

    public QrCodeRecodeListVo(Integer qrcodeRecordId, String qrcodeRecordCode, String qrcodeRecordName, Integer qrcodeRecordStatus, String updateTime) {
        this.qrcodeRecordId = qrcodeRecordId;
        this.qrcodeRecordCode = qrcodeRecordCode;
        this.qrcodeRecordName = qrcodeRecordName;
        this.qrcodeRecordStatus = qrcodeRecordStatus;
        this.updateTime = updateTime;
    }
}
