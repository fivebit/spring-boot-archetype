package com.fivebit.tools.components;

import com.fivebit.tools.config.DatabaseContextHolder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import com.fivebit.tools.utils.*;

/**
 * Created by fivebit on 2017/11/27.
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    //@Value("${signature.token}")
    private String token;
    //@Value("${signature.nonce}")
    private String nonce;
    @Autowired
    private Slog log;
    //private final Logger log = Logger.getLogger(this.getClass());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("AUTHENTICATION BEGIN: ");
        if(request.getMethod().equals("POST") || request.getMethod().equals("PUT")){     //对post 和put方法拦截
        }
        String token = request.getHeader("token");
        if(token == null){

            log.info("REQUEST TOKEN IS NULL" );
            return true;
        }
        log.info("REQUEST TOKEN IS: " + token);

        log.info("==============:"+token);
        if(token.equals("ds1")){
            DatabaseContextHolder.setCustomerType("ds1");
        }else if(token.equals("ds2")) {
            DatabaseContextHolder.setCustomerType("ds2");
        }else{
            DatabaseContextHolder.setCustomerType("dataSource");
        }

        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("AUTHENTICATION SPEND TIME : ");
    }
    public Map<String,String> getSignature(){
        String timestamp = String.valueOf(System.currentTimeMillis());
        ArrayList<String> params = Lists.newArrayList();
        params.add(token);
        params.add(timestamp);
        params.add(nonce);
        Collections.sort(params);
        String signature = JEncode.SHA1(params.get(0)+params.get(1)+params.get(2)).replaceAll("-","");
        Map<String,String> ret = Maps.newHashMap();
        ret.put("signature",signature);
        ret.put("timestamp",timestamp);
        ret.put("nonce",nonce);
        return ret;
    }
    public Boolean checkSignature(String signature,String timestamp,String nonce){
        Long now_timestamp = System.currentTimeMillis();
        Long t_timestamp = Long.valueOf(timestamp);
        Boolean st = false;
        if(now_timestamp - t_timestamp <= 10000){
            ArrayList<String> params = Lists.newArrayList();
            params.add(token);
            params.add(timestamp);
            params.add(nonce);
            Collections.sort(params);
            String h_signature = JEncode.SHA1(params.get(0)+params.get(1)+params.get(2)).replaceAll("-","");
            if(signature.equals(h_signature)){
                st = true;
            }
        }
        return st;
    }
}
