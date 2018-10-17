package org.ylgzs.info.security.filter;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.ylgzs.info.pojo.UserInfo;
import org.ylgzs.info.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName UserAuthenticationTokenFilter
 * @Description filter
 * @Author alex
 * @Date 2018/10/14
 **/
@Component
@Slf4j
public class UserAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private final UserDetailsService userDetailsService;

    @Autowired
    public UserAuthenticationTokenFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(this.tokenHeader);

        if (header != null && header.startsWith(tokenHead)) {
            String token = header.substring(tokenHead.length());
            Claims claims;
            UserInfo userInfo = new UserInfo();
            try {
                 claims = JwtUtil.parseJwt(token);
                 userInfo.setUserId(claims.get("userId", Integer.class));
                 userInfo.setUserLoginName(claims.get("userLoginName", String.class));
                 userInfo.setUserName(claims.get("userName", String.class));
            } catch (Exception e) {
                log.error(e.getMessage());
            }

            if (userInfo.getUserId() != null
                    && userInfo.getUserName() != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(userInfo.getUserLoginName());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("authenticated user = {}, setting security context", userDetails.getUsername());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
