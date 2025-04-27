package org.travelsystem.travel.service;

import org.springframework.transaction.annotation.Transactional;
import org.travelsystem.travel.DTO.PhoneRegisterDTO;
import org.travelsystem.travel.DTO.UserInfoDTO;
import org.travelsystem.travel.entity.User1;

// User1Service.java (接口)
public interface User1Service {
    // 手机号注册
    User1 registerByPhone(PhoneRegisterDTO dto);

    @Transactional(rollbackFor = Exception.class)
    User1 loginByPhone(String phone, String password);

// 使用@Transactional注解标记该方法，表示该方法是一个事务性操作
// rollbackFor = Exception.class 表示当方法抛出Exception或其子类异常时，事务将回滚
    @Transactional(rollbackFor = Exception.class)
// 定义一个名为wechatScanLogin的方法，该方法接收一个String类型的参数code
// 返回值类型为User1，表示该方法返回一个User1对象



    void updateUserInfo(Long userId, UserInfoDTO dto);

    // 获取用户详细信息
    User1 getUserDetails(Long userId);

    // 绑定手机号（微信用户后续绑定）
    void bindPhone(Long userId, String phone, String password);

    void deleteUser(Long userId);
}