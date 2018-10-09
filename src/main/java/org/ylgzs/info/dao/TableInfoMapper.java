package org.ylgzs.info.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.ylgzs.info.pojo.TableInfo;
import org.ylgzs.info.pojo.TableInfoKey;

import java.util.List;

@Repository
public interface TableInfoMapper {
    int deleteByPrimaryKey(TableInfoKey key);

    int insert(TableInfo record);

    int insertSelective(TableInfo record);

    TableInfo selectByPrimaryKey(TableInfoKey key);

    int updateByPrimaryKeySelective(TableInfo record);

    int updateByPrimaryKey(TableInfo record);

    int checkTableName(@Param("userId")Integer userId, @Param("tableName")String tableName);

    List<TableInfo> list(@Param("userId") Integer userId, @Param("gradeId")String gradeId, @Param("departmentId") Integer departmentId);

    List<TableInfo> listByTableInfoIds(List<Integer> list);

    int deleteByTableInfoIdBatch(List<Integer> list);
}