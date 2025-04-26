package org.travelsystem.travel.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
// 标识该类为一个JPA实体，对应数据库中的一个表
@Table(name = "reservation")
// 指定实体对应的表名为 "reservation"
public class Reservation {
    // 定义一个名为 Reservation 的公共类
    @Id
    // 标识该字段为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 指定主键的生成策略为自增
    private Long id;

    // 定义一个名为 id 的私有长整型字段，用于存储预约的唯一标识
    @Column(name = "user_id", nullable = false)
    // 指定该字段在数据库中对应的列名为 "user_id"，且不能为空
    private Long userId;

    // 定义一个名为 userId 的私有长整型字段，用于存储用户的唯一标识
    @ManyToOne(fetch = FetchType.LAZY)
    // 指定该关联为多对一关系，并采用懒加载策略
    @JoinColumn(name = "attraction_id", nullable = false)
    // 指定该字段在数据库中对应的列名为 "attraction_id"，且不能为空
    private Attraction attraction;

    // 定义一个名为 attraction 的私有 Attraction 类型的字段，用于存储景点信息
    @Column(name = "visit_date", nullable = false)
    // 指定该字段在数据库中对应的列名为 "visit_date"，且不能为空
    private LocalDate visitDate;

    // 定义一个名为 visitDate 的私有 LocalDate 类型的字段，用于存储访问日期
    @Enumerated(EnumType.STRING)
    // 指定该枚举类型在数据库中存储为字符串


    // 定义一个名为 timeSlot 的私有 TimeSlot 类型的字段，用于存储时间段
    @Column(name = "start_time")
    // 指定该字段在数据库中对应的列名为 "start_time"
    private LocalDateTime startTime;

    // 定义一个名为 startTime 的私有 LocalDateTime 类型的字段，用于存储开始时间
    @Column(name = "end_time")
    // 指定该字段在数据库中对应的列名为 "end_time"
    private LocalDateTime endTime;

    // 定义一个名为 endTime 的私有 LocalDateTime 类型的字段，用于存储结束时间
    @Enumerated(EnumType.STRING)
    // 指定该枚举类型在数据库中存储为字符串

    // 定义一个名为 status 的私有 ReservationStatus 类型的字段，默认值为 CREATED，用于存储预约状态
    @Column(name = "create_time", updatable = false)
    // 指定该字段在数据库中对应的列名为 "create_time"，且创建后不可更新
    @CreationTimestamp
    // 指定该字段在实体创建时自动填充当前时间
    private LocalDateTime createTime;

    // 定义一个名为 createTime 的私有 LocalDateTime 类型的字段，用于存储创建时间
    @Column(name = "update_time")
    // 指定该字段在数据库中对应的列名为 "update_time"
    @UpdateTimestamp
    // 指定该字段在实体更新时自动填充当前时间
    private LocalDateTime updateTime;

    // 定义一个名为 updateTime 的私有 LocalDateTime 类型的字段，用于存储更新时间
    @Column(name = "reservation_number", unique = true)
    // 指定该字段在数据库中对应的列名为 "reservation_number"，且值必须唯一
    private String reservationNumber;
    // 定义一个名为 reservationNumber 的私有字符串类型的字段，用于存储预约编号
}