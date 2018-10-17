package org.ylgzs.info.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.ylgzs.info.pojo.UserInfo;
import org.ylgzs.info.security.entity.JwtUserDetail;
import org.ylgzs.info.security.entity.TokenResponse;
import org.ylgzs.info.util.JwtUtil;
import org.ylgzs.info.vo.ServerResponse;
import org.ylgzs.info.vo.UserInfoVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserAuthenticationSuccessHandler
 * @Description success
 * @Author alex
 * @Date 2018/10/14
 **/
@Component
@Slf4j
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    @Autowired
    public UserAuthenticationSuccessHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        JwtUserDetail jwtUserDetail = (JwtUserDetail) authentication.getPrincipal();
        UserInfo userInfo = jwtUserDetail.getUserInfo();

        Map<String, Object> claimsMap = new HashMap<>(2);
        claimsMap.put("userId", userInfo.getUserId());
        claimsMap.put("userLoginName", userInfo.getUserLoginName());
        claimsMap.put("userName", userInfo.getUserName());

        String token = JwtUtil.createJwt(claimsMap);

        UserInfoVo userInfoVo = new UserInfoVo(userInfo.getUserId(),
                userInfo.getUserLoginName(),
                userInfo.getUserName(),
                userInfo.getUserEmail(),
                userInfo.getUserEmailConfirm(),
                userInfo.getDepartmentDepartmentId(),
                userInfo.getGradeGradeId(),
                userInfo.getUserRole());

        TokenResponse tokenResponse = new TokenResponse(token, userInfoVo);
        log.info("【登录成功】user = {}", userInfo.toString());
        response.setHeader("Content-type", "application/json;charset=utf-8");
        objectMapper.writeValue(response.getWriter(), ServerResponse.isSuccess(tokenResponse));
    }
}
