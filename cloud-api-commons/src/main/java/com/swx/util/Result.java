package com.swx.util;

/**
 * @Description:
 * @Author: sunweixin
 * @Date: 2021/12/30
 */
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public Result()
    {
    }

    public Result(Integer code, String message, T data)
    {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public Result( Integer code,String message) {
        this( code, message,null);
    }

    public Result(T data) {
        this(200, "操作成功", data);
    }

    //setter--getter
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
