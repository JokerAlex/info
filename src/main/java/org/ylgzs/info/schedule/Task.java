package org.ylgzs.info.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.ylgzs.info.dao.TableInfoMapper;
import org.ylgzs.info.pojo.TableInfo;
import org.ylgzs.info.pojo.TableInfoKey;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName Task
 * @Description 定时任务
 * @Author alex
 * @Date 2018/11/10
 **/
@Component
@Slf4j
public class Task {
    private final TableInfoMapper tableInfoMapper;
    private final MongoTemplate mongoTemplate;

    public Task(TableInfoMapper tableInfoMapper, MongoTemplate mongoTemplate) {
        this.tableInfoMapper = tableInfoMapper;
        this.mongoTemplate = mongoTemplate;
    }


    /**
     * 删除表格
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void deleteTableInfo() {
        try {
            List<TableInfo> tableInfoList = tableInfoMapper.listDeleteItems();
            tableInfoList.forEach(tableInfo -> {
                mongoTemplate.dropCollection(tableInfo.getCollectionName());
            });
            List<Integer> tableInfoIds = tableInfoList.stream()
                    .map(TableInfoKey::getTableInfoId)
                    .collect(Collectors.toList());

            int count = tableInfoMapper.deleteByTableInfoIdBatch(tableInfoIds);
            log.info("[deleteTableInfo] count : {}, tableInfoIds : {}", count, tableInfoIds.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
