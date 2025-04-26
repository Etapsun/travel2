package org.travelsystem.travel.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.travelsystem.travel.DTO.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ReservationMapper {
    int create(Reservation reservation);
    int cancel(@Param("id") Long id, @Param("cancelledAt") LocalDateTime cancelledAt);
    int countByAttractionAndDate(@Param("attractionId") Long attractionId,
                                 @Param("date") LocalDate date);
    List<Reservation> findActiveReservations(Long userId);
}