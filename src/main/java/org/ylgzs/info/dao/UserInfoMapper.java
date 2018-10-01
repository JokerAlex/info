package org.ylgzs.info.dao;

import org.springframework.stereotype.Repository;
import org.ylgzs.info.pojo.UserInfo;

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
}