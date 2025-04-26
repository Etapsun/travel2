package org.travelsystem.travel.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


// PhoneRegisterDTO.java
@Data // 使用Lombok注解@Data，自动生成getter、setter、toString、equals、hashCode等方法
public class PhoneRegisterDTO {
    @NotBlank // 使用Hibernate Validator注解@NotBlank，表示该字段不能为空，且不能只包含空白字符
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误") // 使用@Pattern注解，定义手机号的正则表达式，确保手机号格式正确，若格式错误则返回指定消息
    private String phone; // 手机号字段

    @NotBlank // 使用Hibernate Validator注解@NotBlank，表示该字段不能为空，且不能只包含空白字符
    @Size(min = 6, max = 20) // 使用@Size注解，限制密码长度在6到20个字符之间
    private String password; // 密码字段
}



