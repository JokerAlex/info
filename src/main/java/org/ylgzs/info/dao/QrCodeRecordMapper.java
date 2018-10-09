package org.ylgzs.info.dao;

import org.springframework.stereotype.Repository;
import org.ylgzs.info.pojo.QrCodeRecord;
import org.ylgzs.info.pojo.QrCodeRecordKey;

import java.util.List;

@Repository
public interface QrCodeRecordMapper {
    int deleteByPrimaryKey(QrCodeRecordKey key);

    int insert(QrCodeRecord record);

    int insertSelective(QrCodeRecord record);

    QrCodeRecord selectByPrimaryKey(QrCodeRecordKey key);

    int updateByPrimaryKeySelective(QrCodeRecord record);

    int updateByPrimaryKey(QrCodeRecord record);

    List<QrCodeRecord> list(Integer userId);

    QrCodeRecord selectByRecordCode(String recordCode);

    int deleteByQrCodeRecoedIdBatch(List<Integer> list);
}