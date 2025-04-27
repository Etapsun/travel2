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
import java.util.List;

@Service
// 标记该类为Spring的服务组件，可以被Spring容器管理
@RequiredArgsConstructor
// 使用Lombok注解自动生成包含所有final字段的构造函数
public class ReservationServiceImpl implements ReservationService {
    private final ReservationMapper reservationMapper;
    private final AttractionMapper attractionMapper;

    @Override
    @Transactional
    public ReservationDTO createReservation(ReservationDTO dto) {
        Attraction attraction = attractionMapper.selectByPrimaryKey(dto.getAttraction());

        if (dto.getAttraction() == null || dto.getAttraction() <= 0) {
            throw new IllegalArgumentException("景点ID不合法");
        }

        if (attraction == null) {
            throw new RuntimeException("找不到ID为" + dto.getAttraction() + "的景点");
        }


        if (attraction.getAttractionName() == null) {
            throw new RuntimeException("景点基础信息异常，缺失名称字段");
        }

        if (attraction.getBookingEndTime() == null) {
            throw new RuntimeException(String.format("景点[%s](ID:%d)未配置预约截止时间",
                    attraction.getAttractionName(),
                    dto.getAttraction()));
        }

        LocalDate now = dto.getScheduleTime();
        if (now.isAfter(attraction.getBookingEndTime())) {
            throw new RuntimeException("超出可预约时间范围");
        }
        reservationMapper.insertReservation(dto);
        return dto;
    }

    @Override
    // 标记该方法为接口方法的实现
    public List<ReservationDTO> getUserReservations(Long userId, Integer status) {
        return reservationMapper.selectByCondition(userId, status);
    }

    @Override
    public List<ReservationDTO> getAttractionReservations(Long attraction,Integer status) {
        return reservationMapper.selectByAttractionId(attraction, status);        // 调用Mapper根据用户ID和预约状态查询预约列表，并返回结果
    }
}