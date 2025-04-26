package org.travelsystem.travel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.travelsystem.travel.DTO.PhoneRegisterDTO;
import org.travelsystem.travel.DTO.UserInfoDTO;
import org.travelsystem.travel.DTO.WechatLoginDTO;
import org.travelsystem.travel.entity.User1;
import org.travelsystem.travel.service.User1Service;
import org.travelsystem.travel.utils.JwtUtil;

import java.util.Map;

/**
 * @author 123
 */
// UserController.java
@RestController // 标记该类为Spring MVC的控制器，可以处理HTTP请求
@RequestMapping("/api/user") // 设置该控制器处理的请求路径前缀为"/api/user"
@RequiredArgsConstructor // 使用Lombok注解自动生成包含所有final字段的构造函数
public class User1Controller {
    private final User1Service userService; // 用户服务层，用于处理用户相关的业务逻辑

    // 手机注册
    @PostMapping("/register/phone") // 处理POST请求，路径为"/api/user/register/phone"
    public ResponseEntity<?> phoneRegister(@Valid @ModelAttribute PhoneRegisterDTO dto) {

        // 使用@Valid注解对传入的PhoneRegisterDTO对象进行校验
        // @RequestBody注解表示从请求体中获取数据并转换为PhoneRegisterDTO对象
        userService.registerByPhone(dto.getPhone(), dto.getPassword()); // 调用用户服务层的注册方法
        return ResponseEntity.ok().build(); // 返回HTTP 200状态码，表示注册成功
    }

    // 手机登录
    @PostMapping("/login/phone")
    public ResponseEntity<?> phoneLogin(@Valid @ModelAttribute PhoneRegisterDTO dto) {
        User1 user = userService.loginByPhone(dto.getPhone(), dto.getPassword());
        return ResponseEntity.ok(user);
    }

    // 微信登录
    @PostMapping("/login/wechat")
    public ResponseEntity<?> wechatLogin(@Valid @ModelAttribute WechatLoginDTO dto) {
        User1 user = userService.wechatLogin(dto.getCode());
        return ResponseEntity.ok(user); // 直接返回用户信息
    }

    // 微信扫码注册
    @PostMapping("/register/wechatScan")
    public ResponseEntity<?> wechatScanRegister(@Valid @ModelAttribute WechatLoginDTO dto) {
        User1 user = userService.wechatScanLogin(dto.getCode());
        return ResponseEntity.ok(Map.of("user", user)); // 返回用户信息替代token
    }

    // 更新用户信息
    // 更新用户信息（修改后）
    @PutMapping("/info")
    public ResponseEntity<?> updateInfo(@Valid @ModelAttribute UserInfoDTO dto) {
        // 示例使用固定用户ID，实际需要替代认证方案
        userService.updateUserInfo(1L, dto);
        return ResponseEntity.ok().build();
    }
}