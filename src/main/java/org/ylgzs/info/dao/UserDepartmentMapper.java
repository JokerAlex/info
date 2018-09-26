package org.ylgzs.info.dao;

import org.ylgzs.info.pojo.UserDepartment;
import org.ylgzs.info.pojo.UserDepartmentKey;

public interface UserDepartmentMapper {
    int deleteByPrimaryKey(UserDepartmentKey key);

    int insert(UserDepartment record);

    int insertSelective(UserDepartment record);

    UserDepartment selectByPrimaryKey(UserDepartmentKey key);

    int updateByPrimaryKeySelective(UserDepartment record);

    int updateByPrimaryKey(UserDepartment record);
}