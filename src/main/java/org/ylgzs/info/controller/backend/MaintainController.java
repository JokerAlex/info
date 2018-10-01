package org.ylgzs.info.controller.backend;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ylgzs.info.pojo.Department;
import org.ylgzs.info.service.IDepartmentService;
import org.ylgzs.info.service.IGradeService;
import org.ylgzs.info.vo.ServerResponse;

/**
 * @ClassName MaintainController
 * @Description 年级学院
 * @Author alex
 * @Date 2018/10/1
 **/
@RestController
@RequestMapping(value = "/manage")
@Api(description = "部门、年级信息管理")
public class MaintainController {

    private IGradeService iGradeService;
    private IDepartmentService iDepartmentService;

    @Autowired
    public MaintainController(IGradeService iGradeService, IDepartmentService iDepartmentService) {
        this.iGradeService = iGradeService;
        this.iDepartmentService = iDepartmentService;
    }


    @ApiOperation(value = "获取年级列表", notes = "获取年级列表")
    @GetMapping("/grade/list")
    public ServerResponse listGrade() {
        return iGradeService.listGrade();
    }

    @ApiOperation(value = "添加年级", notes = "添加年级")
    @ApiImplicitParam(name = "gradeId", value = "年级", required = true, dataTypeClass = String.class)
    @PostMapping("/grade/add")
    public ServerResponse addGrade(String gradeId) {
        return iGradeService.addGrade(gradeId);
    }

    @ApiOperation(value = "部门列表", notes = "获取部门列表")
    @GetMapping("/department/list")
    public ServerResponse listDepartment() {
        return iDepartmentService.listDepartment();
    }

    @ApiOperation(value = "添加部门", notes = "添加部门信息")
    @ApiImplicitParam(name = "department", value = "部门详细实体", required = true, dataTypeClass = Department.class)
    @PostMapping("/department/add")
    public ServerResponse addDepartment(Department department) {
        return iDepartmentService.addDepartment(department);
    }
    @ApiOperation(value = "更新部门", notes = "更新部门信息")
    @ApiImplicitParam(name = "department", value = "部门详细实体", required = true, dataTypeClass = Department.class)
    @PutMapping("/department/update")
    public ServerResponse updateDepartment(Department department) {
        return iDepartmentService.updateDepartment(department);
    }


}
