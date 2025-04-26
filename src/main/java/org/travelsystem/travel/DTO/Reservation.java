package org.travelsystem.travel.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder // 使用Lombok的@Builder注解，自动生成Builder模式的构造器，方便创建对象
@Getter // 使用Lombok的@Getter注解，自动生成所有字段的getter方法
@Setter // 使用Lombok的@Setter注解，自动生成所有字段的setter方法
public class Reservation { // 定义一个名为Reservation的公共类
    private Long id; // 预订的唯一标识符
    private Long userId; // 预订用户的唯一标识符
    private Long attractionId; // 预订景点的唯一标识符
    private LocalDate reservationDate; // 预订的日期
    private String timeSlot; // MORNING/AFTERNOON
    private ReservationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime cancelledAt;
// 定义一个名为 ReservationStatus 的枚举类型
    public enum ReservationStatus {
        CONFIRMED, CANCELLED, COMPLETED
    }
}

