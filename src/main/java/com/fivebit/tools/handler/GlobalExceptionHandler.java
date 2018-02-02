package com.fivebit.tools.handler;

import com.alibaba.fastjson.JSONObject;
import com.fivebit.tools.components.Slog;
import com.fivebit.tools.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fivebit on 2017/11/27.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private Slog log;
    @ExceptionHandler(value = AppException.class)
    @ResponseBody
    public JSONObject jsonErrorHandler(HttpServletRequest req, AppException e) throws Exception {
        JSONObject ret = new JSONObject();
        ret.put("status", e.getStatus());
        ret.put("code", e.getCode());
        ret.put("data", e.getData());
        return ret;
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public JSONObject notValidExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
        log.error("notValidExceptionHandler:"+e.getMessage());
        JSONObject ret = new JSONObject();
        ret.put("status", Constants.APP_ERROR_STATUS);
        ret.put("code", Constants.APP_ERROR_CODE);
        ret.put("data", "invalid field:"+e.getBindingResult().getFieldError().getField()+" "+e.getBindingResult().getFieldError().getDefaultMessage());
        return ret;
    }
}
