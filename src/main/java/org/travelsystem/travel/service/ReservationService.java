package org.travelsystem.travel.service;

import org.travelsystem.travel.DTO.ReservationDTO;

import java.util.List;

public interface ReservationService {
    ReservationDTO createReservation(ReservationDTO dto);
    List<ReservationDTO> getUserReservations(Long userId, Integer status);

    public List<ReservationDTO> getAttractionReservations(Long attractionId,Integer status);
}