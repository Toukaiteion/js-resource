package com.hseea.jsresource.constant.enmu;

public enum ResultCode {
    SUCCESS(1,"success"),FAIL(1,"fail");

    private int code;
    private String remark;

    ResultCode(int code, String remark) {
        this.code = code;
        this.remark = remark;
    }
    public int code(){
        return this.code;
    }
    public String remark(){
        return this.remark;
    }
}
