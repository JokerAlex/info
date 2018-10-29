package org.ylgzs.info.service;

import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;
import org.ylgzs.info.pojo.TableInfo;
import org.ylgzs.info.pojo.TableInfoKey;
import org.ylgzs.info.vo.ServerResponse;
import org.ylgzs.info.vo.TableInfoDetailVo;

import java.util.List;

/**
 * @ClassName ITableService
 * @Description 文件列表
 * @Author alex
 * @Date 2018/10/6
 **/
public interface ITableService {

    /**
     * 检查表名是否可用
     * @param userId
     * @param tableName
     * @return
     */
    ServerResponse<String> checkTableName(Integer userId, String tableName);

    /**
     * 导入 excel 文件
     * @param userId
     * @param tableName
     * @param multipartFile
     * @return
     */
    ServerResponse importInfo(Integer userId, String tableName, MultipartFile multipartFile);

    /**
     * 更新表格基本信息
     * @param tableInfo
     * @return
     */
    ServerResponse<String> updateTableInfo(Integer userId, TableInfo tableInfo);

    /**
     * 删除表格
     * @param userId
     * @param tableInfoId
     * @return
     */
    ServerResponse<String> delTableInfo(Integer userId, Integer tableInfoId);

    /**
     * 批量删除
     * @param tableInfoIds
     * @return
     */
    ServerResponse<String> delTableInfoBatch(List<Integer> tableInfoIds);

    /**
     * 获取列表
     * @param pageNum
     * @param pageSize
     * @param userId
     * @param gradeId
     * @param departmentId
     * @return
     */
    ServerResponse<PageInfo> listTableInfo(Integer pageNum, Integer pageSize, Integer userId, String gradeId, Integer departmentId);

    /**
     * 获取表格详情
     * @param tableInfoKey
     * @return
     */
    ServerResponse<TableInfoDetailVo> getTableInfoDetail(TableInfoKey tableInfoKey);

    /**
     * 信息查询
     * @param collectionName
     * @param field1
     * @param value1
     * @param field2
     * @param value2
     * @return
     */
    ServerResponse find(String collectionName, String field1, String value1, String field2, String value2);

}
