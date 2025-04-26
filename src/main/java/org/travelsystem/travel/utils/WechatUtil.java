package org.travelsystem.travel.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import org.travelsystem.travel.exception.BusinessException;

import java.util.Map;

// WechatUtil.java
@Component
public class WechatUtil {
    // 从配置文件中注入微信小程序的appid
    @Value("${wechat.appid}")
    private String appid;
    // 从配置文件中注入微信小程序的secret
    @Value("${wechat.secret}")
    private String secret;

    // 根据微信小程序提供的code获取用户的openid
    public String getOpenid(String code) {
        // 构造请求URL，用于获取openid
    //    return RetrieveOpenid(code);
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?" +
                        "appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                appid, secret, code);

        // 使用RestTemplate发送请求
        ResponseEntity<Map> response = new RestTemplate().getForEntity(url, Map.class);
        Map<String, Object> body = response.getBody();

        // 判断返回结果中是否包含openid
        if (body.containsKey("openid")) {
            // 返回openid
            return (String) body.get("openid");
        } else {
            // 抛出异常
            throw new RuntimeException("微信登录失败：" + body.get("errmsg"));
        }
    }

    // WechatUtil.java 新增方法<dependency>

    private RestTemplate restTemplate;
    public Map<String, Object> getWechatSession(String code) {
        // 构造请求微信服务器的URL
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?"
                        + "appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                appid, secret, code);

        try {
            // 发送GET请求，获取微信服务器的响应
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            // 返回响应体
            return response.getBody();
        } catch (RestClientException e) {
            // 抛出自定义异常
            throw new BusinessException("微信服务调用异常");
        }
    }
}