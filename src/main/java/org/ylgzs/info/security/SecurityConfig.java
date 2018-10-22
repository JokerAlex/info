package org.ylgzs.info.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.ylgzs.info.security.entrypoint.UserAuthenticationEntryPoint;
import org.ylgzs.info.security.filter.UserAuthenticationTokenFilter;
import org.ylgzs.info.security.handler.UserAuthenticationFailureHandler;
import org.ylgzs.info.security.handler.UserAuthenticationSuccessHandler;

/**
 * @ClassName SecurityConfig
 * @Description config
 * @Author alex
 * @Date 2018/10/14
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    private final UserDetailsService userDetailsService;
    private final UserAuthenticationSuccessHandler authenticationSuccessHandler;
    private final UserAuthenticationFailureHandler authenticationFailureHandler;
    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthenticationTokenFilter userAuthenticationTokenFilter;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService,
                          UserAuthenticationSuccessHandler authenticationSuccessHandler,
                          UserAuthenticationFailureHandler authenticationFailureHandler,
                          UserAuthenticationEntryPoint userAuthenticationEntryPoint,
                          UserAuthenticationTokenFilter userAuthenticationTokenFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.userAuthenticationEntryPoint = userAuthenticationEntryPoint;
        this.userAuthenticationTokenFilter = userAuthenticationTokenFilter;
    }


    /**
     * 装载BCrypt密码编码器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()

                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 添加JWT filter
                .addFilterBefore(userAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)

                .authorizeRequests()
                //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                //.antMatchers("/swagger-resources/**","/swagger-ui.html/**","/v2/**").permitAll()
                .antMatchers("/login", "/register").permitAll()
                // swagger start 页面访问403错误
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/configuration/security").permitAll()
                // swagger end
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);

        // 禁用缓存
        http.headers().cacheControl();
    }
}
