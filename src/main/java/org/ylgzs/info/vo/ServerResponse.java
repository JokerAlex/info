package org.ylgzs.info.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;
import org.ylgzs.info.enums.ResultEnum;

import java.io.Serializable;

/**
 * @ClassName ServerResponse
 * @Description 请求返回结果
 * @Author alex
 * @Date 2018/10/1
 **/
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status){
        this.status = status;
    }
    private ServerResponse(int status,T data){
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status,String msg,T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponse(int status,String msg){
        this.status = status;
        this.msg = msg;
    }

    /**
     * 判断请求是否成功
     * 注解使之不在json序列化结果当中
     * @return
     */
    @JsonIgnore
    public boolean isOk(){
        return this.status == ResultEnum.SUCCESS.getCode();
    }

    public static <T> ServerResponse<T> isSuccess(){
        return new ServerResponse<T>(ResultEnum.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> isSuccess(String msg){
        return new ServerResponse<T>(ResultEnum.SUCCESS.getCode(),msg);
    }

    public static <T> ServerResponse<T> isSuccess(T data){
        return new ServerResponse<T>(ResultEnum.SUCCESS.getCode(),data);
    }

    public static <T> ServerResponse<T> isSuccess(String msg,T data){
        return new ServerResponse<T>(ResultEnum.SUCCESS.getCode(),msg,data);
    }


    public static <T> ServerResponse<T> isError(){
        return new ServerResponse<T>(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMessage());
    }


    public static <T> ServerResponse<T> isError(String errorMessage){
        return new ServerResponse<T>(ResultEnum.ERROR.getCode(),errorMessage);
    }

    public static <T> ServerResponse<T> isError(int errorCode,String errorMessage){
        return new ServerResponse<T>(errorCode,errorMessage);
    }

}
