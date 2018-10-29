package org.ylgzs.info.controller.front;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.ylgzs.info.enums.ResultEnum;
import org.ylgzs.info.exception.ParameterErrorException;
import org.ylgzs.info.pojo.TableInfo;
import org.ylgzs.info.pojo.TableInfoKey;
import org.ylgzs.info.service.ITableService;
import org.ylgzs.info.util.JwtUtil;
import org.ylgzs.info.vo.ServerResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName TableInfoController
 * @Description controller
 * @Author alex
 * @Date 2018/10/7
 **/
@RestController
@RequestMapping("/table")
@Api(value = "表格", description = "表格信息管理")
public class TableInfoController {

    private final ITableService iTableService;

    @Autowired
    public TableInfoController(ITableService iTableService) {
        this.iTableService = iTableService;
    }

    @ApiOperation(value = "检查表名可用", notes = "检查表名是否可用")
    @ApiImplicitParam(name = "tableName", value = "表名", required = true, dataTypeClass = String.class)
    @GetMapping("/checkTableName")
    public ServerResponse<String> checkTableName(HttpServletRequest request, String tableName) throws Exception {
        Integer userId = JwtUtil.getUserIdFromToken(request);
        return iTableService.checkTableName(userId, tableName);
    }

    @ApiOperation(value = "上传文件", notes = "检查表名是否可用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "表名", required = true, dataTypeClass = String.class)
    })
    @PostMapping(value = "/import", headers = "content-type=multipart/form-data")
    public ServerResponse importTableInfo(HttpServletRequest request,
                                          String tableName,
                                          @ApiParam(value = "文件", required = true) MultipartFile file) throws Exception {
        Integer userId = JwtUtil.getUserIdFromToken(request);
        return iTableService.importInfo(userId, tableName, file);
    }

    @ApiOperation(value = "更新表格信息", notes = "文件上传完成后的信息完善，表格信息更新")
    @ApiImplicitParam(name = "tableInfo", value = "表格信息实体", required = true, dataTypeClass = TableInfo.class)
    @PutMapping("/{tableId}")
    public ServerResponse updateTableInfo(@PathVariable("tableId") Integer tableId,
                                          @RequestBody TableInfo tableInfo,
                                          HttpServletRequest request) throws Exception {
        if (tableId == null) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        tableInfo.setTableInfoId(tableId);
        Integer userId = JwtUtil.getUserIdFromToken(request);
        return iTableService.updateTableInfo(userId, tableInfo);
    }

    @ApiOperation(value = "删除表格", notes = "单个表格删除")
    @DeleteMapping("/{tableId}")
    public ServerResponse delTableInfo(@PathVariable("tableId") Integer tableId,
                                       HttpServletRequest request) throws Exception {
        Integer userId = JwtUtil.getUserIdFromToken(request);
        return iTableService.delTableInfo(userId, tableId);
    }

    @ApiOperation(value = "删除表格", notes = "批量表格删除")
    @ApiImplicitParam(name = "tableIds", value = "选中表格ID集合", required = true, dataTypeClass = List.class)
    @DeleteMapping()
    public ServerResponse delBatch(@RequestBody List<Integer> tableIds) {
        return iTableService.delTableInfoBatch(tableIds);
    }

    @ApiOperation(value = "获取表格列表", notes = "获取表格列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, dataTypeClass = Integer.class)
    })
    @GetMapping()
    public ServerResponse listTableInfo(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                        HttpServletRequest request) throws Exception {
        Integer userId = JwtUtil.getUserIdFromToken(request);
        return iTableService.listTableInfo(pageNum, pageSize, userId, null, null);
    }

    @ApiOperation(value = "获取表格详情", notes = "获取表格详情")
    @GetMapping("/{tableId}")
    public ServerResponse getTableInfoDetails(@PathVariable("tableId") Integer tableId,
                                              HttpServletRequest request) throws Exception {
        if (tableId == null) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        Integer userId = JwtUtil.getUserIdFromToken(request);
        TableInfoKey tableInfoKey = new TableInfoKey(tableId, userId);
        return iTableService.getTableInfoDetail(tableInfoKey);
    }

    @ApiOperation(value = "信息查询", notes = "信息查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "collectionName", value = "集合名称", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "field1", value = "字段1", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "value1", value = "值1", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "field2", value = "字段2", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "value2", value = "值2", required = true, dataTypeClass = String.class)
    })
    @GetMapping("/find")
    public ServerResponse find(String collectionName,String field1, String value1,String field2, String value2) {
        return iTableService.find(collectionName, field1, value1, field2, value2);
    }
}
