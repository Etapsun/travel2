package org.travelsystem.travel.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

// UserInfoDTO.java
@Data // 使用Lombok注解@Data，自动生成getter、setter、toString、equals和hashCode方法
public class UserInfoDTO { // 定义一个名为UserInfoDTO的公共类，用于传输用户信息数据
    private Long id; // 定义一个私有长整型字段id，用于存储用户的唯一标识符
    @NotBlank // 使用Hibernate Validator注解@NotBlank，表示该字段不能为空且不能只包含空白字符
    private String nickname; // 定义一个私有字符串字段nickname，用于存储用户的昵称
    private String avatar; // 定义一个私有字符串字段avatar，用于存储用户的头像URL
    @Range(min = 0, max = 2) // 使用Hibernate Validator注解@Range，表示该字段的值必须在0到2之间（包括0和2）
    private Integer gender; // 定义一个私有整数字段gender，用于存储用户的性别（0：未知，1：男，2：女）
    private LocalDate birthday; // 定义一个私有LocalDate字段birthday，用于存储用户的生日
    private String email; // 定义一个私有字符串字段email，用于存储用户的电子邮件地址
    private String phone; // 定义一个私有字符串字段phone，用于存储用户的电话号码
    private String password; // 定义一个私有字符串字段address，用于存储用户的地址
    private Integer status; // 定义一个私有整数字段status，用于存储用户的状态（0：正常，1：禁用）
   // private String avatarUrl; // 新增头像URL字段
}