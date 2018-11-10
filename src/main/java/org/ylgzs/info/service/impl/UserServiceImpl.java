package org.ylgzs.info.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.ylgzs.info.constant.Constants;
import org.ylgzs.info.dao.UserInfoMapper;
import org.ylgzs.info.enums.ResultEnum;
import org.ylgzs.info.enums.UserEnum;
import org.ylgzs.info.exception.GeneralException;
import org.ylgzs.info.exception.NotFoundException;
import org.ylgzs.info.exception.ParameterErrorException;
import org.ylgzs.info.pojo.UserInfo;
import org.ylgzs.info.service.IUserService;
import org.ylgzs.info.vo.ServerResponse;
import org.ylgzs.info.vo.UserInfoVo;

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

    private final UserInfoMapper userInfoMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserInfoMapper userInfoMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) throws ParameterErrorException {
        log.info("srt = {}, type = {}.", str, type);
        if (StringUtils.isEmpty(type)) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        //判断检查类型，登录名、邮箱
        if (Constants.USER_LOGIN_NAME.equals(type)) {
            int count = userInfoMapper.checkUserLoginName(str);
            if (count > 0) {
                return ServerResponse.isError(UserEnum.INVALID_USER_LOGIN_NAME.getMessage());
            }
        } else if (Constants.EMAIL.equals(type)) {
            int count = userInfoMapper.checkEmail(str);
            if (count > 0) {
                return ServerResponse.isError(UserEnum.INVALID_EMAIL.getMessage());
            }
        } else {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        return ServerResponse.isSuccess();
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class)
    public ServerResponse<String> register(UserInfo userInfo) {


        if (userInfo == null) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        log.info("userInfo = {}", userInfo.toString());
        //检查用户名是否可用
        ServerResponse<String> validResponse = this.checkValid(userInfo.getUserLoginName(), Constants.USER_LOGIN_NAME);
        if (!validResponse.isOk()) {
            return validResponse;
        }
        //检查邮箱是否可用
        validResponse = this.checkValid(userInfo.getUserEmail(), Constants.EMAIL);
        if (!validResponse.isOk()) {
            return validResponse;
        }
        UserInfo insertUserInfo = new UserInfo();
        insertUserInfo.setUserLoginName(userInfo.getUserLoginName());
        insertUserInfo.setUserPassword(passwordEncoder.encode(userInfo.getUserPassword()));
        insertUserInfo.setUserName(userInfo.getUserName());
        insertUserInfo.setUserEmail(userInfo.getUserEmail());
        insertUserInfo.setUserEmailConfirm(0);
        insertUserInfo.setDepartmentDepartmentId(userInfo.getDepartmentDepartmentId());
        insertUserInfo.setGradeGradeId(userInfo.getGradeGradeId());
        insertUserInfo.setUserRole(userInfo.getUserRole());
        int count = userInfoMapper.insert(insertUserInfo);
        if (count > 0) {
            return ServerResponse.isSuccess();
        }
        throw new GeneralException(UserEnum.REGISTRATION_FAILED.getMessage());
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class)
    public ServerResponse<UserInfoVo> updateInformation(UserInfo userInfo) {
        if (userInfo == null) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        log.info("userInfo = {}", userInfo.toString());
        UserInfo emailCount = userInfoMapper.checkUserByEmail(userInfo.getUserEmail());
        if (emailCount == null || emailCount.getUserId().equals(userInfo.getUserId())) {
            //可更新字段
            UserInfo updateUserInfo = new UserInfo();
            updateUserInfo.setUserId(userInfo.getUserId());
            updateUserInfo.setUserName(userInfo.getUserName());
            updateUserInfo.setUserEmail(userInfo.getUserEmail());
            updateUserInfo.setDepartmentDepartmentId(userInfo.getDepartmentDepartmentId());
            updateUserInfo.setGradeGradeId(userInfo.getGradeGradeId());

            int updateCount = userInfoMapper.updateByPrimaryKeySelective(updateUserInfo);
            if (updateCount > 0) {
                return this.getUserInfo(updateUserInfo.getUserId());
            }
            return ServerResponse.isError(UserEnum.UPDATE_FAILED.getMessage());
        }
        throw new GeneralException(UserEnum.INVALID_EMAIL.getMessage());
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class)
    public ServerResponse<String> resetPassword(String oldPass, String newPass, Integer userId) {
        //校验旧密码
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if (!passwordEncoder.matches(oldPass, userInfo.getUserPassword())) {
            return ServerResponse.isError(UserEnum.OLD_PASS_WRONG.getMessage());
        }
        if (oldPass.equals(newPass)) {
            return ServerResponse.isError(UserEnum.NEW_PASS_SAME_AS_OLD.getMessage());
        }
        UserInfo updatePass = new UserInfo();
        updatePass.setUserId(userId);
        updatePass.setUserPassword(passwordEncoder.encode(newPass));
        int update = userInfoMapper.updateByPrimaryKeySelective(updatePass);
        if (update > 0) {
            return ServerResponse.isSuccess();
        }
        throw new GeneralException(UserEnum.UPDATE_PASSWORD_FAILED.getMessage());

    }

    @Override
    public ServerResponse<UserInfoVo> getUserInfo(Integer userId) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if (userInfo == null) {
            throw new NotFoundException("user not found");
        }
        UserInfoVo vo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, vo);
        return ServerResponse.isSuccess(vo);
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
