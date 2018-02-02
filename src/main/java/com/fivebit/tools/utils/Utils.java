package com.fivebit.tools.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fivebit on 2017/11/27.
 */
public class Utils {
    /**
     * 检测token的格式，要符合xxxxxxxx-xxxx-xxxx-xxxxxx-xxxxxxxxxx
     * @param token
     * @return true/false
     */
    public static Boolean checkTokenFormat(String token){
        Boolean status = true;
        if(token == null || token.isEmpty() == true || token.length() !=36 ){
            status = false;
        }else {
            status = token.matches("[a-zA-Z0-9]{8}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{12}");
        }
        return status;
    }

    public static String encodePassword(String password){
        //return  Jencode.MD5(password);
        //return  new BCryptPasswordEncoder().encode(password);

        return null;
    }
    public static String getNewToken(){
        return UUID.randomUUID().toString();
    }

    /**
     * 检测password是否符合要求，eg. length,
     * @param password
     * @return
     */
    public static Boolean checkPasswordFormat(String password){
        return true;
    }

    public static String getRespons(int status,String code,Object data){
        JSONObject oper = new JSONObject();
        oper.put("status",status);
        oper.put("code",code);
        oper.put("data",data);
        return oper.toJSONString();
    }
    public static String getRespons(String code,Object data){
        return Utils.getRespons(200,code,data);

    }
    public static String getRespons(Object data){
        return Utils.getRespons("0",data);

    }
    public static String getRespons(){
        return Utils.getRespons("0","");

    }
    public static Boolean checkString(String st){
        if( null == st || st.isEmpty() == true ){
            return false;
        }
        return true;
    }
    public static String replaceString(String src,String rep,int start,int length){
        if(src.length()<start+length){
            return src;
        }
        char[] src_char = src.toCharArray();
        char[] rep_char = rep.toCharArray();
        int ret_char_len = src.length()-length+rep.length();    //结果长度
        char[] ret_char = new char[ret_char_len];               //结果数组
        int i = 0;
        for(i=0;i<start;i++){
            ret_char[i] = src_char[i];
        }
        for(i=0;i<rep.length();i++){
            ret_char[i+start] = rep_char[i];
        }
        for(i=0;i<src.length()-length-start;i++){
            ret_char[i+start+rep.length()] = src_char[i+start+length];
        }
        return String.valueOf(ret_char);
    }

    /**
     * 合并两个map
     * @param src
     * @param dest
     * @return
     */
    public static Map<String,String> mergMap(Map<String,String> src, List<Map<String,String>> dest){
        if(src == null ){
            src = new HashMap<String,String>();
        }
        if(dest == null){
            return src;
        }
        for(Map<String,String> item:dest){
            for(Map.Entry<String,String> _set:item.entrySet()){
                src.put(_set.getKey(),_set.getValue());
            }
        }
        return src;

    }

    /***
     * 合并两个map.
     * @param src
     * @param dest
     * @return
     */
    public static Map<String,String> mergMap(Map<String,String> src,Map<String,String> dest){
        if(src == null ){
            src = new HashMap<String,String>();
        }
        if(dest == null){
            return src;
        }
        for(Map.Entry<String,String> _set:dest.entrySet()){
            src.put(_set.getKey(),_set.getValue());
        }
        return src;

    }

    /**
     * 从map转成对应的json object
     * @param srcmap
     * @return
     */
    public static JSONObject map2Json(Map<String,String> srcmap){
        JSONObject json_map = new JSONObject();
        if(srcmap != null) {
            for (Map.Entry<String, String> entry : srcmap.entrySet()) {
                json_map.put(entry.getKey(), entry.getValue());
            }
        }
        return json_map;
    }

    public static Map<String,String> json2map(JSONObject srcjson){
        Map<String,String> map_json = Maps.newHashMap();
        if(srcjson != null){
            for(Map.Entry<String, Object> entry: srcjson.entrySet()) {
                map_json.put(entry.getKey(), entry.getValue().toString());
            }
        }
        return map_json;
    }

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static Boolean isPhone(String item){
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(item);
        return m.matches();
    }
    public static Boolean isEmail(String item){

        String regExp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(regExp);
        Matcher matcher = regex.matcher(item);
        return matcher.matches();
    }
}
