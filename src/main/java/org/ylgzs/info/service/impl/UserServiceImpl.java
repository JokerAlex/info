package org.ylgzs.info.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.ylgzs.info.constant.UserConst;
import org.ylgzs.info.dao.UserInfoMapper;
import org.ylgzs.info.enums.ResultEnum;
import org.ylgzs.info.enums.UserEnum;
import org.ylgzs.info.pojo.UserInfo;
import org.ylgzs.info.service.IUserService;
import org.ylgzs.info.util.MD5Util;
import org.ylgzs.info.vo.ServerResponse;

/**
 * @ClassName UserServiceImpl
 * @Description 用户信息操作实现
 * @Author alex
 * @Date 2018/9/30
 **/
@Service("iUserService")
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        log.info("srt = {}, type = {}.", str, type);
        if (StringUtils.isEmpty(type)) {
            //判断检查类型，登录名、邮箱
            if (UserConst.USER_LOGIN_NAME.equals(type)) {
                int count = userInfoMapper.checkUserLoginName(str);
                if (count > 0) {
                    return ServerResponse.isError(UserEnum.INVALID_USER_LOGIN_NAME.getMessage());
                }
            } else if (UserConst.EMAIL.equals(type)){
                int count = userInfoMapper.checkEmail(str);
                if (count > 0) {
                    return ServerResponse.isError(UserEnum.INVALID_EMAIL.getMessage());
                }
            } else {
                return ServerResponse.isError(ResultEnum.ILLEGAL_PARAMETER.getMessage());
            }
        } else {
            return ServerResponse.isError(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        return ServerResponse.isSuccess();
    }

    @Override
    public ServerResponse<String> register(UserInfo userInfo) {
        log.info("userInfo = {}", userInfo.toString());
        //检查用户名是否可用
        ServerResponse<String> validResponse = this.checkValid(userInfo.getUserLoginName(), UserConst.USER_LOGIN_NAME);
        if (!validResponse.isOk()) {
            return validResponse;
        }
        //检查邮箱是否可用
        validResponse = this.checkValid(userInfo.getUserEmail(), UserConst.EMAIL);
        if (!validResponse.isOk()) {
            return validResponse;
        }
        //加密
        userInfo.setUserPassword(MD5Util.MD5EncodeUtf8(userInfo.getUserPassword()));
        int count = userInfoMapper.insert(userInfo);
        if (count > 0) {
            return ServerResponse.isSuccess();
        }
        return ServerResponse.isError(UserEnum.REGISTRATION_FAILED.getMessage());
    }
}
