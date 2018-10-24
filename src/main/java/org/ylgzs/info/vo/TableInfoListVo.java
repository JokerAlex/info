package org.ylgzs.info.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @ClassName TableInfoListVo
 * @Description list vo
 * @Author alex
 * @Date 2018/10/6
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableInfoListVo {

    private Integer tableInfoId;

    private String tableInfoName;

    private Integer tableInfoPv;

    public TableInfoListVo() {
    }

    public TableInfoListVo(Integer tableInfoId, String tableInfoName, Integer tableInfoPv) {
        this.tableInfoId = tableInfoId;
        this.tableInfoName = tableInfoName;
        this.tableInfoPv = tableInfoPv;
    }
}
