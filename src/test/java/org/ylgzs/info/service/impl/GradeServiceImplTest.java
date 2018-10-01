package org.ylgzs.info.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.ylgzs.info.vo.ServerResponse;

import static org.junit.Assert.*;

/**
 * @ClassName GradeServiceImplTest
 * @Description test
 * @Author alex
 * @Date 2018/10/1
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class GradeServiceImplTest {

    @Autowired
    private GradeServiceImpl gradeService;

    @Test
    public void listGrade() {
        ServerResponse serverResponse = gradeService.listGrade();
        System.out.println(serverResponse.toString());
    }

    @Test
    public void addGrade() {
        ServerResponse serverResponse = gradeService.addGrade("2017");
        System.out.println(serverResponse.toString());
    }
}