package org.ylgzs.info.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @ClassName QrCodeRecordDetailVo
 * @Description vo
 * @Author alex
 * @Date 2018/10/7
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QrCodeRecordDetailVo {

    private Integer qrcodeRecordId;

    private Integer userId;

    private String qrcodeRecordCode;

    private String qrcodeRecordName;

    private String description;

    private Integer qrcodeRecordStatus;

    private String updateTime;

    private List<RecordTableVo> tableVos;

    public QrCodeRecordDetailVo() {
    }

    public QrCodeRecordDetailVo(Integer qrcodeRecordId, Integer userId, String qrcodeRecordCode, String qrcodeRecordName, String description, Integer qrcodeRecordStatus, String updateTime, List<RecordTableVo> tableVos) {
        this.qrcodeRecordId = qrcodeRecordId;
        this.userId = userId;
        this.qrcodeRecordCode = qrcodeRecordCode;
        this.qrcodeRecordName = qrcodeRecordName;
        this.description = description;
        this.qrcodeRecordStatus = qrcodeRecordStatus;
        this.updateTime = updateTime;
        this.tableVos = tableVos;
    }
}
