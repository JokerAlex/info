package org.ylgzs.info.dao;

import org.ylgzs.info.pojo.QrCodeTable;
import org.ylgzs.info.pojo.QrCodeTableKey;

public interface QrCodeTableMapper {
    int deleteByPrimaryKey(QrCodeTableKey key);

    int insert(QrCodeTable record);

    int insertSelective(QrCodeTable record);

    QrCodeTable selectByPrimaryKey(QrCodeTableKey key);

    int updateByPrimaryKeySelective(QrCodeTable record);

    int updateByPrimaryKey(QrCodeTable record);
}