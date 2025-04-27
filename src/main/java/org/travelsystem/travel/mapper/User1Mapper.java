package org.travelsystem.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.travelsystem.travel.DTO.UserInfoDTO;
import org.travelsystem.travel.entity.User1;

// UserMapper.java
@Mapper
public interface User1Mapper extends BaseMapper<User1> {
    @Select("SELECT * FROM user1 WHERE nickname = #{username}")
    User1 selectByUsername(@Param("username") String username);
    User1 selectByPhone(@Param("phone") String phone);
    //User1 selectByWechatOpenid(@Param("openid") String openid);
    int insert(User1 user);
    int update(User1 user);

    int deleteByid(@Param("id") Long id);


    @Select("SELECT avatar " +
            "FROM user1 WHERE id = #{userId}")
    @Results({
           @Result(property = "avatar", column = "avatar")
    })
    UserInfoDTO entityToDto(@Param("userId") Long userId);
}