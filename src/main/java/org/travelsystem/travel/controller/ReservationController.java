package org.travelsystem.travel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.travelsystem.travel.DTO.ReservationDTO;
import org.travelsystem.travel.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    /*
     * 创建预约
     * @param dto 预约信息
     */
    @PostMapping("/createReservation")
    public ResponseEntity<ReservationDTO> create(@RequestParam ReservationDTO dto) {
        return ResponseEntity.ok(reservationService.createReservation(dto));
    }

    /*
     * 获取用户预约列表
     * @param userId 用户ID
     * @param status 预约状态
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDTO>> getByUser(
            @PathVariable Long userId,
            @RequestParam(required = false) Integer status) {
        return ResponseEntity.ok(reservationService.getUserReservations(userId, status));
    }
}