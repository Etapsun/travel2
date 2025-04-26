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
 * 操作人类
 * 操作日志
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="audit_log")
@Entity
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键（MySQL/Auto）
    @Column(name="id")
    @JsonProperty("id")
    private Long id; // 主键字段

    @Column(name = "operator_id")
    @JsonProperty("operatorId")
    private Integer operatorId;//操作人id（外键）


    private enum opertaor_type{删除评论,点赞评论};//操作类型
    @Column(name = "operation_type")
    @JsonProperty("opertaorType")
    private opertaor_type opertaorType;

    @Column(name = "target_id")
    @JsonProperty("targetId")
    private Integer targetId;//操作目标id

    @Column(name = "operation_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime operateTime;//操作时间

    @Column(name = "detail")
    @JsonProperty("operatorDetail")
    private String operatorDetail;//操作详细
}
