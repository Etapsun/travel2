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

import java.util.Map;

/**
 * @author 123
 */
// UserController.java
@RestController // 标记该类为Spring MVC的控制器，可以处理HTTP请求
@RequestMapping("api/user") // 设置该控制器处理的请求路径前缀为"/api/user"
@RequiredArgsConstructor // 使用Lombok注解自动生成包含所有final字段的构造函数
public class User1Controller {
    private final User1Service userService; // 用户服务层，用于处理用户相关的业务逻辑

    // 手机注册
    @PostMapping("/register/phone")
    public ResponseEntity<?> phoneRegister(@Valid @ModelAttribute PhoneRegisterDTO dto) {
        User1 newUser = userService.registerByPhone(dto);
        return ResponseEntity.ok(newUser);
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
// 更新用户信息（修复身份验证和参数绑定问题）
// 更新用户信息
    @PutMapping("/info")
    public ResponseEntity<?> updateInfo(
            @Valid @ModelAttribute UserInfoDTO dto,
            @RequestParam Long id) {  // 新增请求参数
        userService.updateUserInfo(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}