package org.ylgzs.info.security.entrypoint;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.ylgzs.info.vo.ServerResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @ClassName UserAuthenticationEntryPoint
 * @Description entry point 401
 * @Author alex
 * @Date 2018/10/15
 **/
@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // This is invoked when user tries to access a secured REST resource without supplying any credentials
        // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

        // 返回json形式的错误信息

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("{\"status\":401,\"msg\":\"对不起，您没有认证，或认证已过期\"}");
        response.getWriter().flush();
    }

}
