package org.ylgzs.info.service;

import org.ylgzs.info.pojo.Grade;
import org.ylgzs.info.vo.ServerResponse;

import java.util.List;

/**
 * @ClassName IGradeService
 * @Description 年级
 * @Author alex
 * @Date 2018/10/1
 **/
public interface IGradeService {

    /**
     * 获取所有年级信息
     * @return
     */
    ServerResponse<List<Grade>> listGrade();

    /**
     * 添加年级信息
     * @param gradeId
     * @return
     */
    ServerResponse<String> addGrade(String gradeId);
}
