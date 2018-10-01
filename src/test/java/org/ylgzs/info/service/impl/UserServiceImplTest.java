package org.ylgzs.info.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.ylgzs.info.pojo.UserInfo;
import org.ylgzs.info.vo.ServerResponse;


/**
 * @ClassName UserServiceImplTest
 * @Description user service test
 * @Author alex
 * @Date 2018/10/1
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void checkValid() {
    }

    @Test
    public void register() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserLoginName("test");
        userInfo.setUserPassword("123456");
        userInfo.setUserName("zzz");
        userInfo.setUserEmail("111@163.com");
        userInfo.setDepartmentDepartmentId(1);
        userInfo.setGradeGradeId("2016");
        userInfo.setUserRole(0);
        ServerResponse serverResponse = userService.register(userInfo);
        System.out.println(serverResponse.toString());

    }

    @Test
    public void updateInformation() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1);
        userInfo.setUserLoginName("test");
        userInfo.setUserPassword("123456");
        userInfo.setUserName("cc");
        userInfo.setUserEmail("111@163.com");
        userInfo.setDepartmentDepartmentId(1);
        userInfo.setGradeGradeId("2016");
        userInfo.setUserRole(0);
        ServerResponse serverResponse = userService.updateInformation(userInfo);
        System.out.println(serverResponse.toString());
    }

    @Test
    public void resetPassword() {

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1);
        userInfo.setUserLoginName("test");
        userInfo.setUserPassword("123456");
        userInfo.setUserName("bbbbb");
        userInfo.setUserEmail("111@163.com");
        userInfo.setDepartmentDepartmentId(1);
        userInfo.setGradeGradeId("2016");
        userInfo.setUserRole(0);
        ServerResponse serverResponse = userService.resetPassword("123456","654321",userInfo);
        System.out.println(serverResponse.toString());
    }

    @Test
    public void getUserInfo() {
        ServerResponse serverResponse = userService.getUserInfo(1);
        System.out.println(serverResponse.toString());
    }

    @Test
    public void listUser() {
        ServerResponse serverResponse = userService.listUser(1,10,null,null,null);
        System.out.println(serverResponse.toString());
    }
}