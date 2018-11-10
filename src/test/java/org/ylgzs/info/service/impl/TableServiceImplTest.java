package org.ylgzs.info.service.impl;

import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.ylgzs.info.vo.ServerResponse;



/**
 * @ClassName TableServiceImplTest
 * @Description TODO
 * @Author alex
 * @Date 2018/10/29
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class TableServiceImplTest {

    @Autowired
    private TableServiceImpl tableService;

    @Test
    public void findByIdAndName() {
        ServerResponse serverResponse = tableService.find("5_c30a012d_e2ae_4b9b_ad9e_9c1e4d14b730","学号",
                "2016220401014","姓名","赵建成");
        System.out.println(serverResponse.toString());
    }
}