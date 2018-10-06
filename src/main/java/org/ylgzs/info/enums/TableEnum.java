package org.ylgzs.info.enums;

import lombok.Getter;

/**
 * @ClassName TableEnum
 * @Description table
 * @Author alex
 * @Date 2018/10/6
 **/
@Getter
public enum TableEnum {

    /**
     * 文件类型错误
     */
    ILLEGAL_FILE_TYPE("非法文件类型"),

    /**
     * 文件读取错误
     */
    READ_FILE_ERROR("读取文件失败"),

    /**
     * 保存文件内容
     */
    SAVE_FILE_ERROR("文件内容插入数据库失败"),

    /**
     *检验表名
     */
    INVALID_TABLE_NAME("该表名已存在"),

    /**
     * 更新表信息
     */
    UPDATE_TABLE_INFO_ERROR("更新表信息失败"),

    /**
     * 删除
     */
    DEL_TABLE_INFO_ERROR("删除表信息失败"),

    /**
     * 获取列表
     */
    LIST_ERROR("获取列表失败"),

    /**
     * 获取表格详细信息
     */
    DETAIL_ERROR("获取表格详细信息失败"),

    NOT_FOUND("未找到结果"),

    ;

    private String message;

    TableEnum(String message) {
        this.message = message;
    }
}
