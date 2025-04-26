package org.travelsystem.travel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *通知
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "user_id")
    @JsonProperty("user_id")
    private Long user_id;//接收用户id

    @Column(name = "content")
    @JsonProperty("content")
    private String content;//通知内容


    private enum type{订单,系统,促销};//通知类型
    @Column(name = "type")
    @JsonProperty("type")
    private type type;

    @Column(name = "is_read")
    @JsonProperty("isRead")
    public boolean isRead;//是否已读

    @Column(name = "send_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime sendTime;

}
