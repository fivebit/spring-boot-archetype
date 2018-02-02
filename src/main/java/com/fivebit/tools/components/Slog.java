package com.fivebit.tools.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by fivebit on 2017/11/27.
 */
@Component
public class Slog {
    public Logger logger;
    public int linenum = 0;
    public String classname = "";
    public String requestid = "";
    private static final ThreadLocal<String> requestIdHolder = new ThreadLocal<String>();
    Slog(){
    }

    public Logger _get(){
        StackTraceElement[] stack = (new Throwable()).getStackTrace();
        int i = 3;	//this is the caller function,is groovy is 11
        classname = stack[i].getClassName();
        linenum = stack[i].getLineNumber();
        logger = LoggerFactory.getLogger(this.classname);
        return logger;
    }
    public void _print(int type,Object msg){
        Logger logger = _get();
        boolean is_string = msg instanceof String;
        if( is_string == false){
            msg = msg.toString();
        }
        requestid = requestIdHolder.get();
        if(requestid == null) {
            try {
                //requestid = null == request.getHeader("X-Request-ID") ?
                //		UUID.randomUUID().toString().replace("-", "").substring(0, 15)
                //		: request.getHeader("X-Request-ID");
                requestid = UUID.randomUUID().toString().replace("-", "").substring(0, 15);
            } catch (Exception ee) {
                requestid = UUID.randomUUID().toString().replace("-", "").substring(0, 15);
            }
            requestIdHolder.set(requestid);
        }
        msg = linenum+" "+requestid+" "+ msg.toString();
        switch(type){
            case 0:
                logger.debug((String) msg);
                break;
            case 1:
                logger.info(msg.toString());
                break;
            case 2:
                logger.error(msg.toString());
                break;
            case 3:
                logger.warn(msg.toString());
                break;
        }
    }

    public void debug(Object msg){
        _print(0,msg);
    }
    public void info(Object msg){
        _print(1,msg);
    }
    public void error(Object msg){
        _print(2,msg);
    }
    public void fatal(Object msg){
        _print(3,msg);
    }
}
