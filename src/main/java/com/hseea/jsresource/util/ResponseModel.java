package com.hseea.jsresource.util;

import com.hseea.jsresource.constant.enmu.ResultCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseModel<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public ResponseModel (){
    }

    public ResponseModel<T> success(){
        this.code = ResultCode.SUCCESS.code();
        this.message = ResultCode.SUCCESS.remark();
        return this;
    }

    public ResponseModel<T> success(T data){
        this.code = ResultCode.SUCCESS.code();
        this.message = ResultCode.SUCCESS.remark();
        this.data = data;
        return this;
    }

    public ResponseModel<T> fail(){
        this.code = ResultCode.FAIL.code();
        this.message = ResultCode.FAIL.remark();
        return this;
    }
}
