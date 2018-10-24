package org.ylgzs.info.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @ClassName DepartmentVo
 * @Description vo
 * @Author alex
 * @Date 2018/10/7
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentVo {
    private Integer departmentId;

    private String departmentName;

    private String departmentCreateTime;

    private String departmentUpdateTime;

    public DepartmentVo() {
    }

    public DepartmentVo(Integer departmentId, String departmentName, String departmentCreateTime, String departmentUpdateTime) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentCreateTime = departmentCreateTime;
        this.departmentUpdateTime = departmentUpdateTime;
    }
}
