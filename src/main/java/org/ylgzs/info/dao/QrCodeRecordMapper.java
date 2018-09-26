package org.ylgzs.info.dao;

import org.ylgzs.info.pojo.QrCodeRecord;
import org.ylgzs.info.pojo.QrCodeRecordKey;

public interface QrCodeRecordMapper {
    int deleteByPrimaryKey(QrCodeRecordKey key);

    int insert(QrCodeRecord record);

    int insertSelective(QrCodeRecord record);

    QrCodeRecord selectByPrimaryKey(QrCodeRecordKey key);

    int updateByPrimaryKeySelective(QrCodeRecord record);

    int updateByPrimaryKey(QrCodeRecord record);
}