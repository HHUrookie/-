package com.jwking.community.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.UUID;

public class CommunityUtil {


    // 生成随机字符串
    public static String getRandomString() {
        return UUID.randomUUID().toString().replaceAll("_", "");

    }

    //MD5加密，对密码进行加密
    //只能加密不能解密
    //在密码后面加上一个随机字符串，提高密码的安全性

    /**
     *
     * @param key 密码+随机字符串
     * @return 返回加密以后的字符串
     */
    public static String getMD5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    public static String getJSONString(int code, String msg, Map<String,Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        if (map != null) {
            for (String key :map.keySet()) {
                json.put("key", map.get(key));
            }
        }
        return json.toJSONString();
    }
    public static String getJSONString(int code, String msg) {
        return getJSONString(code,msg,null);
    }
    public static String getJSONString(int code) {
        return getJSONString(code,null,null);
    }

}
