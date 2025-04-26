package org.travelsystem.travel.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

// JwtUtil.java
@Component // 标注该类为Spring组件，使其能够被Spring框架自动扫描和注入
public class JwtUtil {
    @Value("${jwt.secret}") // 从配置文件中注入jwt.secret属性的值
    private String secret; // 用于签名和验证JWT的密钥

    // 生成JWT令牌的方法
    public String generateToken(Long userId) {
        return Jwts.builder() // 使用JJWT库的builder模式创建JWT
                .setSubject(userId.toString()) // 设置JWT的主题为用户ID的字符串形式
                .setIssuedAt(new Date()) // 设置JWT的签发时间为当前时间
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24小时
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Long parseToken(String token) {
    // 使用JWT解析器解析传入的token
        Claims claims = Jwts.parser()
            // 设置签名密钥，用于验证token的签名
                .setSigningKey(secret)
            // 移除token中的"Bearer "前缀，然后解析token
                .parseClaimsJws(token.replace("Bearer ", ""))
            // 获取token的主体部分，即claims
                .getBody();
    // 从claims中获取subject字段，并将其转换为Long类型
        return Long.parseLong(claims.getSubject());
    }
}
