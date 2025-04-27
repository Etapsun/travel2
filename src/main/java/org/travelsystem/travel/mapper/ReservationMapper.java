package org.travelsystem.travel.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.travelsystem.travel.DTO.ReservationDTO;

import java.util.List;

@Mapper
public interface ReservationMapper {
    int insertReservation(ReservationDTO dto);
    List<ReservationDTO> selectByCondition(@Param("userId") Long userId,
                                           @Param("status") Integer status);
}