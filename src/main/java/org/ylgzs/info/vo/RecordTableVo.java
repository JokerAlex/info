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

    private String collectionName;

    private Integer tableStatus;

    private String updateTime;

    public RecordTableVo() {
    }

    public RecordTableVo(Integer tableId, String tableName, Integer tableStatus, String updateTime) {
        this.tableId = tableId;
        this.tableName = tableName;
        this.tableStatus = tableStatus;
        this.updateTime = updateTime;
    }

    public RecordTableVo(Integer tableId, String tableName, String collectionName, Integer tableStatus, String updateTime) {
        this.tableId = tableId;
        this.tableName = tableName;
        this.collectionName = collectionName;
        this.tableStatus = tableStatus;
        this.updateTime = updateTime;
    }
}
