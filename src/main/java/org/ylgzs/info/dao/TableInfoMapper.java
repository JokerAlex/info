package org.ylgzs.info.dao;

import org.ylgzs.info.pojo.TableInfo;
import org.ylgzs.info.pojo.TableInfoKey;

public interface TableInfoMapper {
    int deleteByPrimaryKey(TableInfoKey key);

    int insert(TableInfo record);

    int insertSelective(TableInfo record);

    TableInfo selectByPrimaryKey(TableInfoKey key);

    int updateByPrimaryKeySelective(TableInfo record);

    int updateByPrimaryKey(TableInfo record);
}