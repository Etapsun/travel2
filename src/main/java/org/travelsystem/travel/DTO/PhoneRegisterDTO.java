package org.travelsystem.travel.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;


// PhoneRegisterDTO.java
@Data // 使用Lombok注解@Data，自动生成getter、setter、toString、equals、hashCode等方法
public class PhoneRegisterDTO {
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{6,}$",
            message = "密码需包含字母和数字且至少6位")
    private String password;

    @NotBlank(message = "昵称不能为空")
    @Size(min = 2, max = 20, message = "昵称长度2-20个字符")
    private String nickname;

    @URL(message = "头像链接格式不正确")
    private String avatar;

    @Min(value = 0, message = "性别参数错误")
    @Max(value = 2, message = "性别参数错误")
    private Integer gender;

    @Past(message = "生日日期不合法")
    private LocalDate birthday;

    @Email(message = "邮箱格式不正确")
    private String email;

    private Integer status;


    // 生成getter/setter...
}



