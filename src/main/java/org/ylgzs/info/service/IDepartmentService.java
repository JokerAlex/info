package org.ylgzs.info.service;

import org.ylgzs.info.pojo.Department;
import org.ylgzs.info.vo.DepartmentVo;
import org.ylgzs.info.vo.ServerResponse;

import java.util.List;

/**
 * @ClassName IDepartmentService
 * @Description 部门
 * @Author alex
 * @Date 2018/10/1
 **/
public interface IDepartmentService {

    /**
     * 获取所有部门
     * @return
     */
    ServerResponse<List<DepartmentVo>> listDepartment();

    /**
     * 添加部门
     * @param departmentName
     * @return
     */
    ServerResponse<String> addDepartment(String departmentName);

    /**
     * 更新部门信息
     * @param department
     * @return
     */
    ServerResponse<String> updateDepartment(Department department);

}
