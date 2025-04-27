package org.travelsystem.travel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data // Lombok注解，自动生成getter、setter、toString、equals、hashCode方法
@Entity // 标识这是一个JPA实体类
@Table(name = "reservation") // 指定实体类对应的数据库表名为reservation
@Builder // Lombok注解，提供Builder模式构建对象
@NoArgsConstructor // Lombok注解，生成无参构造函数
@AllArgsConstructor // Lombok注解，生成全参构造函数
public class Reservation {
    @Id // 标识这是主键字段
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略为自增
    private Long id; // 预约记录的唯一标识符

    @Column(name = "user_id") // 指定数据库表中对应的列名为user_id
    private Long userId; // 预约用户的唯一标识符

    @ManyToOne // 多对一关系，多个预约可以对应一个景点
    @JoinColumn(name = "attraction_id", referencedColumnName = "attraction_id") // 指定关联的外键列名和引用的列名
    private Attraction attraction; // 关联的景点对象

    @Column(name = "schedule_time") // 指定数据库表中对应的列名为schedule_time
    private LocalDate scheduleTime; // 预约的时间

    @Column(name = "status") // 指定数据库表中对应的列名为status
    private Integer status; // 0-待支付 1-已预约 2-已取消

    @Column(name = "create_time")
    private LocalDateTime createTime;
}