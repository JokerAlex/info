package org.ylgzs.info.vo;

import lombok.Data;

/**
 * @ClassName RecordTableVo
 * @Description vo
 * @Author alex
 * @Date 2018/10/7
 **/
@Data
public class RecordTableVo {
    private Integer tableId;

    private String tableName;

    private String tableStatus;

    private String updateTime;

    public RecordTableVo() {
    }

    public RecordTableVo(Integer tableId, String tableName, String tableStatus, String updateTime) {
        this.tableId = tableId;
        this.tableName = tableName;
        this.tableStatus = tableStatus;
        this.updateTime = updateTime;
    }
}
