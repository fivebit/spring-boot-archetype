package com.fivebit.tools.handler;

import com.fivebit.tools.utils.Constants;

/**
 * Created by fivebit on 2017/11/27.
 */
public class AppException extends  Exception{
    private int status = Constants.APP_ERROR_STATUS;

    /** application specific error code */
    private String code;

    private Object data;

    /**
     *
     * @param status
     * @param code
     * @param  data
     */
    public AppException(Integer status, String code, Object data){
        super((String) data);
        this.code = code;
        this.data = data;
    }

    public AppException(String code, Object data) {
        super((String) data);
        this.code = code;
        this.data = data;
    }

    public AppException(String data) {
        super((String) data);
        this.data = data;
        this.code = "-1";
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
