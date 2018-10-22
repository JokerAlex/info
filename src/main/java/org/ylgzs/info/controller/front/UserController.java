package org.ylgzs.info.controller.front;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.ylgzs.info.enums.ResultEnum;
import org.ylgzs.info.exception.ParameterErrorException;
import org.ylgzs.info.pojo.UserInfo;
import org.ylgzs.info.service.IUserService;
import org.ylgzs.info.util.JwtUtil;
import org.ylgzs.info.vo.ServerResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName UserController
 * @Description user 控制器
 * @Author alex
 * @Date 2018/10/1
 **/
@RestController
@Slf4j
@Api(description = "用户信息信息管理")
public class UserController {

    private final IUserService iUserService;

    @Autowired
    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }


    @ApiOperation(value = "检查登录名称、邮箱", notes = "检查登录名称、邮箱是否可用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "str", value = "登录名、邮箱" , required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "type", value = "类型", required = true, dataTypeClass = String.class)
    })
    @GetMapping("/register")
    public ServerResponse checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    @ApiOperation(value = "用户注册", notes = "用户注册-用户角色ADMIN-管理员，DEPARTMENT-部门管理员,COUNSELOR-辅导员，USER-普通用户")
    @ApiImplicitParam(name = "userInfo", value = "用户实体", required = true, dataTypeClass = UserInfo.class)
    @PostMapping("/register")
    public ServerResponse register(@Valid @RequestBody UserInfo userInfo, BindingResult bindingResult) throws ParameterErrorException {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            List<String> errorList = errors.stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.toList());
            throw new ParameterErrorException(errorList.toString());
        }
        return iUserService.register(userInfo);
    }

    @ApiOperation(value = "用户信息更新", notes = "用户信息更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userInfo", value = "用户实体", required = true, dataTypeClass = UserInfo.class)
    })
    @PutMapping("/user/{id}")
    public ServerResponse updateUserInfo(@PathVariable("id") Integer userId,
                                         @RequestBody UserInfo userInfo,
                                         HttpServletRequest request) throws Exception {
        if (!JwtUtil.getUserIdFromToken(request).equals(userId)) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        return iUserService.updateInformation(userInfo);
    }

    @ApiOperation(value = "获取用户基本信息", notes = "获取用户基本信息")
    @GetMapping("/user/{id}")
    public ServerResponse getDetails(@PathVariable("id") Integer userId,
                                     HttpServletRequest request) throws Exception {
        if (!userId.equals(JwtUtil.getUserIdFromToken(request))) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        return iUserService.getUserInfo(userId);
    }

    @ApiOperation(value = "用户修改密码", notes = "用户修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPass", value = "旧密码", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "newPass", value = "新密码", required = true, dataTypeClass = String.class),
    })
    @PutMapping("/user/pass/{id}")
    public ServerResponse resetPass(@PathVariable("id") Integer userId, String oldPass, String newPass,
                                    HttpServletRequest request) throws Exception {
        if (!JwtUtil.getUserIdFromToken(request).equals(userId)) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        return iUserService.resetPassword(oldPass, newPass, userId);
    }


}
