package org.ylgzs.info.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.ylgzs.info.pojo.Department;
import org.ylgzs.info.vo.ServerResponse;

import static org.junit.Assert.*;

/**
 * @ClassName DepartmentServiceImplTest
 * @Description test
 * @Author alex
 * @Date 2018/10/1
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentServiceImplTest {

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Test
    public void listDepartment() {
        ServerResponse serverResponse = departmentService.listDepartment();
        System.out.println(serverResponse.toString());
    }

    @Test
    public void addDepartment() {
        ServerResponse serverResponse = departmentService.addDepartment("物理学院");
        System.out.println(serverResponse.toString());
    }

    @Test
    public void updateDepartment() {
        Department department = new Department();
        department.setDepartmentId(2);
        department.setDepartmentName("物理");
        ServerResponse serverResponse = departmentService.updateDepartment(department);
        System.out.println(serverResponse.toString());
    }
}