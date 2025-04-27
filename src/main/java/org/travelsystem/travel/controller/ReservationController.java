package org.travelsystem.travel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.travelsystem.travel.DTO.ReservationDTO;
import org.travelsystem.travel.entity.ProductInformation;
import org.travelsystem.travel.service.ReservationService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    /*
     * 创建预约 给用户id，
     * 预约状态id
     * @param dto 预约信息
     */
    @PostMapping("/createReservation")
    public ResponseEntity<ReservationDTO> create(
            @RequestParam("userId") Long userId,
            @RequestParam("attraction") Long attraction,
            @RequestParam Integer status,
            @RequestParam LocalDate scheduleTime) {

        ReservationDTO dto = new ReservationDTO();
        dto.setUserId(userId);
        dto.setAttraction(attraction);
        dto.setStatus(status);
        dto.setScheduleTime(scheduleTime);

        return ResponseEntity.ok(reservationService.createReservation(dto));
    }

    /*
     * 给用户的景点预约表
     * @param userId 用户ID
     * @param status 预约状态
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDTO>> getByUser(
            @PathVariable Long userId,
            @RequestParam(required = false) Integer status) {
        return ResponseEntity.ok(reservationService.getUserReservations(userId, status));
    }

    /**
     * 获取指定景点的预约列表
     * 通过景点id获取预约用户列表
     * 主要面向村民
     * @param attraction 景点ID
     * @param status 预约状态
     * @return
     */
    @GetMapping("/attraction/{attraction}")
    public ResponseEntity<List<ReservationDTO>> getByAttraction(
            @PathVariable Long attraction,
            @RequestParam(required = false) Integer status){
        return ResponseEntity.ok(reservationService.getAttractionReservations(attraction,status));
    }
}