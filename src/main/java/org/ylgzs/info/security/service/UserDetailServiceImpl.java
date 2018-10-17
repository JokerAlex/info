package org.ylgzs.info.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ylgzs.info.dao.UserInfoMapper;
import org.ylgzs.info.security.entity.JwtUserDetail;

import java.util.Optional;

/**
 * @ClassName UserDetailServiceImpl
 * @Description user detail
 * @Author alex
 * @Date 2018/10/14
 **/
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserInfoMapper userInfoMapper;

    @Autowired
    public UserDetailServiceImpl(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        return Optional
                .ofNullable(userInfoMapper.selectByUserLoginName(loginName))
                .map(JwtUserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + loginName));
    }
}
