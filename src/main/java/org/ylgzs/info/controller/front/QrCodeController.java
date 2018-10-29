package org.ylgzs.info.controller.front;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ylgzs.info.pojo.QrCodeRecord;
import org.ylgzs.info.service.IQrCodeService;
import org.ylgzs.info.util.JwtUtil;
import org.ylgzs.info.vo.ServerResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName QrCodeController
 * @Description qr code controller
 * @Author alex
 * @2Date 2018/10/29
 **/
@RestController
@RequestMapping("/qr")
@Api(value = "发布记录", description = "发布记录管理")
public class QrCodeController {

    private final IQrCodeService iQrCodeService;

    @Autowired
    public QrCodeController(IQrCodeService iQrCodeService) {
        this.iQrCodeService = iQrCodeService;
    }

    @ApiOperation(value = "检查发布记录名称", notes = "检查发布记录名称是否可用")
    @ApiImplicitParam(name = "qrName", value = "名称", required = true, dataTypeClass = String.class)
    @GetMapping("/check")
    public ServerResponse checkQrName(String qrName, HttpServletRequest request) throws Exception {
        Integer userId = JwtUtil.getUserIdFromToken(request);
        return iQrCodeService.checkQrName(userId ,qrName);
    }

    @ApiOperation(value = "添加发布记录", notes = "添加新的发布记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qrName", value = "名称", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "description", value = "描述", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "tableIds", value = "表格 ID 集合", required = true, dataTypeClass = List.class),
    })
    @PostMapping()
    public ServerResponse addQrCode(String qrName, String description, List<Integer> tableIds, HttpServletRequest request) throws Exception {
        Integer userId = JwtUtil.getUserIdFromToken(request);
        return iQrCodeService.addQrCode(userId, qrName, description, tableIds);
    }

    @ApiOperation(value = "更新发布记录", notes = "更新发布记录")
    @ApiImplicitParam(name = "qrCodeRecord", value = "发布记录实体", required = true, dataTypeClass = QrCodeRecord.class)
    @PutMapping()
    public ServerResponse updateRecode(@RequestBody QrCodeRecord qrCodeRecord, HttpServletRequest request) throws Exception {
        Integer userId = JwtUtil.getUserIdFromToken(request);
        return iQrCodeService.updateRecord(userId, qrCodeRecord);
    }

    @ApiOperation(value = "更新发布记录状态", notes = "更新发布记录状态")
    @PutMapping("/{recodeId}/{status}")
    public ServerResponse<String> updateStatus(@PathVariable("recodeId") Integer recodeId,
                                               @PathVariable("status") Integer status,
                                               HttpServletRequest request) throws Exception {
        Integer userId = JwtUtil.getUserIdFromToken(request);
        return iQrCodeService.updateStatus(userId, recodeId, status);
    }

    @ApiOperation(value = "删除发布记录", notes = "删除发布记录")
    @DeleteMapping("/{recodeId}")
    public ServerResponse delRecode(@PathVariable("recodeId") Integer recodeId, HttpServletRequest request) throws Exception {
        Integer userId = JwtUtil.getUserIdFromToken(request);
        return iQrCodeService.delRecord(userId, recodeId);
    }

    @ApiOperation(value = "批量删除发布记录", notes = "批量删除发布记录")
    @ApiImplicitParam(name = "recodeIds", value = "发布记录 ID 集合", required = true, dataTypeClass = List.class)
    @DeleteMapping()
    public ServerResponse delRecodeBatch(@RequestBody List<Integer> recodeIds) {
        return iQrCodeService.delRecordBatch(recodeIds);
    }

    @ApiOperation(value = "获取发布记录列表", notes = "获取发布记录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, dataTypeClass = Integer.class)
    })
    @GetMapping()
    public ServerResponse listRecode(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                     HttpServletRequest request) throws Exception {
        Integer userId = JwtUtil.getUserIdFromToken(request);
        return iQrCodeService.listRecord(pageNum, pageSize, userId);
    }

    @ApiOperation(value = "获取发布记录详情", notes = "获取发布记录详情")
    @GetMapping("/{recodeId}")
    public ServerResponse getRecodeDetailsById(@PathVariable("recodeId") Integer recodeId, HttpServletRequest request) throws Exception {
        Integer userId = JwtUtil.getUserIdFromToken(request);
        return iQrCodeService.getRecordDetail(userId, recodeId);
    }

    @ApiOperation(value = "获取发布记录详情", notes = "获取发布记录详情")
    @GetMapping("/{recodeCode}")
    public ServerResponse getRecodeDetailsByCode(@PathVariable("recodeCode") String recodeCode) {
        return iQrCodeService.getRecordDetail(recodeCode);
    }
}
