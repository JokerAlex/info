package org.ylgzs.info.controller.front;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.ylgzs.info.service.ITableService;
import org.ylgzs.info.vo.ServerResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName TableInfoController
 * @Description controller
 * @Author alex
 * @Date 2018/10/7
 **/
@RestController
@RequestMapping("/table")
@Api(value = "表格" , description = "表格信息管理")
public class TableInfoController {

    private final ITableService iTableService;

    @Autowired
    public TableInfoController(ITableService iTableService) {
        this.iTableService = iTableService;
    }

    @ApiOperation(value = "检查表名可用", notes = "检查表名是否可用")
    @ApiImplicitParam(name = "tableName", value = "表名", required = true, dataTypeClass = String.class)
    @PostMapping("/checkTableName")
    public ServerResponse<String> checkTableName(HttpServletRequest request, String tableName) {
        // TODO 通过request获取用户信息
        Integer userId = 1;
        return iTableService.checkTableName(userId, tableName);
    }

    @ApiOperation(value = "上传文件", notes = "检查表名是否可用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "表名", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "file", value = "文件", required = true, dataTypeClass = MultipartFile.class)
    })
    @PostMapping(value = "/import", headers = "content-type=multipart/form-data")
    public ServerResponse importTableInfo(HttpServletRequest request, String tableName, MultipartFile file) {
        // TODO 通过request获取用户信息
        Integer userId = 1;
        return iTableService.importInfo(userId, tableName, file);
    }
}
