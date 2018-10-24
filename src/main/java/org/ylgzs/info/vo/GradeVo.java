package org.ylgzs.info.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @ClassName GradeVo
 * @Description vo
 * @Author alex
 * @Date 2018/10/7
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GradeVo {
    private String gradeId;

    private String gradeCreateTime;

    private String gradeUpdateTime;

    public GradeVo() {
    }

    public GradeVo(String gradeId, String gradeCreateTime, String gradeUpdateTime) {
        this.gradeId = gradeId;
        this.gradeCreateTime = gradeCreateTime;
        this.gradeUpdateTime = gradeUpdateTime;
    }
}
