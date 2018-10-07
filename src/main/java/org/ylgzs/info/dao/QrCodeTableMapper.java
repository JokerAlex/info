package org.ylgzs.info.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.ylgzs.info.dto.RecordTableDetailDTO;
import org.ylgzs.info.pojo.QrCodeTable;
import org.ylgzs.info.pojo.QrCodeTableKey;

import java.util.List;

@Repository
public interface QrCodeTableMapper {
    int deleteByPrimaryKey(QrCodeTableKey key);

    int insert(QrCodeTable record);

    int insertSelective(QrCodeTable record);

    QrCodeTable selectByPrimaryKey(QrCodeTableKey key);

    int updateByPrimaryKeySelective(QrCodeTable record);

    int updateByPrimaryKey(QrCodeTable record);

    int insertSelectiveBatch(@Param("qrCodeId")Integer qrCodeId, @Param("list")List<Integer> tableIds);

    int updateStatus(@Param("tableId")Integer tableId, @Param("status")Integer status);

    int deleteByQrCodeId(Integer qrcodeId);

    List<RecordTableDetailDTO> selectByCodeId(Integer codeId);
}