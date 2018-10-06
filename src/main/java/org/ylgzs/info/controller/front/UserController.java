package org.ylgzs.info.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.ylgzs.info.service.IUserService;

/**
 * @ClassName UserController
 * @Description user 控制器
 * @Author alex
 * @Date 2018/10/1
 **/
@RestController
public class UserController {

    @Autowired
    private IUserService iUserService;


}
