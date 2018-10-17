package org.ylgzs.info.security.entity;

import lombok.Data;
import org.ylgzs.info.vo.UserInfoVo;

/**
 * @ClassName TokenResponse
 * @Description token response
 * @Author alex
 * @Date 2018/10/14
 **/
@Data
public class TokenResponse {
    private String token;

    private UserInfoVo userInfoVo;

    public TokenResponse(String token, UserInfoVo userInfoVo) {
        this.token = token;
        this.userInfoVo = userInfoVo;
    }
}
