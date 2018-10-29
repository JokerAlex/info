package org.ylgzs.info.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.ylgzs.info.constant.QrCodeConst;
import org.ylgzs.info.dao.QrCodeRecordMapper;
import org.ylgzs.info.dao.QrCodeTableMapper;
import org.ylgzs.info.dto.RecordTableDetailDTO;
import org.ylgzs.info.enums.QrCodeEnum;
import org.ylgzs.info.enums.ResultEnum;
import org.ylgzs.info.exception.NotFoundException;
import org.ylgzs.info.exception.ParameterErrorException;
import org.ylgzs.info.exception.QrCodeException;
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
    public ServerResponse<String> checkQrName(Integer userId, String qrName) {
        if (userId == null || qrName == null) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        int count = qrCodeRecordMapper.checkQrName(userId, qrName);
        if (count > 0) {
            return ServerResponse.isError(QrCodeEnum.INVALID_TABLE_NAME.getMessage());
        }
        return ServerResponse.isSuccess();
    }

    @Override
    @Transactional(rollbackFor = QrCodeException.class)
    public ServerResponse<String> addQrCode(Integer userId,String qrName,String description, List<Integer> tableIds) throws ParameterErrorException, QrCodeException {
        if (userId == null || tableIds == null || tableIds.size() == 0 || qrName == null) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        ServerResponse<String> check = this.checkQrName(userId, qrName);
        if (!check.isOk()) {
            return check;
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
        //插入发布记录与表的关联信息
        int qrTableCount = qrCodeTableMapper.insertSelectiveBatch(qrCodeRecord.getQrcodeRecordId(), tableIds);
        log.info("【插入QrCodeTable】qrTableCount = {}", qrTableCount);
        if (qrCount <= 0 || qrTableCount != tableIds.size()) {
            throw new QrCodeException(QrCodeEnum.RECODE_SAVE_ERROR.getMessage());
        }
        return ServerResponse.isSuccess();
    }

    @Override
    @Transactional(rollbackFor = QrCodeException.class)
    public ServerResponse<String> updateRecord(Integer userId, QrCodeRecord qrCodeRecord) throws ParameterErrorException, QrCodeException {
        if (userId == null || qrCodeRecord == null || qrCodeRecord.getQrcodeRecordId() == null || !userId.equals(qrCodeRecord.getUserUserId())) {
            return ServerResponse.isError(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        //可更新字段->名称、描述
        QrCodeRecord update = new QrCodeRecord();
        update.setQrcodeRecordId(qrCodeRecord.getQrcodeRecordId());
        update.setUserUserId(qrCodeRecord.getUserUserId());
        update.setQrcodeRecordName(qrCodeRecord.getQrcodeRecordName());
        update.setQrcodeRecordDescription(qrCodeRecord.getQrcodeRecordDescription());
        int count = qrCodeRecordMapper.updateByPrimaryKeySelective(update);
        log.info("【更新 recode】count = {}", count);
        if (count > 0) {
            return ServerResponse.isSuccess();
        }
        throw new QrCodeException(QrCodeEnum.UPDATE_RECODE_ERROR.getMessage());
    }

    @Override
    @Transactional(rollbackFor = QrCodeException.class)
    public ServerResponse<String> updateStatus(Integer userId, Integer recodeId, Integer status) throws ParameterErrorException, QrCodeException {
        if (userId == null || recodeId == null || status == null) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        if (!QrCodeConst.STATUS_ON.equals(status) && !QrCodeConst.STATUS_OFF.equals(status)) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
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
        throw new QrCodeException(QrCodeEnum.UPDATE_STATUS_ERROR.getMessage());
    }

    @Override
    @Transactional(rollbackFor = QrCodeException.class)
    public ServerResponse<String> delRecord(Integer userId, Integer recordId) throws ParameterErrorException, QrCodeException {
        if (userId == null || recordId == null) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        log.info("【删除记录】 recordId = {}", recordId);
        int tableCount = qrCodeTableMapper.deleteByQrCodeId(recordId);

        QrCodeRecordKey qrCodeRecordKey = new QrCodeRecordKey(recordId, userId);
        int recordCount = qrCodeRecordMapper.deleteByPrimaryKey(qrCodeRecordKey);
        log.info("【删除记录】tableCount = {}, recodeCount = {}", tableCount, recordCount);
        if (tableCount > 0 && recordCount > 0) {
            return ServerResponse.isSuccess();
        }
        throw new QrCodeException(QrCodeEnum.DEL_RECODE_ERROR.getMessage());
    }

    @Override
    @Transactional(rollbackFor = QrCodeException.class)
    public ServerResponse<String> delRecordBatch(List<Integer> recordIds) throws ParameterErrorException, QrCodeException {
        if (recordIds.isEmpty()) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        log.info("【批量删除发布记录】recordIds = {}", recordIds.toString());
        int tableCount = qrCodeTableMapper.deleteByQrCodeIdBatch(recordIds);

        int recordCount = qrCodeRecordMapper.deleteByQrCodeRecoedIdBatch(recordIds);
        log.info("【批量删除发布记录】tableCount = {}, recodeCount = {}", tableCount, recordCount);
        if (tableCount > 0 && recordCount > 0) {
            return ServerResponse.isSuccess();
        }
        throw new QrCodeException(QrCodeEnum.DEL_RECODE_ERROR.getMessage());
    }

    @Override
    public ServerResponse<PageInfo> listRecord(Integer pageNum, Integer pageSize, Integer userId) throws ParameterErrorException {
        if (pageNum == null || pageSize == null || userId == null) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
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
    public ServerResponse<QrCodeRecordDetailVo> getRecordDetail(Integer userId, Integer recodeId) throws ParameterErrorException {
        if (userId == null || recodeId == null) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        log.info("【获取发布记录详情】 userId = {}, recodeId = {}", userId, recodeId);
        QrCodeRecordKey qrCodeRecordKey = new QrCodeRecordKey(recodeId, userId);
        QrCodeRecord record = qrCodeRecordMapper.selectByPrimaryKey(qrCodeRecordKey);
        if (record == null) {
            throw new ParameterErrorException(QrCodeEnum.GET_DETAIL_ERROR.getMessage());
        }
        //获取所有表格 id、表名、插入时间、状态
        List<RecordTableDetailDTO> tableDetailDTOS = qrCodeTableMapper.selectByCodeId(recodeId);
        if (tableDetailDTOS == null) {
            throw new NotFoundException(QrCodeEnum.GET_DETAIL_ERROR.getMessage());
        }
        List<RecordTableVo> tableVos = tableDetailDTOS.stream()
                .sorted(Comparator.comparing(RecordTableDetailDTO::getUpdateTime).reversed())
                .map(dto ->{
                    RecordTableVo vo = new RecordTableVo();
                    vo.setTableId(dto.getTableId());
                    vo.setTableName(dto.getTableName());
                    vo.setCollectionName(dto.getCollectionName());
                    vo.setUpdateTime(DateTimeUtil.dateToStr(dto.getUpdateTime()));
                    vo.setTableStatus(dto.getTableStatus());
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

    @Override
    public ServerResponse<QrCodeRecordDetailVo> getRecordDetail(String recordCode) throws ParameterErrorException {
        if (StringUtils.isEmpty(recordCode)) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        log.info("【发布记录码】 recordCode = {}", recordCode);
        QrCodeRecord record = qrCodeRecordMapper.selectByRecordCode(recordCode);
        if (record == null) {
            return ServerResponse.isError(QrCodeEnum.GET_DETAIL_ERROR.getMessage());
        }
        //发送字段：名称、记录码、描述、状态、列表
        QrCodeRecordDetailVo recordDetailVo = new QrCodeRecordDetailVo();
        recordDetailVo.setQrcodeRecordName(record.getQrcodeRecordName());
        recordDetailVo.setQrcodeRecordCode(record.getQrcodeRecordCode());
        recordDetailVo.setDescription(record.getQrcodeRecordDescription());
        recordDetailVo.setQrcodeRecordStatus(record.getQrcodeRecordStatus());
        if (recordDetailVo.getQrcodeRecordStatus().equals(QrCodeConst.STATUS_ON)) {
            //获取所有表格 id、表名、插入时间、状态
            List<RecordTableDetailDTO> tableDetailDTOS = qrCodeTableMapper.selectByCodeId(record.getQrcodeRecordId());
            if (tableDetailDTOS == null) {
                return ServerResponse.isError(QrCodeEnum.GET_DETAIL_ERROR.getMessage());
            }
            List<RecordTableVo> tableVos = tableDetailDTOS.stream()
                    .sorted(Comparator.comparing(RecordTableDetailDTO::getUpdateTime).reversed())
                    .map(dto ->{
                        RecordTableVo vo = new RecordTableVo();
                        vo.setTableName(dto.getTableName());
                        vo.setCollectionName(dto.getCollectionName());
                        vo.setTableStatus(dto.getTableStatus());
                        return vo;
                    })
                    .collect(Collectors.toList());
            recordDetailVo.setTableVos(tableVos);
        }
        return ServerResponse.isSuccess(recordDetailVo);

    }
}
