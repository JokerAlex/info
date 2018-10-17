package org.ylgzs.info.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.ylgzs.info.vo.ServerResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AuthenticationFailureHandler
 * @Description failure
 * @Author alex
 * @Date 2018/10/14
 **/
@Component
@Slf4j
public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {


    private final ObjectMapper objectMapper;

    @Autowired
    public UserAuthenticationFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
        log.info("【登录失败】");
        response.setHeader("Content-type", "application/json;charset=utf-8");
        objectMapper.writeValue(response.getWriter(), ServerResponse.isError(e.getMessage()));
    }
}
