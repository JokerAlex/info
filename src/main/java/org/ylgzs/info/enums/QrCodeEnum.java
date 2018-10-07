package org.ylgzs.info.enums;

import lombok.Getter;

/**
 * @ClassName QrCodeEnum
 * @Description Qr enum
 * @Author alex
 * @Date 2018/10/7
 **/
@Getter
public enum QrCodeEnum {

    /**
     * 发布记录
     */
    RECODE_SAVE_ERROR("新增发布记录失败"),

    /**\
     * 更新
     */
    UPDATE_RECODE_ERROR("更新发布记录失败"),

    /**
     * 删除
     */
    DEL_RECODE_ERROR("删除发布记录失败"),

    /**
     * 列表
     */
    LIST_RECODE_ERROR("获取发布记录列表失败"),

    /**
     * 详情
     */
    GET_DETAIL_ERROR("获取发布记录详情失败"),

    /**
     * status
     */
    UPDATE_STATUS_ERROR("修改查询状态失败"),

    ;


    private String message;

    QrCodeEnum(String message) {
        this.message = message;
    }
}
