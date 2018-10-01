package org.ylgzs.info.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.ylgzs.info.pojo.UserInfo;

import java.util.List;

/**
 * @author alex
 */
@Repository
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    int checkUserLoginName(String userLoginName);

    int checkEmail(String email);

    UserInfo checkUserByEmail(String email);

    int checkPassword(@Param("pass")String pass, @Param("userId")Integer userId);

    List<UserInfo> selectList(@Param("gradeId")String gradeId, @Param("departmentId")Integer departmentId, @Param("role")Integer role);
}