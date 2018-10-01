package org.ylgzs.info.service;

import org.ylgzs.info.pojo.UserInfo;
import org.ylgzs.info.vo.ServerResponse;

/**
 * @ClassName IUserService
 * @Description 用户信息操作
 * @Author alex
 * @Date 2018/9/30
 **/
public interface IUserService {

    /**
     * 检查邮箱是否可用（email）
     * 检查登录名称是否可用（loginname）
     * @param str
     * @param type
     * @return
     */
    ServerResponse<String> checkValid(String str, String type);

    /**
     * 用户注册
     * @param userInfo
     * @return
     */
    ServerResponse<String> register(UserInfo userInfo);
}
