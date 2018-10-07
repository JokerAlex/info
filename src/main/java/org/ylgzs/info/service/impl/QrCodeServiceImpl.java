package org.ylgzs.info.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ylgzs.info.constant.QrCodeConst;
import org.ylgzs.info.dao.QrCodeRecordMapper;
import org.ylgzs.info.dao.QrCodeTableMapper;
import org.ylgzs.info.dto.RecordTableDetailDTO;
import org.ylgzs.info.enums.QrCodeEnum;
import org.ylgzs.info.enums.ResultEnum;
import org.ylgzs.info.pojo.QrCodeRecord;
import org.ylgzs.info.pojo.QrCodeRecordKey;
import org.ylgzs.info.service.IQrCodeService;
import org.ylgzs.info.util.DateTimeUtil;
import org.ylgzs.info.vo.QrCodeRecodeListVo;
import org.ylgzs.info.vo.QrCodeRecordDetailVo;
import org.ylgzs.info.vo.RecordTableVo;
import org.ylgzs.info.vo.ServerResponse;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @ClassName QrCodeServiceImpl
 * @Description qr code
 * @Author alex
 * @Date 2018/10/6
 */

@Slf4j
@Service("iQrCodeService")
public class QrCodeServiceImpl implements IQrCodeService {


    private QrCodeRecordMapper qrCodeRecordMapper;
    private QrCodeTableMapper qrCodeTableMapper;

    @Autowired
    public QrCodeServiceImpl(QrCodeRecordMapper qrCodeRecordMapper, QrCodeTableMapper qrCodeTableMapper) {
        this.qrCodeRecordMapper = qrCodeRecordMapper;
        this.qrCodeTableMapper = qrCodeTableMapper;
    }

    /**
     * 修改发布列表
     * */



    @Override
    @Transactional
    public ServerResponse<String> addQrCode(Integer userId,String qrName,String description, List<Integer> tableIds) {
        if (userId == null || tableIds == null || tableIds.size() == 0 || qrName == null) {
            return ServerResponse.isError(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        log.info("【插入QrCode】 qrName = {}", qrName);
        //生成 code
        String code = UUID.randomUUID().toString().replace("_","");
        QrCodeRecord qrCodeRecord = new QrCodeRecord();
        qrCodeRecord.setUserUserId(userId);
        qrCodeRecord.setQrcodeRecordCode(code);
        qrCodeRecord.setQrcodeRecordName(qrName);
        qrCodeRecord.setQrcodeRecordDescription(description);
        qrCodeRecord.setQrcodeRecordStatus(QrCodeConst.STATUS_ON);
        //插入发布记录
        int qrCount = qrCodeRecordMapper.insertSelective(qrCodeRecord);
        log.info("【插入QrCodeRecode】qrCount = {}", qrCount);
        if (qrCount <= 0) {
            return ServerResponse.isError(QrCodeEnum.RECODE_SAVE_ERROR.getMessage());
        }
        //插入发布记录与表的关联信息
        int qrTableCount = qrCodeTableMapper.insertSelectiveBatch(qrCodeRecord.getQrcodeRecordId(), tableIds);
        log.info("【插入QrCodeTable】qrTableCount = {}", qrTableCount);
        if (qrTableCount != tableIds.size()) {
            return ServerResponse.isError(QrCodeEnum.RECODE_SAVE_ERROR.getMessage());
        }
        return ServerResponse.isSuccess();
    }

    @Override
    @Transactional
    public ServerResponse<String> updateRecode(Integer userId, QrCodeRecord qrCodeRecord) {
        if (userId == null || qrCodeRecord == null || qrCodeRecord.getQrcodeRecordId() == null || !userId.equals(qrCodeRecord.getUserUserId())) {
            return ServerResponse.isError(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        //可更新字段->名称、描述
        QrCodeRecord update = new QrCodeRecord();
        update.setQrcodeRecordId(qrCodeRecord.getQrcodeRecordId());
        update.setUserUserId(qrCodeRecord.getUserUserId());
        update.setQrcodeRecordName(qrCodeRecord.getQrcodeRecordName());
        update.setQrcodeRecordDescription(qrCodeRecord.getQrcodeRecordDescription());
        log.info("【更新 recode】recode = {}", qrCodeRecord.toString());
        int count = qrCodeRecordMapper.updateByPrimaryKeySelective(update);
        log.info("【更新 recode】count = {}", count);
        if (count > 0) {
            return ServerResponse.isSuccess();
        }
        return ServerResponse.isError(QrCodeEnum.UPDATE_RECODE_ERROR.getMessage());
    }

    @Override
    @Transactional
    public ServerResponse<String> updataStatus(Integer userId, Integer recodeId, Integer status) {
        if (userId == null || recodeId == null || status == null) {
            return ServerResponse.isError(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        if (!QrCodeConst.STATUS_ON.equals(status) && !QrCodeConst.STATUS_OFF.equals(status)) {
            return ServerResponse.isError(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }

        QrCodeRecord update = new QrCodeRecord();
        update.setQrcodeRecordId(recodeId);
        update.setUserUserId(userId);
        update.setQrcodeRecordStatus(status);
        log.info("【更新查询状态】update = {}", update.toString());

        int count = qrCodeRecordMapper.updateByPrimaryKeySelective(update);
        log.info("【更新查询状态】count = {}", count);
        if (count > 0) {
            return ServerResponse.isSuccess();
        }
        return ServerResponse.isError(QrCodeEnum.UPDATE_STATUS_ERROR.getMessage());
    }

    @Override
    @Transactional
    public ServerResponse<String> delRecord(Integer userId, Integer recodeId) {
        if (userId == null || recodeId == null) {
            return ServerResponse.isError(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        log.info("【删除记录】 recodeId = {}", recodeId);
        int tableCount = qrCodeTableMapper.deleteByQrCodeId(recodeId);
        log.info("【删除记录】 tableCount = {}", tableCount);

        QrCodeRecordKey qrCodeRecordKey = new QrCodeRecordKey(recodeId, userId);
        int recodeCount = qrCodeRecordMapper.deleteByPrimaryKey(qrCodeRecordKey);
        log.info("【删除记录】 recodeCount = {}", recodeCount);
        if (tableCount > 0 && recodeCount > 0) {
            return ServerResponse.isSuccess();
        }
        return ServerResponse.isError(QrCodeEnum.DEL_RECODE_ERROR.getMessage());
    }

    @Override
    public ServerResponse<PageInfo> listRecode(Integer pageNum, Integer pageSize, Integer userId) {
        if (pageNum == null || pageSize == null || userId == null) {
            return ServerResponse.isError(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        //获取列表
        log.info("【获取发布记录列表】pageNum = {}, pageSize = {}, userId = {}", pageNum, pageSize, userId);
        PageHelper.startPage(pageNum, pageSize);
        List<QrCodeRecord> qrCodeRecordList = qrCodeRecordMapper.list(userId);
        if (qrCodeRecordList.size() == 0) {
            return ServerResponse.isError(QrCodeEnum.LIST_RECODE_ERROR.getMessage());
        }
        List<QrCodeRecodeListVo> recodeVos = qrCodeRecordList.stream()
                .sorted(Comparator.comparing(QrCodeRecord::getQrcodeRecordUpdateTime).reversed())
                .map(recode -> new QrCodeRecodeListVo(recode.getQrcodeRecordId(),
                        recode.getQrcodeRecordCode(),
                        recode.getQrcodeRecordName(),
                        recode.getQrcodeRecordStatus(),
                        DateTimeUtil.dateToStr(recode.getQrcodeRecordUpdateTime())))
                .collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo(qrCodeRecordList);
        pageInfo.setList(recodeVos);
        return ServerResponse.isSuccess(pageInfo);
    }

    @Override
    public ServerResponse<QrCodeRecordDetailVo> getRecodeDetail(Integer userId, Integer recodeId) {
        if (userId == null || recodeId == null) {
            return ServerResponse.isError(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        log.info("【获取发布记录详情】 userId = {}, recodeId = {}", userId, recodeId);
        QrCodeRecordKey qrCodeRecordKey = new QrCodeRecordKey(recodeId, userId);
        QrCodeRecord record = qrCodeRecordMapper.selectByPrimaryKey(qrCodeRecordKey);
        if (record == null) {
            return ServerResponse.isError(QrCodeEnum.GET_DETAIL_ERROR.getMessage());
        }
        //获取所有表格 id、表名、插入时间、状态
        List<RecordTableDetailDTO> tableDetailDTOS = qrCodeTableMapper.selectByCodeId(recodeId);
        if (tableDetailDTOS == null) {
            return ServerResponse.isError(QrCodeEnum.GET_DETAIL_ERROR.getMessage());
        }
        List<RecordTableVo> tableVos = tableDetailDTOS.stream()
                .sorted(Comparator.comparing(RecordTableDetailDTO::getUpdateTime).reversed())
                .map(dto ->{
                    RecordTableVo vo = new RecordTableVo();
                    vo.setTableId(dto.getTableId());
                    vo.setTableName(dto.getTableName());
                    vo.setUpdateTime(DateTimeUtil.dateToStr(dto.getUpdateTime()));
                    if (dto.getTableStatus().equals(QrCodeConst.STATUS_ON)) {
                        vo.setTableStatus(QrCodeConst.TABLE_STATUS_ON);
                    } else {
                        vo.setTableStatus(QrCodeConst.TABLE_STATUS_OFF);
                    }
                    return vo;
                })
                .collect(Collectors.toList());
        QrCodeRecordDetailVo recordDetailVo = new QrCodeRecordDetailVo(record.getQrcodeRecordId(),
                record.getUserUserId(),
                record.getQrcodeRecordCode(),
                record.getQrcodeRecordName(),
                record.getQrcodeRecordDescription(),
                record.getQrcodeRecordStatus(),
                DateTimeUtil.dateToStr(record.getQrcodeRecordUpdateTime()),
                tableVos);

        return ServerResponse.isSuccess(recordDetailVo);
    }
}
