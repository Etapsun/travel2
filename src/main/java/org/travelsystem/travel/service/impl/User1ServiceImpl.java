package org.travelsystem.travel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.travelsystem.travel.DTO.PhoneRegisterDTO;
import org.travelsystem.travel.DTO.UserInfoDTO;
import org.travelsystem.travel.entity.User1;
import org.travelsystem.travel.exception.BusinessException;
import org.travelsystem.travel.mapper.User1Mapper;
import org.travelsystem.travel.service.User1Service;
import org.travelsystem.travel.utils.WechatUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Pattern;

// UserServiceImpl.java
@Service
@RequiredArgsConstructor
@Slf4j  // 自动生成 private static final Logger log = ...
public class User1ServiceImpl implements User1Service {
    private final User1Mapper userMapper;
    private final WechatUtil wechatUtil;

    @Override// 标记该方法为事务性方法，当发生Exception时回滚事务
    @Transactional(rollbackFor = Exception.class)
    public User1 registerByPhone(PhoneRegisterDTO dto) {
        if (userMapper == null) {
            throw new BusinessException("用户数据访问层初始化失败");
        }

        // 1. 校验手机号格式
        if (!Pattern.matches("^1[3-9]\\d{9}$", dto.getPhone())) {
        // 如果手机号格式不正确，抛出业务异常
            throw new BusinessException("手机号格式不正确");
        }
        // 2. 校验手机号唯一性
        User1 existingUser = userMapper.selectByPhone(dto.getPhone());
        if (existingUser != null) {
            throw new BusinessException("该手机号已注册");
        }
        // 3. 校验密码复杂度（示例：至少6位包含字母和数字）
        if (!dto.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d).{6,}$")) {
        // 如果密码复杂度不符合要求，抛出业务异常
            throw new BusinessException("密码需包含字母和数字且至少6位");
        }
        // 5. 构建用户对象
        // 创建一个新的User1对象，并设置手机号、加密后的密码和创建时间
        // 创建用户对象时设置新字段
        User1 newUser = new User1();
        newUser.setPhone(dto.getPhone());
        newUser.setPassword(dto.getPassword());
        newUser.setNickname(dto.getNickname());
        newUser.setAvatar(dto.getAvatar());
        newUser.setGender(dto.getGender());
        newUser.setBirthday(dto.getBirthday());
        newUser.setCreateTime(LocalDateTime.now());
        newUser.setEmail(dto.getEmail());
        newUser.setStatus(dto.getStatus());
        try {
            // 只执行一次insert操作
            int result = userMapper.insert(newUser);
            if (result <= 0) {
                throw new BusinessException("用户注册失败");
            }

            log.info("手机用户注册成功, ID: {}, 手机号: {}", newUser.getId(), newUser.getPhone());
            return newUser;
        } catch (DuplicateKeyException e) {
            log.error("手机号重复注册: {}", dto.getPhone());
            throw new BusinessException("该手机号已被注册");
        }
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public User1 loginByPhone(String phone, String password) {
        // 1. 校验手机号格式
        if (!Pattern.matches("^1[3-9]\\d{9}$", phone)) {
            throw new BusinessException("手机号格式不正确");
        }

        // 2. 查询用户是否存在
        User1 user = userMapper.selectByPhone(phone);
        if (user == null) {
            throw new BusinessException("该手机号未注册");
        }

        // 3. 校验密码
        if (!password.equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        return user;
    }






    /**
     * 更新用户信息
     * @param userId 用户ID
     * @param dto    用户信息DTO
     */
    @Override
// 标记该方法为事务性方法，如果发生Exception及其子类异常，将回滚事务
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(Long userId, UserInfoDTO dto) {
        User1 user = userMapper.selectById(userId);
    // 如果用户不存在，抛出业务异常
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
    // 如果传入的昵称不为空，则更新用户的昵称
        if (StringUtils.isNotBlank(dto.getNickname())) {
            user.setNickname(dto.getNickname());
        }
    // 如果传入的头像URL不为空，则更新用户的头像
        if (StringUtils.isNotBlank(dto.getAvatar())) {
            user.setAvatar(dto.getAvatar());
        }
    // 如果传入的性别不为空且在有效范围内（0到2），则更新用户的性别
        if (dto.getGender() != null && (dto.getGender() >= 0 && dto.getGender() <= 2)) {
            user.setGender(dto.getGender());
        }
    // 如果传入的生日不为空且在当前日期之前，则更新用户的生日
        if (dto.getBirthday() != null && dto.getBirthday().isBefore(LocalDate.now())) {
            user.setBirthday(dto.getBirthday());
        }
        if(dto.getStatus()!=null&&(dto.getStatus()==0||dto.getStatus()==1)){
            user.setStatus(dto.getStatus());
        }
        // 新增手机号校验逻辑
        if (StringUtils.isNotBlank(dto.getPhone())) {
            // 检查新手机号是否已被他人使用
            User1 existUser = userMapper.selectByPhone(dto.getPhone());
            if (existUser != null && !existUser.getId().equals(userId)) {
                throw new BusinessException("手机号已被其他用户使用");
            }
            user.setPhone(dto.getPhone());
        }
        if (StringUtils.isNotBlank(dto.getPassword())) {
            if (!dto.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d).{6,}$")) {
                throw new BusinessException("密码需包含字母和数字且至少6位");
            }
            user.setPassword(dto.getPassword());
        }
        if(StringUtils.isNotBlank(dto.getEmail())) {
            user.setEmail(dto.getEmail());
        }

        // 3. 设置更新时间
    // 将当前时间设置为用户的最后更新时间
        user.setUpdateTime(LocalDateTime.now());

        // 4. 执行更新
    // 调用用户Mapper的更新方法，将更新后的用户信息保存到数据库
        int affectedRows = userMapper.update(user);
    // 如果更新影响的行数为0，说明更新失败，抛出业务异常
        if (affectedRows == 0) {
            throw new BusinessException("用户信息更新失败");
        }

    // 记录日志，表示用户信息更新成功
        log.info("用户信息更新成功, ID: {}", userId);
    }

    /**
     * 获取用户详细信息
     * @param userId 用户ID
     * @return 用户详细信息
     */
    @Override// 重写注解，表示该方法重写了父类或接口中的方法
    @GetMapping("/gitId")
    public User1 getUserDetails(Long userId) {

        return userMapper.selectById(userId);
    // 调用userMapper对象的selectById方法，传入userId参数，返回查询到的User1对象
    }

    /**
     * 绑定手机号
     * @param userId   用户ID
     * @param phone    手机号
     * @param password 密码
     */
    @Override// 标记该方法为重写父类或接口中的方法
    @Transactional// 标记该方法为事务性方法，确保方法中的数据库操作要么全部成功，要么全部回滚
    public void bindPhone(Long userId, String phone, String password) {
    // 方法签名，接收用户ID、手机号和密码作为参数
        User1 user = userMapper.selectById(userId);
    // 通过用户ID从数据库中查询用户信息
        if (user.getPhone() != null) {
        // 如果用户已经绑定了手机号，抛出运行时异常
            throw new RuntimeException("用户已绑定手机号");
        }
        if (userMapper.selectByPhone(phone) != null) {
        // 如果手机号已经被其他用户占用，抛出运行时异常
            throw new RuntimeException("手机号已被占用");
        }
        user.setPhone(phone);
    // 将手机号设置到用户对象中
        user.setPassword(password);
    // 将密码进行加密后设置到用户对象中
        userMapper.update(user);
    // 更新用户信息到数据库
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        // 添加前置校验
        User1 user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 执行删除
        int affectedRows = userMapper.deleteById(userId);
        if (affectedRows == 0) {
            throw new BusinessException("用户删除失败");
        }

        log.info("用户删除成功, ID: {}", userId);
    }
}