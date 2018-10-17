package org.ylgzs.info.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.ylgzs.info.pojo.UserInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @ClassName JwtUserDetail
 * @Description user
 * @Author alex
 * @Date 2018/10/14
 **/
public class JwtUserDetail implements UserDetails {

    private UserInfo userInfo;

    public JwtUserDetail(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userInfo.getUserRole() == null) {
            return null;
        }
        return Arrays.stream(userInfo.getUserRole().split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return userInfo.getUserPassword();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return userInfo.getUserName();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
