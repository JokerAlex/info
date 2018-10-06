package org.ylgzs.info.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.ylgzs.info.dao.DepartmentMapper;
import org.ylgzs.info.enums.DepartmentEnum;
import org.ylgzs.info.enums.ResultEnum;
import org.ylgzs.info.pojo.Department;
import org.ylgzs.info.service.IDepartmentService;
import org.ylgzs.info.vo.ServerResponse;

import java.util.List;

/**
 * @ClassName DepartmentServiceImpl
 * @Description 部门
 * @Author alex
 * @Date 2018/10/1
 **/
@Service("iDepartmentService")
@Slf4j
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public ServerResponse<List<Department>> listDepartment() {
        List<Department> departmentList = departmentMapper.selectList();
        if (CollectionUtils.isEmpty(departmentList)) {
            return ServerResponse.isError(DepartmentEnum.LIST_DEPARTMENT_FAILED.getMessage());
        }
        log.info("departmentList = {}", departmentList);
        return ServerResponse.isSuccess(departmentList);
    }

    @Override
    public ServerResponse<String> addDepartment(String departmentName) {
        if (StringUtils.isEmpty(departmentName)) {
            return ServerResponse.isError(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        Department department = new Department();
        department.setDepartmentName(departmentName);
        int count = departmentMapper.insertSelective(department);
        if (count > 0) {
            return ServerResponse.isSuccess();
        }
        return ServerResponse.isError(DepartmentEnum.ADD_DEPARTMENT_FAILED.getMessage());
    }

    @Override
    public ServerResponse<String> updateDepartment(Department department) {
        if (department == null || StringUtils.isEmpty(department.getDepartmentName())) {
            return ServerResponse.isError(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        Department update = new Department();
        update.setDepartmentId(department.getDepartmentId());
        update.setDepartmentName(department.getDepartmentName());

        int count = departmentMapper.updateByPrimaryKeySelective(update);
        if (count > 0) {
            return ServerResponse.isSuccess();
        }
        return ServerResponse.isError(DepartmentEnum.UPDATE_DEPARTMENT_FAILED.getMessage());
    }
}
