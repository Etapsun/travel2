package org.travelsystem.travel.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private Long id;
    private Long userId;
    private Long attraction;
    private LocalDate scheduleTime;
    private Integer status;
    private LocalDateTime createTime;
}