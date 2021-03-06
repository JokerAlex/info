package org.ylgzs.info.service;

import com.github.pagehelper.PageInfo;
import org.ylgzs.info.pojo.QrCodeRecord;
import org.ylgzs.info.vo.QrCodeRecordDetailVo;
import org.ylgzs.info.vo.ServerResponse;

import java.util.List;

/**
 * @ClassName IQrCodeService
 * @Description qr code
 * @Author alex
 * @Date 2018/10/6
 **/
public interface IQrCodeService {

    /**
     * 校验发布记录名称
     * @param userId
     * @param qrName
     * @return
     */
    ServerResponse<String> checkQrName(Integer userId, String qrName);

    /**
     *新增发布记录
     * @param userId
     * @param qrName
     * @param description
     * @param tableIds
     * @return
     */
    ServerResponse<String> addQrCode(Integer userId, String qrName, String description, List<Integer> tableIds);

    /**
     * 更新发布记录信息
     * @param userId
     * @param qrCodeRecord
     * @return
     */
    ServerResponse<String> updateRecord(Integer userId, QrCodeRecord qrCodeRecord);

    /**
     * 修改发布记录查询状态
     * @param userId
     * @param recodeId
     * @param status
     * @return
     */
    ServerResponse<String> updateStatus(Integer userId, Integer recodeId, Integer status);

    /**
     * 删除发布记录
     * @param userId
     * @param recodeId
     * @return
     */
    ServerResponse<String> delRecord(Integer userId, Integer recodeId);

    /**
     * 批量删除
     * @param recordIds
     * @return
     */
    ServerResponse<String> delRecordBatch(List<Integer> recordIds);

    /**
     * 后去发布记录列表
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    ServerResponse<PageInfo> listRecord(Integer pageNum, Integer pageSize, Integer userId);

    /**
     * 获取发布记录详情
     * @param userId
     * @param recodeId
     * @return
     */
    ServerResponse<QrCodeRecordDetailVo> getRecordDetail(Integer userId, Integer recodeId);

    /**
     * 外放查询使用
     * @param recordCode
     * @return
     */
    ServerResponse<QrCodeRecordDetailVo> getRecordDetail(String recordCode);
}
