package com.fivebit.tools.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by fivebit on 2017/11/27.
 */
public class JEncode {
    public static String skey = "topzedu.com!0.0!";
    private static Logger logger = LoggerFactory.getLogger(JEncode.class);
    public static String sEncode(String str) {
        String newSkey = new StringBuffer(JEncode.skey).reverse().toString();
        String base64Str = JEncode.getBase64(str);
        int count = base64Str.length();
        StringBuffer strbuf = new StringBuffer(base64Str);
        for (int i = 0; i < newSkey.length(); i++) {
            if(i < count){
                strbuf.replace(i*2, i*2+1, ""+base64Str.charAt(i)+newSkey.charAt(i));
            }
        }
        return strbuf.toString().replace("=", "O0O0O");
    }

    public static String sDecode(String str) {
        String newSkey = new StringBuffer(JEncode.skey).reverse().toString();
        String base64Str = str.replace("O0O0O", "=");
        int count = base64Str.length();
        StringBuffer strbuf = new StringBuffer(base64Str);
        for (int i = 0; i < newSkey.length(); i++) {
            if(i < count/2 && (i*2+2-i) < count){
                strbuf.delete(i*2+1-i, i*2+2-i);
                //if(i >= 1) break;
            }
        }
        return JEncode.getFromBase64(strbuf.toString());
    }

    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    public static String getFromBase64(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public final static String base64MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes("utf-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            byte[] md = mdInst.digest(btInput);
            BASE64Encoder base64en = new BASE64Encoder();
            return base64en.encode(md);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toUpperCase();
        } catch (Exception e) {
            logger.error(" md5 error:"+e.getMessage());
            return null;
        }
    }
    public static String SHA1(String inStr) {
        MessageDigest md = null;
        String outStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");     //选择SHA-1，也可以选择MD5
            byte[] digest = md.digest(inStr.getBytes());       //返回的是byet[]，要转化为String存储比较方便
            outStr = bytetoString(digest);
        }
        catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        return outStr;
    }


    public static String bytetoString(byte[] digest) {
        String str = "";
        String tempStr = "";
        for (int i = 0; i < digest.length; i++) {
            tempStr = (Integer.toHexString(digest[i] & 0xff));
            if (tempStr.length() == 1) {
                str = str + "0" + tempStr;
            } else {
                str = str + tempStr;
            }
        }
        return str.toLowerCase();
    }
}
