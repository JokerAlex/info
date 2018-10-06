package org.ylgzs.info.vo;

import lombok.Data;

/**
 * @ClassName TableInfoDetailVo
 * @Description detail vo
 * @Author alex
 * @Date 2018/10/6
 **/
@Data
public class TableInfoDetailVo {

    private String tableInfoName;

    private String userName;

    private String tableInfoDescription;

    private String tableInfoQueryCol;

    private Integer tableInfoPv;

    private String tableInfoCreateTime;

    private String tableInfoUpdateTime;

    public TableInfoDetailVo() {
    }

    public TableInfoDetailVo(String tableInfoName, String userName, String tableInfoDescription, String tableInfoQueryCol, Integer tableInfoPv, String tableInfoCreateTime, String tableInfoUpdateTime) {
        this.tableInfoName = tableInfoName;
        this.userName = userName;
        this.tableInfoDescription = tableInfoDescription;
        this.tableInfoQueryCol = tableInfoQueryCol;
        this.tableInfoPv = tableInfoPv;
        this.tableInfoCreateTime = tableInfoCreateTime;
        this.tableInfoUpdateTime = tableInfoUpdateTime;
    }
}
