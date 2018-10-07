package org.ylgzs.info.dto;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName RecordTableDetailDTO
 * @Description dto
 * @Author alex
 * @Date 2018/10/7
 **/
@Data
public class RecordTableDetailDTO {
    private Integer tableId;

    private String tableName;

    private Integer tableStatus;

    private Date updateTime;

    public RecordTableDetailDTO() {
    }

    public RecordTableDetailDTO(Integer tableId, String tableName, Integer tableStatus, Date updateTime) {
        this.tableId = tableId;
        this.tableName = tableName;
        this.tableStatus = tableStatus;
        this.updateTime = updateTime;
    }
}
