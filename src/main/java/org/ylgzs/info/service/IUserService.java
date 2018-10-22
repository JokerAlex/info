package org.ylgzs.info.service;

import com.github.pagehelper.PageInfo;
import org.ylgzs.info.exception.ParameterErrorException;
import org.ylgzs.info.pojo.UserInfo;
import org.ylgzs.info.security.entity.TokenResponse;
import org.ylgzs.info.vo.ServerResponse;
import org.ylgzs.info.vo.UserInfoVo;

/**
 * @ClassName IUserService
 * @Description 用户信息操作
 * @Author alex
 * @Date 2018/9/30
 **/
public interface IUserService {

    /**
     * 检查邮箱是否可用（email）
     * 检查登录名称是否可用（login_name）
     * @param str
     * @param type
     * @return
     */
    ServerResponse<String> checkValid(String str, String type) throws ParameterErrorException;

    /**
     * 用户注册
     * @param userInfo
     * @return
     */
    ServerResponse<String> register(UserInfo userInfo);

    /**
     * 更新用户信息
     * @param userInfo
     * @return
     */
    ServerResponse<UserInfoVo> updateInformation(UserInfo userInfo);

    /**
     * 修改密码
     * @param oldPass
     * @param newPass
     * @param userId
     * @return
     */
    ServerResponse<String> resetPassword(String oldPass, String newPass, Integer userId);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    ServerResponse<UserInfoVo> getUserInfo(Integer userId);

    /**
     * 获取用户列表
     * @param pageNum
     * @param pageSize
     * @param gradeId
     * @param departmentId
     * @param role
     * @return
     */
    ServerResponse<PageInfo> listUser(int pageNum, int pageSize, String gradeId, Integer departmentId, Integer role);
}
