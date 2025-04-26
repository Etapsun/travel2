package org.travelsystem.travel.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// WechatLoginDTO.java
// 引入Lombok库的@Data注解，用于自动生成getter、setter、toString、equals和hashCode方法
@Data
public class WechatLoginDTO {
    // 使用@NotBlank注解，表示该字段在字符串非空且长度大于0的情况下才有效，通常用于表单验证
    @NotBlank
    private String code; // 定义一个私有字符串类型的成员变量code，用于存储微信登录时使用的code
}