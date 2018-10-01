package org.ylgzs.info.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.ylgzs.info.constant.UserConst;
import org.ylgzs.info.dao.UserInfoMapper;
import org.ylgzs.info.enums.ResultEnum;
import org.ylgzs.info.enums.UserEnum;
import org.ylgzs.info.pojo.UserInfo;
import org.ylgzs.info.service.IUserService;
import org.ylgzs.info.util.MD5Util;
import org.ylgzs.info.vo.ServerResponse;

import java.util.List;

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
        if (!StringUtils.isEmpty(type)) {
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

        if (userInfo == null) {
            return ServerResponse.isError(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
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
        userInfo.setUserEmailConfirm(0);
        int count = userInfoMapper.insert(userInfo);
        if (count > 0) {
            return ServerResponse.isSuccess();
        }
        return ServerResponse.isError(UserEnum.REGISTRATION_FAILED.getMessage());
    }

    @Override
    public ServerResponse<UserInfo> updateInformation(UserInfo userInfo) {
        //user_login_name不能更新
        //email校验
        log.info("userInfo = {}", userInfo.toString());
        UserInfo emailCount = userInfoMapper.checkUserByEmail(userInfo.getUserEmail());
        if (emailCount != null && emailCount.getUserId().equals(userInfo.getUserId())) {
            UserInfo update = new UserInfo();
            update.setUserId(userInfo.getUserId());
            update.setUserName(userInfo.getUserName());
            update.setUserEmail(userInfo.getUserEmail());
            update.setDepartmentDepartmentId(userInfo.getDepartmentDepartmentId());
            update.setGradeGradeId(userInfo.getGradeGradeId());

            int updateCount = userInfoMapper.updateByPrimaryKeySelective(update);
            if (updateCount > 0) {
                return ServerResponse.isSuccess(update);
            }
            return ServerResponse.isError(UserEnum.UPDATE_FAILED.getMessage());
        }
        return ServerResponse.isError(UserEnum.INVALID_EMAIL.getMessage());
    }

    @Override
    public ServerResponse<String> resetPassword(String oldPass, String newPass, UserInfo userInfo) {
        //校验旧密码
        int oldCount = userInfoMapper.checkPassword(MD5Util.MD5EncodeUtf8(oldPass), userInfo.getUserId());
        if (oldCount == 0) {
            return ServerResponse.isError(UserEnum.OLD_PASS_WRONG.getMessage());
        }
        if (oldPass.equals(newPass)) {
            return ServerResponse.isError(UserEnum.NEW_PASS_SAME_AS_OLD.getMessage());
        }
        UserInfo updatePass = new UserInfo();
        updatePass.setUserId(userInfo.getUserId());
        updatePass.setUserPassword(MD5Util.MD5EncodeUtf8(newPass));
        int update = userInfoMapper.updateByPrimaryKeySelective(updatePass);
        if (update > 0) {
            return ServerResponse.isSuccess();
        }
        return ServerResponse.isError(UserEnum.UPDATE_PASSWORD_FAILED.getMessage());

    }

    @Override
    public ServerResponse<UserInfo> getUserInfo(Integer userId) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if (userInfo == null) {
            return ServerResponse.isError();
        }
        userInfo.setUserPassword("");
        return ServerResponse.isSuccess(userInfo);
    }

    @Override
    public ServerResponse<PageInfo> listUser(int pageNum, int pageSize, String gradeId, Integer departmentId, Integer role) {
        //startPage--start
        //填充自己的sql查询逻辑
        //pageHelper-收尾
        PageHelper.startPage(pageNum, pageSize);
        List<UserInfo> userInfoList = userInfoMapper.selectList(gradeId, departmentId, role);
        if (CollectionUtils.isEmpty(userInfoList)) {
            return ServerResponse.isError(UserEnum.LIST_USER_FAILED.getMessage());
        }
        //密码置空
        userInfoList.forEach(e -> e.setUserPassword(null));
        PageInfo pageInfo = new PageInfo<>(userInfoList);
        return ServerResponse.isSuccess(pageInfo);
    }
}
