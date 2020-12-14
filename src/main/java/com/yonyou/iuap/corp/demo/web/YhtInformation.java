package com.yonyou.iuap.corp.demo.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.yht.sdk.UserCenter;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/yht")
public class YhtInformation {
    /**
     * 获取临时token
     * @return
     */
    @ApiOperation(value = "getToken", notes = "獲取臨時token")
    @GetMapping("/getToken")
    public Object  getToken(@RequestParam String userName, @RequestParam String passWord){
        String sha1Hex = DigestUtils.sha1Hex(passWord);
        String md5 = null;
        Map<String,Object> map = new HashMap<>();
        try {
            md5 = EncoderByMd5(passWord);
            Map<String, String> params = new HashMap<>();
            params.put("username",userName);
            params.put("shaPassword",sha1Hex);
            params.put("md5Password",md5);
            // String ssoToken = UserCenter.createSSOToken(params);
            String result = UserCenter.generateAccessToken(userName, passWord,
                    true,true);

            if(null == JSONObject.parseObject(result).get("data")){
                map.put("token", null);
                map.put("msg", JSONObject.parseObject(result).get("msg"));
                return map;
            }
            String accessToken = (String) JSONObject.parseObject(String.valueOf(JSONObject.parseObject(result).get("data"))).get("access_token");
            String loginTokenByAccessToken = UserCenter.getLoginTokenByAccessToken(accessToken);
            map.put("token",JSONObject.parseObject(loginTokenByAccessToken).get("token"));
            map.put("msg","生成token成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("token", null);
            map.put("msg", e.getMessage());
        }

        return map;
    }

    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定算法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
}
