package org.ylgzs.info.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.ylgzs.info.constant.QrCodeConst;
import org.ylgzs.info.dao.QrCodeTableMapper;
import org.ylgzs.info.dao.TableInfoMapper;
import org.ylgzs.info.dao.UserInfoMapper;
import org.ylgzs.info.enums.ResultEnum;
import org.ylgzs.info.enums.TableEnum;
import org.ylgzs.info.exception.ParameterErrorException;
import org.ylgzs.info.exception.TableException;
import org.ylgzs.info.pojo.TableInfo;
import org.ylgzs.info.pojo.TableInfoKey;
import org.ylgzs.info.pojo.UserInfo;
import org.ylgzs.info.service.ITableService;
import org.ylgzs.info.util.DateTimeUtil;
import org.ylgzs.info.util.ExcelUtil;
import org.ylgzs.info.vo.ServerResponse;
import org.ylgzs.info.vo.TableInfoDetailVo;
import org.ylgzs.info.vo.TableInfoListVo;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @ClassName TableServiceImpl
 * @Description 文件列表
 * @Author alex
 * @Date 2018/10/6
 **/
@Slf4j
@Service("iTableService")
public class TableServiceImpl implements ITableService {

    private final MongoTemplate mongoTemplate;
    private final TableInfoMapper tableInfoMapper;
    private final UserInfoMapper userInfoMapper;
    private final QrCodeTableMapper qrCodeTableMapper;

    @Autowired
    public TableServiceImpl(MongoTemplate mongoTemplate, TableInfoMapper tableInfoMapper, UserInfoMapper userInfoMapper, QrCodeTableMapper qrCodeTableMapper) {
        this.mongoTemplate = mongoTemplate;
        this.tableInfoMapper = tableInfoMapper;
        this.userInfoMapper = userInfoMapper;
        this.qrCodeTableMapper = qrCodeTableMapper;
    }

    @Override
    public ServerResponse<String> checkTableName(Integer userId, String tableName) {
        if (userId == null || tableName == null) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        int count = tableInfoMapper.checkTableName(userId, tableName);
        if (count > 0) {
            return ServerResponse.isError(TableEnum.INVALID_TABLE_NAME.getMessage());
        }
        return ServerResponse.isSuccess();
    }


    @Override
    @Transactional(rollbackFor = TableException.class)
    public ServerResponse importInfo(Integer userId, String tableName, MultipartFile multipartFile) throws TableException{
        if (multipartFile == null || userId == null || tableName == null) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        //检查 tableName 是否可用
        ServerResponse<String> checkTable = this.checkTableName(userId, tableName);
        if (!checkTable.isOk()) {
            return checkTable;
        }

        String fileName = multipartFile.getOriginalFilename();
        log.info("fileName = {}", fileName);
        //判断文件类型
        //读取文件内容并存储
        //tableInfo 插入
        if (!fileName.substring(fileName.lastIndexOf(ExcelUtil.POINT)).equals(ExcelUtil.EXCEL_2003L)
                && !fileName.substring(fileName.lastIndexOf(ExcelUtil.POINT)).equals(ExcelUtil.EXCEL_2007U)) {
            return ServerResponse.isError(TableEnum.ILLEGAL_FILE_TYPE.getMessage());
        }
        try {
            //读取文件内容并存储
            List<Document> documentList = ExcelUtil.readByDocument(multipartFile.getInputStream());
            String collectionName = userId + "_" + UUID.randomUUID().toString().replaceAll("-", "_");
            mongoTemplate.createCollection(collectionName);
            MongoCollection<Document> mongoCollection = mongoTemplate.getCollection(collectionName);
            mongoCollection.insertMany(documentList);

            //tableInfo 插入
            TableInfo tableInfo = new TableInfo();
            tableInfo.setUserUserId(userId);
            tableInfo.setTableInfoName(tableName);
            tableInfo.setCollectionName(collectionName);
            log.info("【插入 tableInfo 】tableInfo = {}", tableInfo.toString());
            int count = tableInfoMapper.insertSelective(tableInfo);
            log.info("【插入 tableInfo 】count = {}", count);
            if (count > 0) {
                TableInfoDetailVo vo = new TableInfoDetailVo();
                BeanUtils.copyProperties(tableInfo, vo);
                return ServerResponse.isSuccess(vo);
            }
        } catch (IOException ioe) {
            log.error("文件读取错误 = {}", ioe.getMessage());
            throw new TableException(TableEnum.READ_FILE_ERROR.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new TableException(TableEnum.SAVE_FILE_ERROR.getMessage());
        }
        throw new TableException(TableEnum.UPDATE_TABLE_INFO_ERROR.getMessage());
    }

    @Override
    @Transactional(rollbackFor = TableException.class)
    public ServerResponse<String> updateTableInfo(Integer userId, TableInfo tableInfo) {
        if (userId == null || tableInfo == null || !userId.equals(tableInfo.getUserUserId()) || tableInfo.getTableInfoId() == null) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        //可更新字段->表名、描述、查询列
        TableInfo update = new TableInfo();
        update.setTableInfoId(tableInfo.getTableInfoId());
        update.setUserUserId(userId);
        update.setTableInfoDescription(tableInfo.getTableInfoDescription());
        update.setTableInfoQueryCol(tableInfo.getTableInfoQueryCol());
        log.info("【更新 tableInfo】tableInfo = {}", update.toString());

        int count = tableInfoMapper.updateByPrimaryKeySelective(update);
        log.info("【更新 tableInfo】count = {}", count);
        if (count > 0) {
            return ServerResponse.isSuccess();
        }
        throw new TableException(TableEnum.UPDATE_TABLE_INFO_ERROR.getMessage());
    }

    @Override
    @Transactional(rollbackFor = TableException.class)
    public ServerResponse<String> delTableInfo(Integer userId, Integer tableInfoId) {
        if (tableInfoId == null || userId == null) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        //删除mongoDB里的表
        log.info("【删除表格】userId = {}, tableInfoId = {}", userId, tableInfoId);
        TableInfoKey tableInfoKey = new TableInfoKey(tableInfoId, userId);
        TableInfo tableInfo = tableInfoMapper.selectByPrimaryKey(tableInfoKey);
        mongoTemplate.dropCollection(tableInfo.getCollectionName());
        //删除表信息
        int count = tableInfoMapper.deleteByPrimaryKey(tableInfoKey);
        //TODO 修改删除方式
        int codeCount = qrCodeTableMapper.updateStatus(tableInfoId, QrCodeConst.STATUS_OFF);
        log.info("【删除表格】count = {}, codeCount = {}", count, codeCount);
        if (count > 0) {
            return ServerResponse.isSuccess();
        }
        throw new TableException(TableEnum.DEL_TABLE_INFO_ERROR.getMessage());

    }

    @Override
    @Transactional(rollbackFor = TableException.class)
    public ServerResponse<String> delTableInfoBatch(List<Integer> tableInfoIds) {
        if (tableInfoIds.isEmpty()) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        log.info("【删除表格】tableInfoIds = {}", tableInfoIds.toString());
        List<String> collectionNames = tableInfoMapper.listByTableInfoIds(tableInfoIds).stream()
                .map(TableInfo::getCollectionName)
                .collect(Collectors.toList());
        //删除mongoDB里的表
        //TODO 删除方式优化
        collectionNames.forEach(mongoTemplate::dropCollection);
        int tableCount = tableInfoMapper.deleteByTableInfoIdBatch(tableInfoIds);
        int codeCount = qrCodeTableMapper.updateStatusBatch(tableInfoIds, QrCodeConst.STATUS_OFF);
        log.info("【删除表格】tableCount = {}, codeCount = {}", tableCount, codeCount);
        if (tableCount > 0 && codeCount > 0) {
            return ServerResponse.isSuccess();
        }
        throw new TableException(TableEnum.DEL_TABLE_INFO_ERROR.getMessage());
    }

    @Override
    public ServerResponse<PageInfo> listTableInfo(Integer pageNum, Integer pageSize, Integer userId, String gradeId, Integer departmentId) {
        if (pageNum == null || pageSize == null) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        //获取表格信息
        PageHelper.startPage(pageNum, pageSize);
        List<TableInfo> tableInfoList = tableInfoMapper.list(userId, gradeId, departmentId);
        if (CollectionUtils.isEmpty(tableInfoList)) {
            return ServerResponse.isError(TableEnum.LIST_ERROR.getMessage());
        }
        List<TableInfoListVo> voList = tableInfoList.stream()
                .sorted(Comparator.comparing(TableInfo::getTableInfoUpdateTime).reversed())
                .map(tableInfo -> new TableInfoListVo(tableInfo.getTableInfoId(),
                        tableInfo.getTableInfoName(),
                        tableInfo.getTableInfoPv()))
                .collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo<>(tableInfoList);
        pageInfo.setList(voList);

        return ServerResponse.isSuccess(pageInfo);
    }

    @Override
    public ServerResponse<TableInfoDetailVo> getTableInfoDetail(TableInfoKey tableInfoKey) {
        if (tableInfoKey == null) {
            throw new ParameterErrorException(ResultEnum.ILLEGAL_PARAMETER.getMessage());
        }
        TableInfo tableInfo = tableInfoMapper.selectByPrimaryKey(tableInfoKey);
        if (tableInfo == null) {
            throw new TableException(TableEnum.NOT_FOUND.getMessage());
        }
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(tableInfoKey.getUserUserId());
        TableInfoDetailVo tableInfoDetailVo = new TableInfoDetailVo(tableInfo.getTableInfoId(),
                tableInfo.getTableInfoName(),
                userInfo.getUserName(),
                tableInfo.getTableInfoDescription(),
                tableInfo.getTableInfoQueryCol(),
                tableInfo.getTableInfoPv(),
                DateTimeUtil.dateToStr(tableInfo.getTableInfoCreateTime()),
                DateTimeUtil.dateToStr(tableInfo.getTableInfoUpdateTime()));
        return ServerResponse.isSuccess(tableInfoDetailVo);
    }
}
