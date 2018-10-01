package org.ylgzs.info.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.ylgzs.info.dao.GradeMapper;
import org.ylgzs.info.enums.GradeEnum;
import org.ylgzs.info.enums.ResultEnum;
import org.ylgzs.info.pojo.Grade;
import org.ylgzs.info.service.IGradeService;
import org.ylgzs.info.vo.ServerResponse;

import java.util.List;

/**
 * @ClassName GradeServiceImpl
 * @Description 年级
 * @Author alex
 * @Date 2018/10/1
 **/
@Service("iGradeService")
@Slf4j
public class GradeServiceImpl implements IGradeService {

    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public ServerResponse<List<Grade>> listGrade() {
        List<Grade> gradeList = gradeMapper.selectList();
        if (CollectionUtils.isEmpty(gradeList)) {
            return ServerResponse.isError(GradeEnum.LIST_GRADE_FAILED.getMessage());
        }
        log.info(gradeList.toString());
        return ServerResponse.isSuccess(gradeList);
    }

    @Override
    public ServerResponse<String> addGrade(String gradeId) {
        log.info("grade = {}", gradeId);
        if (StringUtils.isEmpty(gradeId)) {
            return ServerResponse.isError(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        if (Integer.valueOf(gradeId) > 2050) {
            return ServerResponse.isError(GradeEnum.TOO_BIG.getMessage());
        }
        Grade grade = new Grade();
        grade.setGradeId(gradeId);
        int count = gradeMapper.insertSelective(grade);
        if (count > 0) {
            return ServerResponse.isSuccess();
        }
        return ServerResponse.isError(GradeEnum.ADD_GRADE_FAILED.getMessage());
    }

}
