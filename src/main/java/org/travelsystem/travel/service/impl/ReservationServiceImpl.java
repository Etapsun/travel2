package org.travelsystem.travel.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.travelsystem.travel.DTO.ReservationDTO;
import org.travelsystem.travel.entity.Attraction;
import org.travelsystem.travel.mapper.AttractionMapper;
import org.travelsystem.travel.mapper.ReservationMapper;
import org.travelsystem.travel.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

@Service
// 标记该类为Spring的服务组件，可以被Spring容器管理
@RequiredArgsConstructor
// 使用Lombok注解自动生成包含所有final字段的构造函数
public class ReservationServiceImpl implements ReservationService {
    // 实现ReservationService接口，提供预约服务
    private final ReservationMapper reservationMapper;
    // 使用final修饰，表示该字段在初始化后不可改变，依赖注入时由Spring自动注入
    private final AttractionMapper attractionMapper;

    @Override
    @Transactional
    public ReservationDTO createReservation(ReservationDTO dto) {
        // 创建预约的方法，参数为预约信息的数据传输对象
        Attraction attraction = attractionMapper.selectById(dto.getAttractionId());
        // 通过预约信息中的景点ID查询景点信息
        LocalDate now = LocalDate.now();

        // 获取当前时间
        if (now.isAfter(ChronoLocalDate.from(attraction.getBookingEndTime().atStartOfDay()))) {
            // 如果当前时间在景点的可预约时间之后
            throw new RuntimeException("超出可预约时间范围");
            // 抛出运行时异常，提示超出可预约时间范围
        }

        reservationMapper.insertReservation(dto);
        // 调用Mapper插入预约信息到数据库
        return dto;
        // 返回预约信息的数据传输对象
    }

    @Override
    // 标记该方法为接口方法的实现
    public List<ReservationDTO> getUserReservations(Long userId, Integer status) {
        // 获取用户预约列表的方法，参数为用户ID和预约状态
        return reservationMapper.selectByCondition(userId, status);
        // 调用Mapper根据用户ID和预约状态查询预约列表，并返回结果
    }
}