package org.ylgzs.info.dao;

import org.springframework.stereotype.Repository;
import org.ylgzs.info.pojo.Grade;

import java.util.List;

@Repository
public interface GradeMapper {
    int deleteByPrimaryKey(String gradeId);

    int insert(Grade record);

    int insertSelective(Grade record);

    Grade selectByPrimaryKey(String gradeId);

    int updateByPrimaryKeySelective(Grade record);

    int updateByPrimaryKey(Grade record);

    List<Grade> selectList();
}